package com.yzb.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @Date 2021/2/2 下午5:41
 * @Creater BeckoninGshy
 */
public class HelloServlet extends HttpServlet {

    @Override
    public void destroy() {
        System.out.println("hello servlet is destroyed");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("hello servlet is init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("this is HelloSerlvet response");
        writer.close();
    }
}
