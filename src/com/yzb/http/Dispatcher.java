package com.yzb.http;

import cn.hutool.core.util.StrUtil;
import com.yzb.classcloader.WebappClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description Parse the url and pass it to the appropriate component, such as a servlet or file
 * @Date 2021/2/2 下午5:15
 * @Creater BeckoninGshy
 */
public class Dispatcher {
    public void dispatch(String url, ApplicationContext appContext, HttpRequest req, HttpResponse resp) throws ClassNotFoundException, IOException, ServletException, IllegalAccessException, InstantiationException {

        // Reduce context path
        if(!appContext.getPath().equals("/"))
            url = url.substring(appContext.getPath().length());

        if(url.length() == 0 || url.equals("/")){ // like "/test" "/test/" --> "" test is an app dir
            System.out.println("is app root url");
        }

        // is servlet url
        if(url.startsWith("/")){
            // app context

            if(appContext.getServletURLToClass(url) != null) {

                WebappClassLoader webappClassLoader = appContext.getWebappClassLoader();
                Class<?> clazz = webappClassLoader.loadClass(appContext.getServletURLToClass(url));
                //get instance servlet from context
                Servlet servlet = appContext.getServlet(clazz);
                //execute service method (auto call method like doGet,doPost etc)process request
                servlet.service(req,resp);
                return;
            }

            // defalut context
            ApplicationContext defaultContext = appContext.getDefaultContext();
            if(defaultContext.getServletURLToClass(url) != null) {

                WebappClassLoader webappClassLoader = defaultContext.getWebappClassLoader();
                Class<?> clazz = webappClassLoader.loadClass(defaultContext.getServletURLToClass(url));
                //get instance servlet from context
                Servlet servlet = defaultContext.getServlet(clazz);
                //execute service method (auto call method like doGet,doPost etc)process request
                servlet.service(req,resp);
                return;
            }
        }
        // is mime type?
        if (StrUtil.contains(url, '.')){
            String ext = StrUtil.subAfter(url, '.', false);
            String type = appContext.getMimeType(ext);
            if(type == null) type = appContext.getDefaultContext().getMimeType(ext);
            if(type != null) {
                String filePath = appContext.getRealPath() + url;
                File file = new File(filePath);
                if(file.exists()){


                    resp.setContentType(type);
                    //default charset: utf-8
                    if(type.startsWith("text"))
                        resp.setCharacterEncoding("utf-8");

                    FileInputStream fileInputStream = new FileInputStream(file);
                    ServletOutputStream outputStream = resp.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = fileInputStream.read(buffer,0,buffer.length)) != -1){
                        outputStream.write(buffer,0,len);
                    }
                    outputStream.close();

                    return;
                }

            }

        }

        // is mismatched.
        PrintWriter writer = resp.getWriter();
        writer.print("this is mismatched page.");
        writer.close();
    }
}
