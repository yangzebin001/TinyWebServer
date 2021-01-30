package com.yzb.core;

import com.yzb.common.StandardContainer;

/**
 * @Description
 * @Date 2021/1/30 下午9:34
 * @Creater BeckoninGshy
 */
public class Engine extends StandardContainer {
    private String defaultHost = "localhost";

    public String getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }
}
