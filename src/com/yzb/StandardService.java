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
    private Connector[] connectors;
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

    }

    @Override
    public Connector findConnector(String name) {
        return null;
    }

    @Override
    public String[] getConnectorNames() {
        return new String[0];
    }

    @Override
    public Connector[] findConnectors() {
        return connectors;
    }

    @Override
    public void removeConnector(Connector connector) {

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
