package com.yzb.http;

import cn.hutool.log.LogFactory;
import com.yzb.common.CommonThreadPool;
import com.yzb.common.Connector;
import com.yzb.common.StandardConnector;
import com.yzb.exception.LifecycleException;
import com.yzb.exception.ParseHttpRequestException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description
 * @Date 2021/1/29 下午10:13
 * @Creater BeckoninGshy
 */
public class HttpConnector extends StandardConnector implements Runnable {

    private Thread workThread;

    @Override
    public void init() throws LifecycleException {
        super.init();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LogFactory.get().error("init Connector{} failed, port {} is used", getName(), getPort());
            throw new LifecycleException();
        }
    }

    @Override
    public void start() throws LifecycleException {
        super.start();
        // start a thread to handle connecting
        workThread = new Thread(this);
        workThread.start();
    }

    @Override
    public void stop() throws LifecycleException {
        super.stop();
        workThread.interrupt();
        try{
            workThread.join(1000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void destroy() throws LifecycleException {
        super.destroy();
    }

    @Override
    public HttpRequest createRequest(Socket socket, Connector connector) {
        try {
            return new HttpRequest(socket, connector);
        } catch (ParseHttpRequestException e) {
            e.printStackTrace();
            //bad request
        }
        return null;
    }

    @Override
    public HttpResponse createResponse(Socket socket) {
        return new HttpResponse(socket);
    }

    @Override
    public void run() {
        while(true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Socket finalSocket = socket;
            Runnable runnable = () -> {
                HttpRequest request = createRequest(finalSocket, this);
                HttpResponse response = createResponse(finalSocket);

                LogFactory.get().info("receiving from {}, request: {}", request.getRemoteAddr(), request.getRequestURI());

                HttpProcessor httpProcessor = new HttpProcessor();
                httpProcessor.execute(finalSocket, request, response);
            };
            //add connector to thread pool
            CommonThreadPool.run(runnable);
        }
    }
}
