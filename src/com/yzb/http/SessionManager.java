package com.yzb.http;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.yzb.common.Request;
import com.yzb.common.Response;
import com.yzb.common.ServerContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.print.Doc;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Date 2021/2/10 下午4:40
 * @Creater BeckoninGshy
 */
public class SessionManager {
    private static Map<String, StandardSession> sessionMap = new ConcurrentHashMap<>();

    private static int defaultTimeout = getTimeout();
    private static Thread checkoutThread;

    static{
        startCheckOutSessionOutDateThread();
    }

    //启动线程，每隔3s调用一次checkoutdatesession方法
    private static void startCheckOutSessionOutDateThread() {
        checkoutThread = new Thread(){
            public void run(){
                while(true){
                    checkOutDateSession();
                    ThreadUtil.sleep(1000*3);
                }
            }
        };
        checkoutThread.start();
    }

    private static void checkOutDateSession() {
        Set<String> sessionId = sessionMap.keySet();
        List<String> outdateJessionIds = new ArrayList<>();
        for(String id : sessionId){
            StandardSession standardSession = sessionMap.get(id);
            long interval = System.currentTimeMillis() - standardSession.getLastAccessedTime();
            if(standardSession.getMaxInactiveInterval() == -1) continue;
            if(interval > standardSession.getMaxInactiveInterval() * 1000L){
                outdateJessionIds.add(id);
            }
        }
        for(String id : outdateJessionIds){
            sessionMap.remove(id);
        }
    }


    private static int getTimeout(){
        int defautlTime = 30;
        Document document = Jsoup.parse(FileUtil.readUtf8String(ServerContext.webXMLPath));
        Elements select = document.select("session-config session-timeout");
        if(select.isEmpty()) return defautlTime;
        return Convert.toInt(select.get(0).text());
    }


    public static void destroy(){
        checkoutThread.interrupt();
        try {
            checkoutThread.join();
        } catch (InterruptedException e) {
        }
    }

    // 如果浏览器没有传jsessionid过来，就创建一个新的session
    // 如果浏览器传递过来的jsessionid无效，那么也创建一个新的sessionid
    // 否则就使用现成的session，并修改他的lastAccessedTime，以及创建对应的cookie
    public static HttpSession getSession(String jsessionid, Request request, Response response) {
        if (null == jsessionid) {
            return newSession(request, response);
        } else {
            StandardSession currentSession = sessionMap.get(jsessionid);
            if (null == currentSession) {
                return newSession(request, response);
            } else {
                currentSession.setLastAccessedTime(System.currentTimeMillis());
                createCookieBySession(currentSession, request, response);
                return currentSession;
            }
        }
    }


    private static void createCookieBySession(HttpSession session, Request request, Response response) {
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(session.getMaxInactiveInterval());
        cookie.setPath(request.getServletContext().getContextPath());
        response.addCookie(cookie);
    }


    public static StandardSession newSession(Request request, Response response){
        ServletContext servletContext = request.getServletContext();
        String sessionId = generateSessionId();
        StandardSession standardSession = new StandardSession(sessionId, servletContext);
        standardSession.setMaxInactiveInterval(defaultTimeout);
        sessionMap.put(sessionId,standardSession);
        //create cookie
        createCookieBySession(standardSession,request,response);
        return standardSession;
    }


    public static synchronized String generateSessionId() {
        String result = null;
        byte[] bytes = RandomUtil.randomBytes(16);
        result = new String(bytes);
        result = SecureUtil.md5(result);
        result = result.toUpperCase();
        return result;
    }

}
