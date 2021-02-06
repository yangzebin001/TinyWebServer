package com.yzb.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Description Defines an object that receives requests from the client and sends them to any resource (relative )
 * @Date 2021/2/6 下午7:57
 * @Creater BeckoninGshy
 */
public class ApplicationRequestDispatcher implements RequestDispatcher {

    private String uri;
    private ApplicationContext appContext = null;

    public ApplicationRequestDispatcher(String uri){
        if(uri.startsWith("/"))
            this.uri = uri;
        else this.uri = "/" + uri;
    }

    public ApplicationRequestDispatcher(String uri, ApplicationContext appContext){
        this(uri);
        this.appContext = appContext;
    }

    @Override
    public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpRequest req= (HttpRequest) servletRequest;
        HttpResponse resp = (HttpResponse) servletResponse;
        HttpProcessor hp = new HttpProcessor();
        if(appContext != null) req.setServletContext(appContext);
        req.setForwardURI(uri);
        req.setForwarding();
        resp.resetBuffer();
        hp.execute(req.getSocket(), req, resp);
        req.setForwarded();
    }

    @Override
    public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
}
