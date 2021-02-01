package com.yzb.core;

import com.yzb.common.ServerContext;
import com.yzb.common.StandardContainer;

import java.io.File;

/**
 * @Description
 * @Date 2021/1/30 下午9:36
 * @Creater BeckoninGshy
 */
public class Host extends StandardContainer {

    private String appBase;

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        if(appBase.startsWith(File.separator))
            this.appBase = ServerContext.serverBasePath + appBase;
        else this.appBase = ServerContext.serverBasePath + File.separator + appBase;
    }
}
