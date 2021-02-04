package com.yzb.http;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.LogFactory;
import com.yzb.classcloader.WebappClassLoader;
import com.yzb.common.ServerContext;
import com.yzb.core.StandardServletConfig;
import com.yzb.core.StandardServletContext;
import com.yzb.exception.LifecycleException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    private Map<String,String> initParameters     = new HashMap<>();
    private Map<String,Map<String,String>> servletClassToInitParameters = new HashMap<>();
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
            assert name != null;
            assert clazz != null;
            servletNameToClass.put(name, clazz);
            servletClassToName.put(clazz, name);

            // process init-param
            Elements initParams = servlet.select("init-param");
            if(initParams == null || initParams.size() == 0) continue;
            Map<String,String> inits = new HashMap<>();
            for(Element initParam : initParams){
                String initParamValue = initParam.select("param-value").text();
                String initParamName = initParam.select("param-name").text();
                inits.put(initParamName,initParamValue);
            }
            servletClassToInitParameters.put(clazz, inits);
        }

        Elements servletMappings = document.select("servlet-mapping");
        for(Element servlet : servletMappings){
            String name = servlet.select("servlet-name").text();
            String url = servlet.select("url-pattern").text();
            assert name != null;
            assert url != null;
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

    public String getServletClassToName(Class<?> clazz){
        return servletClassToName.get(clazz.getName());
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        try {
            return getServlet(webappClassLoader.loadClass(getServletNameToClass(s)));
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            LogFactory.get().error("get servlet {} failed!", s);
        }
        return null;
    }

    public synchronized Servlet getServlet(Class<?> clazz) throws ServletException, IllegalAccessException, InstantiationException {
        Servlet s = (Servlet) clazz.newInstance();
        ServletConfig sc = new StandardServletConfig(this, getServletClassToName(clazz), servletClassToInitParameters.get(clazz.getName()));
        s.init(sc);
        return s;
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


    @Override
    public String getInitParameter(String s) {
        return initParameters.get(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    @Override
    public boolean setInitParameter(String key, String value) {
        initParameters.put(key, value);
        return true;
    }

}
