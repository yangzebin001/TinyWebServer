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

            new Dispatcher().dispatch(request.getRequestURI(),appContext, request, response);


        }  catch (Exception e) {
            //500
            e.printStackTrace();
        } finally {
            try {
                if(!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
