package com.yzb.core;

import com.yzb.common.Container;
import com.yzb.common.StandardContainer;

/**
 * @Description
 * @Date 2021/1/30 下午9:34
 * @Creater BeckoninGshy
 */
public class Engine extends StandardContainer {
    private String defaultHostName;

    public String getDefaultHostName() {
        return defaultHostName;
    }

    public Host getDefaultHost(){
        if(defaultHostName == null) return null;
        for(Container child : children){
            if(child instanceof Host && defaultHostName.equals(child.getName())){
                return (Host)child;
            }
        }
        return null;
    }

    public void setDefaultHostName(String defaultHostName) {
        this.defaultHostName = defaultHostName;

    }
}
