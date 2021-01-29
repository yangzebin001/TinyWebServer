package com.yzb;

import com.yzb.exception.LifecycleException;

/**
 * @Description
 * @Date 2021/1/29 下午1:17
 * @Creater BeckoninGshy
 */
public class StandardContainer implements Container{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName() {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container parent) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void addChild(Container container) {

    }

    @Override
    public Container findChild(String container) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public void removeChild(Container container) {

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
