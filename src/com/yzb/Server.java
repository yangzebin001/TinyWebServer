package com.yzb;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.LogFactory;
import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpProcessor;
import com.yzb.http.HttpRequest;
import com.yzb.http.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private volatile static Server serverInstance;
    private int port;

    private static ServerSocket serverSocket;

    private Server() {

    }

    public static Server getServerInstance() {
        if (serverInstance == null) {
            synchronized (Server.class) {
                if (serverInstance == null) {
                    serverInstance = new Server();
                }
            }
        }
        return serverInstance;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void start() {
        TimeInterval startTimer = DateUtil.timer();
        init();
        LogFactory.get().info("Server startup in {} ms", startTimer.intervalMs());
    }

    public void init(){
        Thread service = new Thread(this);
        service.start();
        LogFactory.get().info("Initializing service [http-bio-{}]",port);
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);

            while (true) {

                Socket socket = serverSocket.accept();
                Runnable runnable = () -> {

                    try {
                        HttpRequest httpRequest = new HttpRequest(socket);
                        HttpResponse httpResponse = new HttpResponse(socket);
                        HttpProcessor httpProcessor = new HttpProcessor();

                        httpProcessor.execute(socket, httpRequest, httpResponse);
                    } catch (ParseHttpRequestException e) {
                        //bad request
                        e.printStackTrace();
                    }

                };
                //add connector to thread pool
                ConnectorThreadPool.run(runnable);

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
        } catch (IOException e) {
            // connector failed
            e.printStackTrace();
        }
    }

}