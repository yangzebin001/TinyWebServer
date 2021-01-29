package com.yzb;

/**
 * @Description
 * @Date 2021/1/28 下午10:18
 * @Creater BeckoninGshy
 */
public interface Container extends Lifecycle {

    public String getName();

    public void setName();

    public Container getParent();

    public void setParent(Container parent);

    public ClassLoader getParentClassLoader();

    public void setParentClassLoader(ClassLoader classLoader);

    public void addChild(Container container);

    public Container findChild(String container);

    public Container[] findChildren();

    public void removeChild(Container container);


}
