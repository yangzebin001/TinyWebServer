package com.yzb.core;

import com.yzb.common.ServerContext;
import com.yzb.common.StandardContainer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description The Context element represents a web application under webapps folder.
 * @Date 2021/1/30 下午9:37
 * @Creater BeckoninGshy
 */
public class Context extends StandardContainer {

    private Map<String,Object> attributes = new ConcurrentHashMap<>();
    private String path;

    public Context(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealPath(){
        if(getPath().equals("/")) return ((Host)parent).getAppBase() + File.separator + "ROOT";
        return ((Host)parent).getAppBase() + getPath();
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value){
        attributes.put(key, value);
    }

    public void removeAttribute(String key){
        attributes.remove(key);
    }
}
