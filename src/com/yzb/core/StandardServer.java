package com.yzb.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.LogFactory;
import cn.hutool.system.SystemUtil;
import com.yzb.common.Server;
import com.yzb.common.ServerContext;
import com.yzb.common.Service;
import com.yzb.exception.LifecycleException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StandardServer implements Server, Runnable {
    private volatile static StandardServer standardServerInstance;
    private int port = -1;
    private String name = "TinyWebServer";
    private String shutdown = "shutdown";
    private String address = "localhost";
    private ClassLoader parentClassLoader;

    private Service[] services = new Service[0];
    private final Object servicesLock = new Object();

    private static ServerSocket serverSocket;

    public StandardServer() {
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

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
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
    public void start() {
        TimeInterval startTimer = DateUtil.timer();
        synchronized (servicesLock) {
            for(Service service : services){
                try {
                    service.start();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                    LogFactory.get().error("Start service{} failed", service.getName());
                }
            }
        }
        LogFactory.get().info("Server startup in {} ms", startTimer.intervalMs());
    }

    @Override
    public void stop() throws LifecycleException {
        for (Service service : services){
            try {
                service.stop();
            } catch (LifecycleException e) {
            }
        }
        LogFactory.get().info("Stopped server{}", getName());
    }

    @Override
    public void destroy() throws LifecycleException {
        for (Service service : services){
            try {
                service.destroy();
            } catch (LifecycleException e) {
            }
        }
        LogFactory.get().info("destroyed Server{}", getName());
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public void init(){
        logJVM();
        synchronized (servicesLock) {
            for (Service service : services){
                try {
                    service.init();
                } catch (LifecycleException e) {
                    LogFactory.get().error("Init service {} failed",service.getName());
                }
            }
            LogFactory.get().info("Init server {}", getName());
        }
    }

    private static void logJVM() {
        Map<String, String> infos = new LinkedHashMap<>();
        infos.put("Server Name", ServerContext.serverName);
        infos.put("Server built", DateUtil.now());
        infos.put("Server version", ServerContext.version);
        infos.put("OS Name\t", SystemUtil.get("os.name"));
        infos.put("OS version", SystemUtil.get("os.version"));
        infos.put("Architecture", SystemUtil.get("java.home"));
        infos.put("Java Home", SystemUtil.get("java home"));
        infos.put("JVM Version", SystemUtil.get("java.runtime.version"));
        infos.put("JVM Vendor", SystemUtil.get("java.vm.specification.vendor"));
        Set<String> keys = infos.keySet();
        for (String key : keys) {
            LogFactory.get().info(key + ":\t\t" + infos.get(key));
        }
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();

            }
        } catch (IOException e) {
            // connector failed
            e.printStackTrace();
        }
    }

}