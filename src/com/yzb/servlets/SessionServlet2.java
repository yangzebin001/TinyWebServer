package com.yzb.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @Date 2021/2/10 下午10:30
 * @Creater BeckoninGshy
 */
public class SessionServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);
        PrintWriter writer = resp.getWriter();
        writer.println("this is SessionServlet2 response");
        writer.close();
    }
}