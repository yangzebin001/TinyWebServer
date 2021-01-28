package com.yzb;

import com.yzb.common.Request;
import com.yzb.common.Response;

/**
 * @Description
 * @Date 2021/1/28 下午10:17
 * @Creater BeckoninGshy
 */
public interface Connector {

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

    public String getScheme();

    public void etScheme(String scheme);

    public String getURIEncoding();

    public void setURIEncoding(String uriEncoding);

    public boolean getUseBodyEncodingForURI();

    public void setUseBodyEncodingForURI(boolean isUse);

    public Request createRequest();

    public Response createResponse();


}
