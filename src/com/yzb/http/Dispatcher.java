package com.yzb.http;

import cn.hutool.core.util.StrUtil;
import com.yzb.classcloader.WebappClassLoader;
import com.yzb.exception.URLMismatchedExpection;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.*;

/**
 * @Description Parse the url and pass it to the appropriate component, such as a servlet or file
 * @Date 2021/2/2 下午5:15
 * @Creater BeckoninGshy
 */
public class Dispatcher {
    public void dispatch(String url, ApplicationContext appContext, HttpRequest req, HttpResponse resp) throws ClassNotFoundException, IOException, ServletException, IllegalAccessException, InstantiationException, URLMismatchedExpection, Exception {

        // Reduce context path
        if(!appContext.getPath().equals("/"))
            url = url.substring(appContext.getPath().length());

        // is app root url, match welcome-file-list config;
        if(url.length() == 0 || url.equals("/")){ // like "/test" "/test/" --> "" test is an app dir
            InputStream is = appContext.getWelcomFile();
            if(is != null){
                handleWriteFile(is, resp);
                return;
            }
        }

        // is servlet url
        if(url.startsWith("/")){
            // app context --> default context
            ApplicationContext curContext = appContext;
            if(appContext.getServletURLToClass(url) == null) {
                curContext = curContext.getDefaultContext();
            }

            if(curContext.getServletURLToClass(url) != null) {
                WebappClassLoader webappClassLoader = curContext.getWebappClassLoader();
                Class<?> clazz = webappClassLoader.loadClass(curContext.getServletURLToClass(url));
                //get instance servlet from context
                Servlet servlet = curContext.getServlet(clazz);
                //execute service method (auto call method like doGet,doPost etc)process request
                servlet.service(req,resp);
                return;
            }
        }
        // is mime type?
        if (StrUtil.contains(url, '.')){
            String ext = StrUtil.subAfter(url, '.', false);
            String type = appContext.getMimeType(ext);
            if(type != null) {
                //get resource succeeded.
                InputStream is = appContext.getResourceAsStream(url);
                if(is != null){
                    resp.setContentType(type);
                    //default charset: utf-8
                    if(type.startsWith("text")) {
                        resp.setCharacterEncoding("utf-8");
                    }

                    handleWriteFile(is, resp);
                    return;
                }
            }

        }

        // is mismatched.
        throw new URLMismatchedExpection(url);
    }


    private void handleWriteFile(InputStream is, HttpResponse resp) throws IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer,0,buffer.length)) != -1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
    }
}
