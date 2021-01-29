package com.yzb;



/**
 * @Description
 * @Date 2021/1/28 下午10:07
 * @Creater BeckoninGshy
 */
public interface Service extends Lifecycle {


    public Container getContainer();

    public void setContainer(Container container);

    public String getInfo();

    public String getName();

    public void setName();

    public Server getServer();

    public void setServer(Server server);

    public void addConnector(Connector connector);

    public Connector findConnector(String name);

    public String[] getConnectorNames();

    public Connector[] findConnectors();

    public void removeConnector(Connector connector);

    public ClassLoader getParentClassLoader();

    public void setParentClassLoader(ClassLoader classLoader);
}
