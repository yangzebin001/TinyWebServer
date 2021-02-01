package com.yzb.core;

import com.yzb.common.Container;
import com.yzb.common.StandardContainer;
import com.yzb.common.StandardServletContext;
import com.yzb.exception.LifecycleException;
import com.yzb.http.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Date 2021/1/30 下午9:34
 * @Creater BeckoninGshy
 */
public class Engine extends StandardContainer {
    private String defaultHostName;

    public String getDefaultHostName() {
        return defaultHostName;
    }

    public Host getDefaultHost(){
        if(defaultHostName == null) return null;
        for(Container child : children){
            if(child instanceof Host && defaultHostName.equals(child.getName())){
                return (Host)child;
            }
        }
        return null;
    }

    public void setDefaultHostName(String defaultHostName) {
        this.defaultHostName = defaultHostName;

    }

    @Override
    public void init() throws LifecycleException {
        loadContext();
        super.init();
    }

    public void loadContext(){
        String contextsDirectory = getDefaultHost().getAppBase();
        File contexts = new File(contextsDirectory);
        if(!contexts.exists() || !contexts.isDirectory()) return;
        File[] files = contexts.listFiles();
        List<ApplicationContext> contextList = new ArrayList<>();
        for(File file : files){
            if(file.isDirectory()){
                ApplicationContext t = new ApplicationContext();
                if("ROOT".equals(file.getName())){
                    t.setPath("/");
                }else{
                    t.setPath("/" + file.getName());
                }
                contextList.add(t);
            }
        }
        for(ApplicationContext servletContext : contextList){
            servletContext.setParent(getDefaultHost());
            getDefaultHost().addChild(servletContext);
        }
    }
}
