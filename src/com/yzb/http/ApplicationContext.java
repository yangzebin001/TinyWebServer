package com.yzb.http;

import cn.hutool.core.io.FileUtil;
import com.yzb.classcloader.WebappClassLoader;
import com.yzb.common.ServerContext;
import com.yzb.common.StandardServletContext;
import com.yzb.exception.LifecycleException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private Map<String,String> mimeMapping        = new HashMap<>();
    private WebappClassLoader  webappClassLoader;
    private boolean isDefault = false;
    private ApplicationContext defaultContext = null;



    public ApplicationContext(String docBase, Boolean isDefault){
        ClassLoader commonClassLoader = Thread.currentThread().getContextClassLoader();
        this.webappClassLoader = new WebappClassLoader(docBase, commonClassLoader);
        this.isDefault = isDefault;
    }

    public WebappClassLoader getWebappClassLoader() {
        return webappClassLoader;
    }

    public String getServletURLToName(String url){
        return servletURLToName.get(url);
    }

    public String getServletNameToClass(String name){
        return servletNameToClass.get(name);
    }

    @Override
    public String getMimeType(String extension){
        return mimeMapping.get(extension);
    }

    public Set<String> getMimeTypesSet(){
        return mimeMapping.keySet();
    }

    @Override
    public String getContextPath() {
        return getPath();
    }

    @Override
    public void init() throws LifecycleException {
        try {
            parseServlets();
            parseMimeAndMapping();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            stop();
        }
        super.init();
    }

    private void parseServlets() throws FileNotFoundException {
        File webInf = null;
        if(isDefault){
            webInf = new File(ServerContext.serverConfigDir);
        }else{
            webInf = new File(getRealPath(), "WEB-INF");
            if(!webInf.exists()) throw new FileNotFoundException("cannot find WEB-INF dir in current application directory");
        }
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

    private void parseMimeAndMapping() throws FileNotFoundException {
        File webInf = null;
        if(isDefault){
            webInf = new File(ServerContext.serverConfigDir);
        }else{
            webInf = new File(getRealPath(), "WEB-INF");
            if(!webInf.exists()) throw new FileNotFoundException("cannot find WEB-INF dir in current application directory");
        }
        File webXML = new File(webInf,  "web.xml");
        String xml = FileUtil.readUtf8String(webXML);
        Document document = Jsoup.parse(xml);
        Elements mimeMappings = document.select("mime-mapping");
        for(Element mime : mimeMappings){
            String extension = mime.select("extension").text();
            String mimeType = mime.select("mime-type").text();
            mimeMapping.put(extension, mimeType);
        }

    }

    public String getServletURLToClass(String url) {
        return getServletNameToClass(getServletURLToName(url));
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return super.getServlet(s);
    }

    public Servlet getServlet(Class<?> clazz) throws ServletException, IllegalAccessException, InstantiationException {
        return (Servlet) clazz.newInstance();
    }

    public boolean isDefaultContext(){
        return isDefault;
    }

    public void setDefaultContext(ApplicationContext defaultContext){
        this.defaultContext = defaultContext;
    }

    public ApplicationContext getDefaultContext(){
        return defaultContext;
    }

}
