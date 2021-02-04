package com.yzb.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Description
 * @Date 2021/2/2 下午8:40
 * @Creater BeckoninGshy
 */
public class DefaultServlet extends HttpServlet {

    @Override
    public void init(ServletConfig sc) throws ServletException {
        System.out.println(sc.getServletName());
        Enumeration<String> initParameterNames = sc.getInitParameterNames();
        while(initParameterNames.hasMoreElements()){
            String s = initParameterNames.nextElement();
            System.out.println("init name:" + s + ", value:" + sc.getInitParameter(s));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("this is default page");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
