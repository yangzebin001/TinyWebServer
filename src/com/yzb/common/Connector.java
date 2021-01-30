package com.yzb.common;

import java.net.Socket;

/**
 * @Description
 * @Date 2021/1/28 下午10:17
 * @Creater BeckoninGshy
 */
public interface Connector extends Lifecycle {

    public String getName();

    public void setName(String name);

    public String getProperty(String property);

    public boolean setProperty(String key, String value);

    public Service getService();

    public void setService(Service service);

    public int getPort();

    public void setPort(int port);

    public String getProtocol();

    public void setProtocol(String protocol);

    public void setConnectionTimeout(long timeout);

    public long getConnectionTimeout();

    public String getScheme();

    public void setScheme(String scheme);

    public String getURIEncoding();

    public void setURIEncoding(String uriEncoding);

    public boolean getUseBodyEncodingForURI();

    public void setUseBodyEncodingForURI(boolean isUse);

    public Request createRequest(Socket socket);

    public Response createResponse(Socket socket);


}
