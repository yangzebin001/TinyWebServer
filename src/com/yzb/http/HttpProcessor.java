package com.yzb.http;


import cn.hutool.core.util.StrUtil;
import cn.hutool.log.LogFactory;
import com.yzb.classcloader.WebappClassLoader;
import com.yzb.exception.URLMismatchedExpection;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Description processor one http request
 * @Date 2021/1/26 下午10:00
 * @Creater BeckoninGshy
 */
public class HttpProcessor {

    public void execute(Socket socket, HttpRequest request, HttpResponse response){
        try{
            LogFactory.get().info("receiving from {}, request: {}", request.getRemoteAddr(), request.getRequestURI());

            ApplicationContext appContext = (ApplicationContext) request.getServletContext();
            new Dispatcher().dispatch(request.getRequestURI(), appContext, request, response);

        }  catch (URLMismatchedExpection e) {
            //404
            handle404(request.getRequestURI(), response, e.getMessage());
        } catch (Exception e) {
            //500
            handle500(request.getRequestURI(), response, e.getMessage());
        } finally {
            try {
                if(!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void handle404(String url, HttpResponse resp, String message) {
        LogFactory.get().info("{} is 404", url);
        try {
            resp.sendError(404, StrUtil.format(HttpContant.textFormat_404, url, message));
        } catch (IOException e) {
        }
    }

    private void handle500(String url, HttpResponse resp, String message) {
        LogFactory.get().info("{} is 500", url);
        try {
            resp.sendError(500, StrUtil.format(HttpContant.textFormat_500, url, message));
        } catch (IOException e) {
        }
    }

}
