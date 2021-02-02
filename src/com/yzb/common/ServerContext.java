package com.yzb.common;

import java.io.File;

/**
 * @Description
 * @Date 2021/1/30 下午10:01
 * @Creater BeckoninGshy
 */
public class ServerContext {

    public static final String serverName = "TinyWebServer";

    public static final String version = "0.0.1";

    public static final String serverBasePath = System.getProperty("user.dir").toString();

    public static final String serverConfigDir = serverBasePath + File.separator + "conf";

    public static final String serverXMLPath = serverConfigDir + File.separator + "server.xml";

    public static final String webXMLPath = serverConfigDir+ File.separator + "web.xml";

    public static final String servletLoadClassDir = serverConfigDir+ File.separator + "servlets";

}
