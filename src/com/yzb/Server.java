package com.yzb;

import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Server {
        public static void main(String[] args) throws IOException, ParseHttpRequestException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while(true){
            Socket socket = serverSocket.accept();
            HttpRequest hr = new HttpRequest(socket);

            System.out.println(hr.getHeaderMap());

            OutputStream outputStream = socket.getOutputStream();
            String sendMessage = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nhello Client!";
            outputStream.write(sendMessage.getBytes(StandardCharsets.UTF_8));

            socket.close();
        }
    }
}
