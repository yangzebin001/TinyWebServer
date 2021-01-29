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
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName() {

    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public void setServer(Server server) {

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
        return new Connector[0];
    }

    @Override
    public void removeConnector(Connector connector) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {

    }
}
