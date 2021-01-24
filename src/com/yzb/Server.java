package com.yzb;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.lang.Console;
import com.yzb.Exception.ParseHttpRequestException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server {
        public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while(true){
            Socket socket = serverSocket.accept();
            HttpRequest hr = new HttpRequest(socket);

            System.out.println(hr.getRequestContent());
            System.out.println(hr.getQueryString());

            OutputStream outputStream = socket.getOutputStream();
            String sendMessage = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nhello Client!";
            outputStream.write(sendMessage.getBytes(StandardCharsets.UTF_8));

            socket.close();
        }
    }
}
