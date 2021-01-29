package com.yzb;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.LogFactory;
import com.yzb.exception.LifecycleException;
import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpProcessor;
import com.yzb.http.HttpRequest;
import com.yzb.http.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class StandardServer implements Server, Runnable {
    private volatile static StandardServer standardServerInstance;
    private int port;
    private String name;
    private String shutdown;
    private String address;
    private ClassLoader parentClassLoader;

    private Service[] services = new Service[0];

    private static ServerSocket serverSocket;

    private StandardServer() {
    }

    public static StandardServer getServerInstance() {
        if (standardServerInstance == null) {
            synchronized (StandardServer.class) {
                if (standardServerInstance == null) {
                    standardServerInstance = new StandardServer();
                }
            }
        }
        return standardServerInstance;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getShutdown() {
        return shutdown;
    }

    @Override
    public void setShutdown(String shutdown) {
        this.shutdown = shutdown;
    }

    @Override
    public void addService(Service service) {
        int len = services.length;
        Service[] newServices = new Service[len+1];
        System.arraycopy(services,0,newServices,0,len);
        newServices[len] = service;
        services = newServices;
    }

    @Override
    public Service findService(String service) {
        int len = services.length;
        for (Service value : services) {
            if (value.getName().equals(service)) return value;
        }
        return null;
    }

    @Override
    public Service[] findServices() {
        return services;
    }

    @Override
    public String[] getServiceNames() {
        int len = services.length;
        String[] names = new String[len];
        for(int i = 0; i < len; i++){
            names[i] = services[i].getName();
        }
        return names;
    }

    @Override
    public void removeService(Service service) {
        int idx = 0, len = services.length;
        while(idx < len && services[idx] != service) idx++;
        if(idx == len) return;
        try {
            services[idx].stop();
        } catch (LifecycleException e) {
            //noting to do.
        }
        Service[] newServices = new Service[len-1];
        System.arraycopy(services,0,newServices,0,idx);
        System.arraycopy(services,idx+1,newServices,idx,len-idx-1);
        services = newServices;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void start() {
        TimeInterval startTimer = DateUtil.timer();
        init();
        LogFactory.get().info("Server startup in {} ms", startTimer.intervalMs());
    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public String getState() {
        return null;
    }

    @Override
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