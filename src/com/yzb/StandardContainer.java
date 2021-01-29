package com.yzb;

import com.yzb.exception.LifecycleException;

/**
 * @Description
 * @Date 2021/1/29 下午1:17
 * @Creater BeckoninGshy
 */
public class StandardContainer implements Container{
    private String name;
    private Service service;
    private Container parent;
    private ClassLoader parentClassLoader;
    private Container[] children = new Container[0];

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {
        this.parentClassLoader = classLoader;
    }

    @Override
    public void addChild(Container container) {
        int len = children.length;
        Container[] newChildren = new Container[len+1];
        System.arraycopy(children,0,newChildren,0,len);
        newChildren[len] = container;
        children = newChildren;
    }

    @Override
    public Container findChild(String container) {
        int len = children.length;
        for (Container value : children) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

    @Override
    public Container[] findChildren() {
        return children;
    }

    @Override
    public void removeChild(Container container) {
        int idx = 0, len = children.length;
        while(idx < len && children[idx] != container) idx++;
        if(idx == len) return;
        try {
            children[idx].stop();
        } catch (LifecycleException e) {
            //noting to do.
        }
        Container[] newContainers = new Container[len-1];
        System.arraycopy(children,0,newContainers,0,idx);
        System.arraycopy(children,idx+1,newContainers,idx,len-idx-1);
        children = newContainers;
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
