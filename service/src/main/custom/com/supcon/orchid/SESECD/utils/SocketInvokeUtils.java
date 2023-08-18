package com.supcon.orchid.SESECD.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * <p>
 * socket请求客户端
 * </p>
 *
 * @author lufengdong
 * @create 2023-03-22 18:20
 */
@Slf4j
public class SocketInvokeUtils {

    public static String exchange(String ip, int port, byte[] data) {
        StringBuilder sb = new StringBuilder();
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            // 创建socket用来发送请求数据，设置IP和端口
            socket = new Socket(ip, port);
            outputStream = socket.getOutputStream();
            outputStream.write(data);
            Thread.sleep(3000L);
            outputStream.flush();
            // 关闭输出流：向网络发送一个结束符EOF
            socket.shutdownOutput();
            // 接收socket反馈
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
                if (sb.toString().endsWith("@@")) {
                    break;
                }
            }
        } catch (Exception ex) {
            log.error("Socket请求出错，错误信息:{0}", ex);
        } finally {
            close(inputStream, outputStream, socket);
        }
        return sb.toString();
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (null != closeable) {
                    closeable.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param socket
     * @param content     发送内容，字符串
     * @param sendCharset 发送时编码
     * @param retCharset  解析响应内容编码
     * @return
     */
    public static String sendCommand(Socket socket, String content, String sendCharset, String retCharset) throws IOException, InterruptedException {
        if (socket == null) {
            log.error("socket 传入失败");
            throw new RuntimeException("socket 传入失败");
        }
        byte[] bytes;
        try {
            bytes = content.getBytes(sendCharset);
        } catch (UnsupportedEncodingException e) {
            log.error("编码集不存在：" + sendCharset);
            return null;
        }
        //发送数据
        log.error("sendCommand===== 发送指令内容： " + content);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        //发送结束 关闭输入流
        //socket.shutdownOutput();
        Thread.sleep(1000L);
        //接受响应数据
        InputStream in = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, retCharset));
        //读取一行 应答只有一行
        String line = bufferedReader.readLine();
        log.error("sendCommand===== 收到指令回复： " + line);
        return line;
    }


}
