package com.yzb.core;

import cn.hutool.log.LogFactory;
import com.yzb.common.Connector;
import com.yzb.common.Container;
import com.yzb.common.Server;
import com.yzb.common.Service;
import com.yzb.exception.LifecycleException;

/**
 * @Description
 * @Date 2021/1/29 下午1:15
 * @Creater BeckoninGshy
 */
public class StandardService implements Service {

    private Container container;
    private String name = "StandardService";
    private Server server;
    private Connector[] connectors = new Connector[0];
    private final Object connectorsLock = new Object();
    private ClassLoader parentClassLoader;

    @Override
    public void init() throws LifecycleException {
        container.init();
        synchronized (connectorsLock) {
            for(Connector connector : connectors){
                connector.init();
            }
        }
    }

    @Override
    public void start() throws LifecycleException {
        container.start();

        synchronized (connectorsLock) {
            for(Connector connector : connectors){
                connector.start();
            }
        }

        LogFactory.get().info("stared service {}", getName());
    }

    @Override
    public void stop() throws LifecycleException {
        for(Connector connector : connectors){
            connector.stop();
        }
        container.stop();
        LogFactory.get().info("stopped service {}", getName());
    }

    @Override
    public void destroy() throws LifecycleException {
        for(Connector connector : connectors){
            connector.destroy();
        }
        container.destroy();
        LogFactory.get().info("destroyed service {}", getName());
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void addConnector(Connector connector) {
        synchronized (connectorsLock) {
            int len = connectors.length;
            Connector[] newConnectors = new Connector[len+1];
            System.arraycopy(connectors,0,newConnectors,0,len);
            newConnectors[len] = connector;
            connectors = newConnectors;
        }
    }

    @Override
    public Connector findConnector(String name) {
        int len = connectors.length;
        for (Connector value : connectors) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

    @Override
    public String[] getConnectorNames() {
        int len = connectors.length;
        String[] names = new String[len];
        for(int i = 0; i < len; i++){
            names[i] = connectors[i].getName();
        }
        return names;
    }

    @Override
    public Connector[] findConnectors() {
        return connectors;
    }

    @Override
    public void removeConnector(Connector connector) {
        synchronized (connectorsLock) {

            int idx = 0, len = connectors.length;
            while(idx < len && connectors[idx] != connector) idx++;
            if(idx == len) return;
            try {
                connectors[idx].stop();
            } catch (LifecycleException e) {
                //noting to do.
            }

            Connector[] newConnectors = new Connector[len-1];
            System.arraycopy(connectors,0,newConnectors,0,idx);
            System.arraycopy(connectors,idx+1,newConnectors,idx,len-idx-1);
            connectors = newConnectors;
        }
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }
}
