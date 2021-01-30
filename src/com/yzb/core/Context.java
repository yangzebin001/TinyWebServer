package com.yzb.core;

import com.yzb.common.StandardContainer;

import java.io.File;

/**
 * @Description a Context represents an app under webapps folder
 * @Date 2021/1/30 下午9:37
 * @Creater BeckoninGshy
 */
public class Context extends StandardContainer {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealPath(){
        return new File(path).getAbsolutePath();
    }
}
