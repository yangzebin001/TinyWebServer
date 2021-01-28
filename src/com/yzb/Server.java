package com.yzb;

/**
 * @Description
 * @Date 2021/1/28 下午10:31
 * @Creater BeckoninGshy
 */
public interface Server {

    public String getName();

    public void setName();

    public int getPort();

    public void setPort(int port);

    public String getAddress();

    public void setAddress(String address);

    public String getShutdown();

    public void setShutdown(String shutdown);

    public void addService(Service service);

    public Service findService(String service);

    public Service[] findServices();

    public String[] getServiceNames();

    public void removeService(Service service);

    public ClassLoader getParentClassLoader();

    public void setParentClassLoader(ClassLoader classLoader);


}
