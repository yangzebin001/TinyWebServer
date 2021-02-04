package com.yzb.core;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description The implementation class of ServletConfig
 * @Date 2021/2/4 下午9:31
 * @Creater BeckoninGshy
 */
public class StandardServletConfig implements ServletConfig {
    private Map<String,String> initParameters;
    private ServletContext servletContext;
    private String servletName;

    public StandardServletConfig(ServletContext servletContext,String servletName,Map<String,String> initParameters){
        this.servletContext = servletContext;
        this.initParameters = initParameters;
        this.servletName = servletName;
    }

    @Override
    public String getServletName() {
        return servletName;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getInitParameter(String s) {
        return initParameters.get(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }
}
