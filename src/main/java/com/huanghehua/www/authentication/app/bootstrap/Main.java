package com.huanghehua.www.authentication.app.bootstrap;


import com.huanghehua.rpc.proxy.Skeleton;

/**
 * 主要
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
public class Main {
    public static void main(String[] args) {
        Skeleton.register("localhost", 8090);
    }
}
