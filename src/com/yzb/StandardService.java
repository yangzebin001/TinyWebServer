package com.yzb;

import com.yzb.Connector;
import com.yzb.Container;
import com.yzb.Server;
import com.yzb.Service;
import com.yzb.exception.LifecycleException;

/**
 * @Description
 * @Date 2021/1/29 下午1:15
 * @Creater BeckoninGshy
 */
public class StandardService implements Service {

    private Container container;
    private String name;
    private Server server;
    private Connector[] connectors = new Connector[0];
    private ClassLoader parentClassLoader;

    @Override
    public void init() throws LifecycleException {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public void destroy() throws LifecycleException {

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
        int len = connectors.length;
        Connector[] newConnectors = new Connector[len+1];
        System.arraycopy(connectors,0,newConnectors,0,len);
        newConnectors[len] = connector;
        connectors = newConnectors;
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

    @Override
    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }
}
