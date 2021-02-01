package com.yzb.http;


import com.yzb.classcloader.WebappClassLoader;

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

            ApplicationContext appContext = (ApplicationContext) request.getServletContext();
            WebappClassLoader webappClassLoader = appContext.getWebappClassLoader();
            String servletURL = request.getRequestURI().substring(appContext.getPath().length());

            if(servletURL.startsWith("/") && appContext.getServletURLToClass(servletURL) != null){
                Class<?> clazz = webappClassLoader.loadClass(appContext.getServletURLToClass(servletURL));
                Servlet servlet = appContext.getServlet(clazz);
                servlet.service(request,response);
            }else{
                PrintWriter writer = response.getWriter();
                writer.print("this is common page.");
                writer.close();
            }


        }  catch (Exception e) {
            //500
            e.printStackTrace();
        } finally {
            try {
                if(!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
                //send 500
                e.printStackTrace();
            }
        }


    }

}
