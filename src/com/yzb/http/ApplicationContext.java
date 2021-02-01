package com.yzb.http;

import cn.hutool.core.io.FileUtil;
import com.yzb.classcloader.WebappClassLoader;
import com.yzb.common.StandardServletContext;
import com.yzb.exception.LifecycleException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Date 2021/2/1 下午8:08
 * @Creater BeckoninGshy
 */
public class ApplicationContext extends StandardServletContext {

    private Map<String,String> servletNameToClass = new HashMap<>();
    private Map<String,String> servletClassToName = new HashMap<>();
    private Map<String,String> servletNameToURL   = new HashMap<>();
    private Map<String,String> servletURLToName   = new HashMap<>();
    private WebappClassLoader  webappClassLoader;

    public ApplicationContext(String docBase){
        ClassLoader commonClassLoader = Thread.currentThread().getContextClassLoader();
        this.webappClassLoader = new WebappClassLoader(docBase, commonClassLoader);
    }

    public WebappClassLoader getWebappClassLoader() {
        return webappClassLoader;
    }

    @Override
    public String getContextPath() {
        return getPath();
    }

    @Override
    public void init() throws LifecycleException {
        try {
            parseServlets();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            stop();
        }
        super.init();
    }

    private void parseServlets() throws FileNotFoundException {
        File webInf = new File(getRealPath(), "WEB-INF");
        if(!webInf.exists()) throw new FileNotFoundException("cannot find WEB-INF dir in current application directory");
        File webXML = new File(webInf,  "web.xml");
        String xml = FileUtil.readUtf8String(webXML);
        Document document = Jsoup.parse(xml);
        Elements servlets = document.select("servlet");
        for(Element servlet : servlets){
            String name = servlet.select("servlet-name").text();
            String clazz = servlet.select("servlet-class").text();
            servletNameToClass.put(name, clazz);
            servletClassToName.put(clazz, name);
        }

        Elements servletMappings = document.select("servlet-mapping");
        for(Element servlet : servletMappings){
            String name = servlet.select("servlet-name").text();
            String url = servlet.select("url-pattern").text();
            servletNameToURL.put(name, url);
            servletURLToName.put(url, name);
        }
    }
}
