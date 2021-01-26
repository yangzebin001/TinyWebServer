package com.yzb;

import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpContant;
import com.yzb.http.HttpRequest;
import com.yzb.http.HttpResponse;

import java.io.*;
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

//            System.out.println(hr.getHeaderMap());

            HttpResponse hs = new HttpResponse(socket);

//            PrintWriter outputStream = hs.getWriter();
//            hs.setHeader(HttpContant.HEADER_CONTENT_TYPE, HttpContant.DEFAULT_CONTENT_TYPE);
//            hs.setContentLength("<html><body>hello client!</body></html>".length());
//            outputStream.print("<html><body>hello client!</body></html>");
//            outputStream.close();

//            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("webapps/form.html")));
//            String line = "";
//            while((line = bufferedReader.readLine()) != null){
//                outputStream.write(line);
//                outputStream.write(HttpContant.LINE_TERMINATOR);
//            }
//            outputStream.flush();

//            OutputStream outputStream = hs.getOutputStream();
//            hs.setHeader(HttpContant.HEADER_CONTENT_TYPE, HttpContant.DEFAULT_CONTENT_TYPE);
//            hs.setContentLength("<html><body>hi client!</body></html>".length());
//            outputStream.write("<html><body>hi client!</body></html>".getBytes(StandardCharsets.UTF_8));
//            outputStream.flush();
//            outputStream.close();

//            FileInputStream bufferedReader = new FileInputStream(new File("webapps/form.html"));
//            int size = 0;
//            int b = 0;
//            while((b = bufferedReader.read()) != -1){
//                outputStream.write(b);
//                size++;
//            }
//            hs.setContentLength(size);
//            outputStream.flush();


//            FileInputStream bufferedReader = new FileInputStream(new File("webapps/longPdf.pdf"));
//            int size = 0;
//            int b = 0;
//            while((b = bufferedReader.read()) != -1){
//                outputStream.write(b);
//                size++;
//            }
//            hs.setContentLength(size);
//            hs.setContentType("application/pdf");
//            outputStream.flush();

//            hs.sendRedirect("/hello");
            socket.close();


        }
    }
}
