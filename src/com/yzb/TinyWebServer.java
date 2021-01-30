package com.yzb;

import com.yzb.common.Server;
import com.yzb.common.StandardContainer;
import com.yzb.common.StandardServer;
import com.yzb.common.StandardService;
import com.yzb.exception.LifecycleException;
import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpConnector;
import com.yzb.util.ServerXMLParser;

import java.io.IOException;

/**
 * @Description TinyWebServer app starter
 * @Date 2021/1/26 下午9:20
 * @Creater BeckoninGshy
 */
public class TinyWebServer {
    public static void main(String[] args) throws LifecycleException {
        Server server = ServerXMLParser.getServerWithAutoPack();
        server.init();
        server.start();
    }
}
