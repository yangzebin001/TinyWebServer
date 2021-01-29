package com.yzb;

import com.yzb.common.Request;
import com.yzb.common.Response;
import com.yzb.exception.LifecycleException;

/**
 * @Description
 * @Date 2021/1/29 下午1:17
 * @Creater BeckoninGshy
 */
public class StandardConnector implements Connector{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getProperty(String property) {
        return null;
    }

    @Override
    public boolean setProperty(String key, String value) {
        return false;
    }

    @Override
    public Service getService() {
        return null;
    }

    @Override
    public void setService(Service service) {

    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public void setPort(int port) {

    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public void etScheme(String scheme) {

    }

    @Override
    public String getURIEncoding() {
        return null;
    }

    @Override
    public void setURIEncoding(String uriEncoding) {

    }

    @Override
    public boolean getUseBodyEncodingForURI() {
        return false;
    }

    @Override
    public void setUseBodyEncodingForURI(boolean isUse) {

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
