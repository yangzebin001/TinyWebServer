package com.yzb;

import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpContant;
import com.yzb.http.HttpProcessor;
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
    private volatile static Server serverInstance;
    private int port;
    private Server(){
    }

    public static Server getServerInstance(){
        if(serverInstance == null){
            synchronized(Server.class){
                if(serverInstance == null){
                    serverInstance = new Server();
                }
            }
        }
        return serverInstance;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }


    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // server init failed!!!
            e.printStackTrace();
        }
        while(true){
            Socket socket = null;
            HttpRequest httpRequest = null;
            HttpResponse httpResponse = null;
            HttpProcessor httpProcessor = null;
            try {
                socket = serverSocket.accept();
                httpRequest = new HttpRequest(socket);
                httpResponse = new HttpResponse(socket);
                httpProcessor = new HttpProcessor();
                httpProcessor.execute(socket,httpRequest,httpResponse);

            } catch (IOException e) {
                //accept http request failed!
                e.printStackTrace();
            } catch (ParseHttpRequestException e) {
                //bad http request
                e.printStackTrace();
            }


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
        }
    }
}
