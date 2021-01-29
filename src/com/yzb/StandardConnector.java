package com.yzb;

import com.yzb.common.Request;
import com.yzb.common.Response;
import com.yzb.exception.LifecycleException;

import java.util.Map;

/**
 * @Description
 * @Date 2021/1/29 下午1:17
 * @Creater BeckoninGshy
 */
public class StandardConnector implements Connector{

    private String name;
    private Map<String,String> properties;
    private Service service;
    private int port = -1;
    private String protocol;
    private String scheme;
    private String uriEncoding;
    private boolean useBodyEncodingForURI;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getProperty(String property) {
        return properties.get(property);
    }

    @Override
    public boolean setProperty(String key, String value) {
        properties.put(key,value);
        return true;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getURIEncoding() {
        return uriEncoding;
    }

    @Override
    public void setURIEncoding(String uriEncoding) {
        this.uriEncoding = uriEncoding;
    }

    @Override
    public boolean getUseBodyEncodingForURI() {
        return useBodyEncodingForURI;
    }

    @Override
    public void setUseBodyEncodingForURI(boolean isUse) {
        this.useBodyEncodingForURI = isUse;
    }

    @Override
    public Request createRequest() {
        return null;
    }

    @Override
    public Response createResponse() {
        return null;
    }

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
}
