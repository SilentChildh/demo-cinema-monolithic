package com.huanghehua.www.framework.rpc.data;

import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.Objects;

/**
 * 服务器配置信息
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
public class ServerConfig {
    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private Channel channel;
    public ServerConfig(NioEventLoopGroup boss, NioEventLoopGroup worker, Channel channel) {
        this.boss = boss;
        this.worker = worker;
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerConfig that = (ServerConfig) o;
        return Objects.equals(boss, that.boss) && Objects.equals(worker, that.worker) && Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boss, worker, channel);
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "boss=" + boss +
                ", worker=" + worker +
                ", channel=" + channel +
                '}';
    }

    public NioEventLoopGroup getBoss() {
        return boss;
    }

    public void setBoss(NioEventLoopGroup boss) {
        this.boss = boss;
    }

    public NioEventLoopGroup getWorker() {
        return worker;
    }

    public void setWorker(NioEventLoopGroup worker) {
        this.worker = worker;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
