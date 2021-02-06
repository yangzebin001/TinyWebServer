package com.yzb.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @Date 2021/2/6 下午8:58
 * @Creater BeckoninGshy
 */
public class TestServlet2 extends HelloServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/test1").forward(req, resp);
        req.getServletContext().getContext("/")
                .getRequestDispatcher("/b/a.html").forward(req,resp);
    }
}
