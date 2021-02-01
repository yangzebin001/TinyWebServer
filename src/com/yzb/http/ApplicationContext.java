package com.yzb.http;

import com.yzb.common.StandardServletContext;

/**
 * @Description
 * @Date 2021/2/1 下午8:08
 * @Creater BeckoninGshy
 */
public class ApplicationContext extends StandardServletContext {

    @Override
    public String getContextPath() {
        return getPath();
    }
}
