package com.yzb.http;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description class Session
 * @Date 2021/2/10 下午4:26
 * @Creater BeckoninGshy
 */
public class StandardSession implements HttpSession {

    private ServletContext servletContext;
    private Map<String,Object> attritubes;
    private String ID;
    private long creationTime;
    private long lastAccessedTime;
    private int maxInactiveInterval;


    public StandardSession(String sessionId, ServletContext servletContext){
        this.ID = sessionId;
        this.servletContext = servletContext;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.attritubes = new ConcurrentHashMap<>();
    }


    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime){
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        this.maxInactiveInterval = i;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }


    @Override
    public Object getAttribute(String s) {
        return attritubes.get(s);
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attritubes.keySet());
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String s, Object o) {
        attritubes.put(s,o);
    }

    @Override
    public void putValue(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {
        attritubes.remove(s);
    }

    @Override
    public void removeValue(String s) {

    }

    @Override
    public void invalidate() {
        attritubes.clear();
        long now = System.currentTimeMillis();
        setMaxInactiveInterval(0);
    }

    @Override
    public boolean isNew() {
        return creationTime == lastAccessedTime;
    }
}
