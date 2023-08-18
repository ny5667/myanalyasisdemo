package com.supcon.orchid.SESECD.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * socket 连接池 复用socket
 */
@Slf4j
public class SocketPoolUtils {
    private static String host;
    private static int port;
    /**
     *  创建一个有界队列用于存储Socket连接对象
     */
    private static BlockingQueue<Socket> pool = new LinkedBlockingQueue<>(100);


    public static void init(String h, int p) {
        host = h;
        port = p;
    }

    /**
     * 获取一个socket连接
     *
     * @return
     * @throws Exception
     */
    public static Socket borrowSocket() {
        // 从连接池中获取一个Socket连接对象
        Socket socket = pool.poll();
        try {
            if (socket == null) {
                // 如果没有可用的连接，则创建一个新连接
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port));
            } else if (socket.isClosed() || !socket.isConnected()) {
                //确保关闭
                socket.close();
                // 如果连接已关闭，则创建一个新连接
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port));
            }
        } catch (Exception e) {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(host, port));
            } catch (IOException ioException) {
                log.error("Socket无法创建，ip： " + host + " post: " + port);
                ioException.printStackTrace();
                return null;
            }
        }
        return socket;
    }

    /**
     * 归还一个socket连接
     *
     * @param socket
     */
    public static void returnSocket(Socket socket) {
        // 将连接归还到连接池中
        pool.offer(socket);
    }
}
