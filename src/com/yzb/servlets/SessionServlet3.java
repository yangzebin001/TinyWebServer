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
public class SessionServlet3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
//        session.invalidate();
        System.out.println(session.isNew());
        PrintWriter writer = resp.getWriter();
        writer.println("this is SessionServlet3 response");
        writer.close();
    }
}