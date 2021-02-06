package com.yzb.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Date 2021/2/6 下午8:58
 * @Creater BeckoninGshy
 */
public class TestServlet3 extends HelloServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("this is test3 response."); // should be useless.
        req.getRequestDispatcher("/test1").forward(req, resp);
    }
}
