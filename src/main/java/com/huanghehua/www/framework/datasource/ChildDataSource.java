package com.huanghehua.www.framework.datasource;


import com.huanghehua.www.datasource.exception.*;
import com.huanghehua.www.datasource.model.DataSourceConfiguration;
import com.huanghehua.www.orm.spi.datasource.DataSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 创建数据库连接池。用于获取连接池中空闲的连接。<br/>
 * 可以同时创建多个连接池，每个连接池将拥有自己的特性。<br/>
 * <p/>
 * <strong>关于字段：</strong>
 * <ol>
 *     <li>数据库连接池，利用池化技术来避免多次与数据库的物理连接与关闭，进而提升性能，并优化用户体验。</li>
 *     <li>final修饰保证值不可变</li>
 *     <li>
 *          连接池容器{@code QUEUE}选择使用阻塞队列{@link LinkedBlockingQueue}来保证线程安全.
 *          同时需要强调的是，
 *          使用并发队列{@link ConcurrentLinkedQueue}可能会导致{@code size()}方法不是百分百准确，
 *          故本类不使用该队列来存储连接资源。
 *     </li>
 *     <li>资源绑定器{@code THREAD_LOCAL}，保证了一个线程在未释放资源前，每次获取到的连接都是同一个。</li>
 *     <li>累加器{@code PRESENT_MAX_ACTIVE}，保证了多线程操作时对于活跃数量增减的原子性与可见性，即保证了线程安全。</li>
 * </ol>
 * <strong>关于方法：</strong>
 * <ol>
 *     <li>用于获取连接池对象的方法{@code creatDataSource()}。</li>
 *     <li>
 *         核心方法 {@code getConnection()}用于返回连接资源，其中会保证多线程同步，并将经过三个自定义异常检测，
 *         分别是{@link MinIdledException}, {@link OverMaxActiveException}, {@link TimedOutException}。
 *     </li>
 *     <li>重要方法一 {@code release()}用于回收资源入池，并解除线程绑定资源。</li>
 *     <li>
 *         重要方法二 {@code getConnectionProxy()}用于获取连接的代理类，其中特别强调了在调用{@code close()}方法时，
 *         调用的是本类中的{@code release()}方法。
 *     </li>
 * </ol>
 *
 * @author silent_child
 *
 * @version 1.0
 **/

public class ChildDataSource implements DataSource {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.datasource.ChildDataSource");
    private String url;
    private String user;
    private String password;
    /**
     * 空闲池中最少连接资源数量，默认为5
     */
    private int minIdle;
    /**
     * 活跃状态下的最大连接数，默认为20
     */
    private int maxActive;
    /**
     * 最大连接时长，默认是5s
     */
    private int maxWait;

    /**
     * 数据源配置
     */
    private final DataSourceConfiguration dataSourceConfiguration;
    /**
     * 缓冲线程池，其中的每个线程用于监听每个外界线程获取连接资源
     */
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * 用于为线程绑定连接资源，使得每一个线程在未释放资源时，获取的都是同一个资源。
     */
    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 使用阻塞队列，线程安全，而且是队列数据结构，更符合池化技术。用于保存空闲状态的连接资源。<br/>
     * 之所以不选择并发队列，是因为size()方法可能并不准确
     */
    private final LinkedBlockingQueue<Connection> idlePool = new LinkedBlockingQueue<>();
    /**
     * 使用并发hashMap，线程安全，用于保存处于活跃状态的连接资源。<br/>
     * 需要注意的是，不应该使用size()方法来统计活跃连接数量，因为它可能是不准确的。<br/>
     * 该池采用延迟初始化，在调用{@code createConnection()}时，根据最大活跃数量配置信息进行初始化。<br/>
     * 且为了防止临界值扩容的情况，初始容量应该设置为 elementSum / loaderFactor + 1.<br/>
     */
    private Map<Connection, ConnectionStatus> activePool;
    /**
     * 累加器，用于记录当前活跃连接数量
     */
    private final LongAdder presentMaxActive = new LongAdder();

    /**
     * 标记该连接池是否需要销毁
     */
    private boolean destroy = false;

    /**
     * 外界获取连接池对象的方式，但并未开启连接池。<br/>
     *
     * @param dataSourceConfiguration 数据源配置
     */
    public ChildDataSource(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    /**
     * 用于开启连接池对象。<br/>
     * <p/>
     *
     * @return {@link ChildDataSource} 一个数据库连接池对象
     */
    public ChildDataSource init() {
        // 获取对应资源
        String driver = dataSourceConfiguration.getDriver();
        this.url = dataSourceConfiguration.getUrl();
        this.user = dataSourceConfiguration.getUser();
        this.password = dataSourceConfiguration.getPassword();
        int initialSize = dataSourceConfiguration.getInitialSize();
        this.minIdle = dataSourceConfiguration.getMinIdle();
        this.maxActive = dataSourceConfiguration.getMaxActive();
        this.maxWait = dataSourceConfiguration.getMaxWait();

        // 根据最大活跃数量对活跃池进行初始化
        this.activePool = new ConcurrentHashMap<>((int) (maxActive / 0.75) + 1);
        try {
            // 注册数据库驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Driver class not found.", e);
            throw new ReflectionException("Driver class not found.", e);
        }

        // 获取初始化资源
        for (int i = 0; i < initialSize; i++) {
            this.idlePool.offer(this.getConnectionProxy());
        }

        LOGGER.log(Level.INFO, "数据库连接池初始化完毕");
        return this;
    }

    /**
     * 用于保证获取连接时的线程安全
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 用于从空闲池中获取连接资源。<br/>
     * <p/>
     * 以{@code queue}为锁，防止多线程并发获取连接。<br/>
     * 特别地，由于最小空闲数的存在，基本上都不会令阻塞队列在获取资源时发生阻塞。<br/>
     * 之所以要用一个线程来监视获取资源，是因为在本次操作中，实际上可以分为两层：
     * 拿不到第一把锁的所有线程将进入阻塞，假如已获得第一把锁的线程在想得到第二把锁的时候被阻塞，那么就会有死锁的可能性。
     * 故此时通过提供一个外部线程，无论如何都会在一定时间后使其释放锁，即通知他们超时。<br/>
     *
     * @return {@link Connection}
     */
    @Override
    public Connection getConnection() {
        // 获取当前线程的绑定资源
        Connection connection = threadLocal.get();
        // 若当前线程已绑定连接资源，则直接返回
        if (connection != null) {
            return connection;
        }

        // 获取当前线程
        final Thread currentThread = Thread.currentThread();

        // lambda表达式中捕获的值不可变，故用原子类来包装
        AtomicBoolean cancelTimer = new AtomicBoolean(false);
        threadPool.submit(() -> {
            // 开始记录时间
            long start = System.currentTimeMillis();
            while (!currentThread.isInterrupted()) {
                if ((System.currentTimeMillis() - start) >= maxWait) {
                    // 若线程处于活动中，该方法不会产生任何影响。若线程在获取资源时被阻塞，则会中断阻塞。
                    currentThread.interrupt();

                    LOGGER.log(Level.INFO, "{0} Client attempt to get connection timeOut.\n {1} Timer is done.",
                            new Object[]{currentThread, Thread.currentThread()});
                    return;
                }
                // 已经获取到连接时，监听任务终止
                if (cancelTimer.get()) {
                    LOGGER.log(Level.INFO, "{0} Timer is done", Thread.currentThread());
                    return;
                }
            }
        });


        try {
            try {
                boolean holdLock = lock.tryLock(maxWait, TimeUnit.MILLISECONDS);
                if (!holdLock) {
                    // 判断超时，超时则直接抛出异常。
                    LOGGER.log(Level.WARNING, "{0} 连接超时", Thread.currentThread());
                    throw new TimedOutException(Thread.currentThread() + "连接超时");
                }
                LOGGER.log(Level.INFO, "{0} 获取锁", Thread.currentThread());
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, Thread.currentThread() + " tryLock 限时获取锁被中断", e);
                throw new InterruptException(Thread.currentThread() + " tryLock 限时获取锁被中断", e);
            }

            // 是否低于最小空闲数
            isMinIdled();
            // 判断是否超过最大活跃数
            isOverMaxActive();
            // 活跃数量累加器自增
            presentMaxActive.increment();

            // 获取资源
            try {
            /*若无资源，那么将会阻塞，此时若超时，则会中断阻塞，抛出异常。
            若在超时前恢复运行，那么将不会抛出异常*/
                connection = idlePool.take();
            }
            // 一般来说，只要最小连接数量不为负数，就不会出现阻塞的情况
            catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, Thread.currentThread() + " 获取资源被阻塞，导致超时", e);
                throw new TimedOutException(Thread.currentThread() + " 获取资源被阻塞，导致超时", e);
            }

        } finally {
            // 如果当前线程持有锁，则解锁
            if (lock.isHeldByCurrentThread()) {
                // 解锁
                LOGGER.log(Level.INFO, "{0} 释放锁", Thread.currentThread());
                lock.unlock();
            }
            // 取消监听任务
            cancelTimer.set(true);
        }


        // 线程绑定资源
        threadLocal.set(connection);
        // 将该连接放入活跃连接池
        activePool.put(connection, new ConnectionStatus());
        // 最后返回资源
        return connection;

    }

    /**
     * 用于获取连接的代理类。<br/>
     * <p/>
     * 代理方法中主要增加了：在调用代理对象的{@code close()}时，
     * 调用的是本类{@code DataBasePool}的方法——{@code reclaim()}，特别地，需要将连接的事务重置，
     * 即掉调用{@link Connection#setAutoCommit(boolean)}并设置为true。<br/>
     * 在调用{@link Connection#isClosed()}方法时，将调用本类的{@code isReleased()}方法。<br/>
     *
     * @return {@link Connection} 实际上返回的是一个连接的代理类
     */
    private Connection getConnectionProxy() {
        return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class},
                new InvocationHandler() {
                    /**
                     * connection为代理类中的被代理对象。<br/>
                     * 用final修饰保证该对象不会再指向其他连接资源，
                     * 提高了池中的连接资源被反复获取与回收时的安全性。<br/>
                     * 不使用static的原因是，每个代理对象中，应该持有独有的一份连接资源，而不应该是共享的。<br/>
                     */
                    private final Connection connection;
                    private static final String CLOSE = "close";
                    private static final String IS_CLOSED = "isClosed";

                    {
                        try {
                            connection = DriverManager.getConnection(url, user, password);// 用于获取资源
                        } catch (SQLException e) {
                            LOGGER.log(Level.SEVERE, "连接资源入池失败");
                            throw new DataSourceException("连接资源入池失败", e);
                        }
                    }

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws SQLException {
                        String methodName = method.getName();

                        // 如果调用的是close方法
                        if (CLOSE.equals(methodName)) {
                            // 如果调用的是destroy方法，则物理关闭连接
                            if (destroy) {
                                connection.close();
                                LOGGER.log(Level.INFO, "{0} close successfully", connection);
                            }
                            // 调用自定义数据库连接池的release方法
                            else {
                                // 注意需要将连接的事务进行重置
                                ((Connection) proxy).setAutoCommit(true);
                                return release((Connection) proxy);
                            }
                        }
                        // 如果调用的是isClosed方法，则调用自定义数据库连接池的isReleased方法
                        else if (IS_CLOSED.equals(methodName)) {
                            return isReleased((Connection) proxy);
                        }
                        // 如果是其他方法则无所谓了
                        try {
                            return method.invoke(connection, args);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LOGGER.log(Level.SEVERE, "执行数据库操作Fail", e);
                            throw new ReflectionException("执行数据库操作Fail", e);
                        }
                    }
                });

    }

    /**
     * 用于回收资源到空闲池<br/>
     *
     * @param connection 需要回收的连接资源
     */
    public Void release(Connection connection) {
        // 将当前线程解除资源绑定
        threadLocal.remove();
        // 将连接资源移出活跃连接池
        activePool.remove(connection);
        try {
            // 将连接资源放回池中
            idlePool.put(connection);
        } catch (InterruptedException e) {
            // 一般情况下不会出现该异常。因为一定会放回无界队列中
            LOGGER.log(Level.SEVERE, "中断阻塞队列的阻塞作用", e);
            throw new InterruptException("中断阻塞队列的阻塞作用", e);
        }
        // 活跃数量累加器自减
        presentMaxActive.decrement();

        LOGGER.log(Level.INFO, "Connection was successfully released.");
        return null;
    }

    /**
     * 物理关闭连接池中的所有连接资源
     */
    public void destroy() {
        destroy = true;
        activePool.forEach((connection, connectionStatus) -> {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "{0} 连接关闭 fail", connection);
                throw new RuntimeException(connection + "Connection close fail", e);
            }
        });

        idlePool.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.INFO, "{0} 连接关闭 fail", connection);
                throw new RuntimeException(connection + "Connection close fail", e);
            }
        });
    }

    /**
     * 用于判断一个连接资源是否从活跃连接池回到空闲连接池。<br/>
     *
     * @param connection 需要进行判断的连接资源
     * @return Boolean true已释放，false为未释放
     */
    private Boolean isReleased(Connection connection) {
        // 如果活跃连接池不存在该连接，则已释放，否则未释放
        return !activePool.containsKey(connection);
    }

    /**
     * 用于判断空闲连接数量是否低于{@code minIdle}。<br/>
     * <p/>
     * 在空闲连接数量小于等于{@code minIdle}时添加个数。个数cnt为 minIdle / 2 + 1。<br/>
     *
     * @throws MinIdledException 当空闲连接数量小于等于{@code minIdle}时，
     *                           抛出“已达最小空闲连接数量”异常。<br/>
     *                           但是异常不继续向上抛出，而是直接该方法内捕获。<br/>
     *                           故该方法不影响线程获取连接。<br/>
     */
    private void isMinIdled() {
        // 未达到最小空闲数量时，直接返回
        if (idlePool.size() > minIdle) {
            return;
        }

        // 当达到最小空闲数量时，补充资源

        // 设置补充资源的数量
        int cnt = minIdle / 2 + 1;

        // 向池中添加资源
        for (int i = 0; i < cnt; i++) {
            try {
                idlePool.put(getConnectionProxy());
            }
            // 对于本方法来说不会出现此异常
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "当阻塞队列到达边界线程阻塞，阻塞被打断时抛出异常", e);
                throw new InterruptException("当阻塞队列到达边界线程阻塞，阻塞被打断时抛出异常", e);
            }
        }

        // 提示已到达最小空闲数量，已自动扩容
        try {
            throw new MinIdledException("已到达最小空闲数量，且自动扩容");
        } catch (MinIdledException e) {
            LOGGER.log(Level.WARNING, "IdlePool has been reached MinIdledNumber.", e);
        }

    }

    /**
     * 用于判断活跃连接数量是否大于{@code maxActive}<br/>
     * <p/>
     *
     * @throws OverMaxActiveException 当活跃连接数量大于{@code maxActive}时，
     *                                抛出“连接数量已达阈值”异常
     */
    private void isOverMaxActive() {
        // 当达到最大活跃数量时抛出异常
        if (presentMaxActive.sum() >= maxActive) {
            LOGGER.log(Level.WARNING, "Connections has been beyond MaxActiveNumber.");
            throw new OverMaxActiveException("达到最大活跃数量");
        }
    }

    /**
     * 保存活跃连接资源的状态，如已存活时间，即被调用者持有的时间。
     */
    class ConnectionStatus {
        private long surviveTime;
        /*public ConnectionStatus openActiveConnection() {
        }*/

    }
}
