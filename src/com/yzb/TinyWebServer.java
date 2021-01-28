package com.yzb;

import com.yzb.exception.ParseHttpRequestException;

import java.io.IOException;

/**
 * @Description TinyWebServer app starter
 * @Date 2021/1/26 下午9:20
 * @Creater BeckoninGshy
 */
public class TinyWebServer {
    public static void main(String[] args) throws IOException, ParseHttpRequestException {
        StandardServer standardServer = StandardServer.getServerInstance();
        int port = 9090;
        standardServer.setPort(port);
        standardServer.start();

    }
}
