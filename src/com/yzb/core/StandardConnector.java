package com.yzb.core;

import com.yzb.common.*;
import com.yzb.exception.LifecycleException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @Description
 * @Date 2021/1/29 下午1:17
 * @Creater BeckoninGshy
 */
public class StandardConnector implements Connector {

    protected String name;
    protected Map<String,String> properties;
    protected Service service;
    protected int port = -1;
    protected String protocol;
    private String scheme;
    protected long connectionTimeout = 0;
    private String uriEncoding;
    private boolean useBodyEncodingForURI;
    protected static ServerSocket serverSocket;

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
    public void setConnectionTimeout(long timeout) {
        this.connectionTimeout = timeout;
    }

    @Override
    public long getConnectionTimeout() {
        return connectionTimeout;
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
    public Request createRequest(Socket socket, Connector connector) {
        return null;
    }

    @Override
    public Response createResponse(Socket socket) {
        return null;
    }

    @Override
    public Server getServer() {
        return service.getServer();
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
