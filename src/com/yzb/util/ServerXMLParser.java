package com.yzb.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import com.yzb.common.*;
import com.yzb.core.Engine;
import com.yzb.core.Host;
import com.yzb.http.HttpConnector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @Description
 * @Date 2021/1/30 下午9:58
 * @Creater BeckoninGshy
 */
public class ServerXMLParser {
    private static String xml = FileUtil.readUtf8String(ServerContext.serverXMLPath);
    public static Server getServer(){
        StandardServer server = StandardServer.getServerInstance();
        Document document = Jsoup.parse(xml);
        Element element = document.select("Server").first();
        int port = Convert.toInt(element.attr("port"));
        String shutdown = element.attr("shutdown");
        server.setPort(port);
        server.setShutdown(shutdown);
        server.setName(ServerContext.serverName);
        return server;
    }

    public static Service[] getServices(){
        Document document = Jsoup.parse(xml);
        Elements elements = document.select("Service");
        StandardService[] service = new StandardService[elements.size()];
        for(int i = 0; i < elements.size(); i++){
            String name = elements.get(i).attr("name");
            service[i] = new StandardService();
            service[i].setName(name);
        }
        return service;
    }

    public static Connector[] getConnectors(String serviceName){
        Document document = Jsoup.parse(xml);
        Elements elements = document.select("Service");

        StandardService[] service = new StandardService[elements.size()];
        StandardConnector[] connectors = null;
        for(Element element : elements){
            String name = element.attr("name");
            if (name.equals(serviceName)) {
                Elements connectorEles = element.select("Connector");
                connectors = new StandardConnector[connectorEles.size()];
                for(int i = 0; i < connectorEles.size(); i++){

                    int port = Convert.toInt(connectorEles.get(i).attr("port"));
                    String protocol = connectorEles.get(i).attr("protocol");
                    long connectionTimeout = Convert.toLong(connectorEles.get(i).attr("connectionTimeout"));

                    //to do better.
                    if("HTTP/1.1".equals(protocol)){
                        connectors[i] = new HttpConnector();
                    }else{
                        connectors[i] = new StandardConnector();
                    }


                    connectors[i].setPort(port);
                    connectors[i].setProtocol(protocol);
                    connectors[i].setConnectionTimeout(connectionTimeout);
                }
                break;
            }
        }
        return connectors;
    }
    public static Engine getEngine(String serviceName){
        Document document = Jsoup.parse(xml);
        Elements elements = document.select("Service");
        StandardService[] service = new StandardService[elements.size()];
        Engine engine = null;
        for (Element element : elements) {
            String name = element.attr("name");
            if (name.equals(serviceName)) {
                Element engineEle = element.select("Engine").first();
                String engineName = engineEle.attr("name");
                String defaultHost = engineEle.attr("defaultHost");
                engine = new Engine();
                engine.setName(engineName);
                engine.setDefaultHostName(defaultHost);
                break;
            }
        }
        return engine;
    }


    public static Host[] getHost(String engineName){
        Document document = Jsoup.parse(xml);
        Elements elements = document.select("Engine");
        Host[] hosts = null;
        for (Element element: elements){
            String name = element.attr("name");
            if (name.equals(engineName)) {
                Elements hostEles = element.select("Host");
                hosts = new Host[hostEles.size()];
                for(int i = 0; i < hostEles.size(); i++){
                    String hostName = hostEles.get(i).attr("name");
                    String appBase = hostEles.get(i).attr("appBase");
                    System.out.println(appBase);
                    hosts[i] = new Host();
                    hosts[i].setName(hostName);
                    hosts[i].setAppBase(appBase);
                }
                break;
            }
        }
        return hosts;
    }


    public static Server getServerWithAutoPack(){
        Server server = getServer();
        Service[] services = getServices();
        for(Service service : services){
            service.setServer(server);
            server.addService(service);
            Connector[] connectors = getConnectors(service.getName());
            for(Connector connector : connectors){
                connector.setService(service);
                service.addConnector(connector);
            }
            Engine engine = getEngine(service.getName());
            engine.setService(service);
            service.setContainer(engine);

            Host[] hosts = getHost(engine.getName());
            for(Host host : hosts){
                host.setService(service);
                host.setParent(engine);

                engine.addChild(host);
            }
        }

        return server;

    }

}
