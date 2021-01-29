package com.yzb;

import com.yzb.exception.ParseHttpRequestException;
import com.yzb.http.HttpConnector;

import java.io.IOException;

/**
 * @Description TinyWebServer app starter
 * @Date 2021/1/26 下午9:20
 * @Creater BeckoninGshy
 */
public class TinyWebServer {
    public static void main(String[] args) throws IOException, ParseHttpRequestException {
        int port = 9090;
        StandardServer standardServer = StandardServer.getServerInstance();
        StandardService standardService = new StandardService();
        HttpConnector httpConnector = new HttpConnector();
        StandardContainer standardContainer = new StandardContainer();

        httpConnector.setPort(9090);
        httpConnector.setProtocol("http");
        httpConnector.setService(standardService);

        standardContainer.setService(standardService);

        standardService.addConnector(httpConnector);
        standardService.setServer(standardServer);
        standardService.setContainer(standardContainer);

        standardServer.addService(standardService);

        standardServer.init();
        standardServer.start();

    }
}
