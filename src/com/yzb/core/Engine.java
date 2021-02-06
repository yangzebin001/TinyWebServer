package com.yzb.core;

import com.yzb.common.Container;
import com.yzb.common.ServerContext;
import com.yzb.exception.LifecycleException;
import com.yzb.http.ApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
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
        ApplicationContext defaultContext = null;
        try {
            defaultContext = loadDefaultContext();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            stop();
        }
        loadUserContext(defaultContext);
        super.init();
    }

    private ApplicationContext loadDefaultContext() throws FileNotFoundException {
        String contextsDirectory = ServerContext.webXMLPath;
        File contextFile = new File(contextsDirectory);
        if(!contextFile.exists())
            throw new FileNotFoundException("There is no web.xml in conf dir.");

        ApplicationContext defaultContext = new ApplicationContext(ServerContext.servletLoadClassDir,true);
        defaultContext.setPath("");
        defaultContext.setParent(getDefaultHost());
        getDefaultHost().addChild(defaultContext);
        defaultContext.setService(getService());
        return defaultContext;
    }


    private void loadUserContext(ApplicationContext defaultContext){
        String contextsDirectory = getDefaultHost().getAppBase();
        File contexts = new File(contextsDirectory);
        if(!contexts.exists() || !contexts.isDirectory()) return;
        File[] files = contexts.listFiles();
        List<ApplicationContext> contextList = new ArrayList<>();
        for(File file : files){
            if(file.isDirectory()){
                ApplicationContext t = new ApplicationContext(file.getAbsolutePath(),false);
                if("ROOT".equals(file.getName())){
                    t.setPath("/");
                }else{
                    t.setPath("/" + file.getName());
                }
                t.setName(file.getName());
                contextList.add(t);
            }
        }
        for(ApplicationContext servletContext : contextList){
            servletContext.setDefaultContext(defaultContext);
            servletContext.setParent(getDefaultHost());
            getDefaultHost().addChild(servletContext);
            servletContext.setService(getService());
        }
    }

}
