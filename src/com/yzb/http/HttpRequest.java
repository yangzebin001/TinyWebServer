package com.yzb.http;

import cn.hutool.core.util.StrUtil;
import com.yzb.exception.ParseHttpRequestException;
import com.yzb.common.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

/**
 * @description: 处理HTTP请求的实现类
 * @author: BeckoninGshy
 * @create: 2021/1/24 16:17
 */
public class HttpRequest extends Request {

    private static final String SEPARATOR_LINES = "\r\n\r\n";
    private static final String LINE_TERMINATOR = "\r\n";
    private static final String LINE_SEPARATOR = ": ";
    private static final String HEADER_SEPARATOR = ",";
    private static final String PARAMATERS_SEPARATOR = "&";
    private static final String PARAMATERSKV_SEPARATOR = "=";

    private String requestContent;
    private String outlineMessage;

    private Map<String, String> headers;

    private Map<String, String[]> parameterMap;

    private final Socket socket;

    public HttpRequest(Socket socket) throws ParseHttpRequestException {
        this.socket = socket;
        headers = new HashMap<>();
        parameterMap = new HashMap<>();
        parseHttpRequestContent();
        parseHttpRequestHeader();
        parseHttpRequestParameter();
    }

    public Map<String,String> getHeaderMap(){
        return headers;
    }

    @Override
    public long getDateHeader(String s) {
        if(headers.containsKey(s)) return -1;
        Date date = null;
        try{
            date = new Date(headers.get(s).trim());
        } catch (IllegalArgumentException e){
            return -1;
        }
        return date.getTime();
    }

    @Override
    public String getHeader(String s) {
        return headers.get(s);
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return (Enumeration<String>) StrUtil.splitTrim(headers.get(s), HEADER_SEPARATOR);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return (Enumeration<String>) headers.keySet();
    }

    @Override
    public int getIntHeader(String s) {
        return Integer.parseInt(headers.get(s));
    }

    public String getRequestContent(){
        return requestContent;
    }

    public String getOutlineMessage(){
        return outlineMessage;
    }

    @Override
    public String getMethod(){
        return StrUtil.subBefore(outlineMessage, " ", false);
    }

    @Override
    public String getProtocol() {
        return outlineMessage.substring(outlineMessage.lastIndexOf(' ')+1);
    }

    @Override
    public String getScheme() {
        return StrUtil.subBefore(getProtocol(), "/", false).toLowerCase();
    }


    private String getWholeRequestURL(){
        return StrUtil.subBetween(outlineMessage, " ", " ");
    }

    @Override
    public String getQueryString() {
        return StrUtil.subAfter(getWholeRequestURL(), "?", false);
    }

    @Override
    public String getRequestURI() {
        return  StrUtil.subBefore(getWholeRequestURL(), "?", false);
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return parameterMap.getOrDefault(s,new String[1])[0];
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return (Enumeration<String>) parameterMap.keySet();
    }

    @Override
    public String[] getParameterValues(String s) {
        return parameterMap.getOrDefault(s,new String[0]);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }



    private void parseHttpRequestContent() throws ParseHttpRequestException {

        ByteArrayOutputStream baso = null;
        try {
            InputStream inputStream = socket.getInputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[1024];
            baso = new ByteArrayOutputStream();
            while (true) {
                int len = inputStream.read(buffer);
                if (len == -1) break;
                baso.write(buffer, 0, len);
                if (len != bufferSize) {
                    break;
                }
            }
            requestContent = baso.toString();
        } catch (IOException e) {
            throw new ParseHttpRequestException("解析Http请求错误：读取socket输入流错误");
        } finally {
            try {
                baso.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseHttpRequestHeader() throws ParseHttpRequestException {
        String[] headerStrings = getHttpRequestHeaderString().split(LINE_TERMINATOR);
        outlineMessage = headerStrings[0];
        for(int i = 1, n = headerStrings.length; i < n; i++){
            String[] line = headerStrings[i].split(LINE_SEPARATOR);
            if(line.length != 2)
                throw new ParseHttpRequestException("解析请求头部错误！");
            headers.put(line[0].trim(), line[1].trim());
        }
    }

    private void parseHttpRequestParameter() throws ParseHttpRequestException {
        String parameterString = "";
        if (HttpContant.REQUEST_METHOD_GET.equals(getMethod())) {
            parameterString = getQueryString();
        }else if(HttpContant.REQUEST_METHOD_POST.equals(getMethod())) { //仅支持单次请求。例如表单提交
            parameterString = getHttpRequestBodyString();
        }

        if(StrUtil.isEmpty(parameterString)) return;

        String[] parameters = parameterString.split(PARAMATERS_SEPARATOR);

        Map<String, List<String>> parameterKVs = new HashMap<>();
        for(int i = 0, n = parameters.length; i < n; i++){
            String[] entries = parameters[i].split(PARAMATERSKV_SEPARATOR);
            if(entries.length != 2)
                throw new ParseHttpRequestException("解析请求参数错误！");
            List<String> values = parameterKVs.getOrDefault(entries[0], new ArrayList<>());
            values.add(entries[1]);
            parameterKVs.put(entries[0], values);
        }
        // add values to origin map
        for(Map.Entry<String,List<String>> entry : parameterKVs.entrySet()) {
            String[] origin = parameterMap.getOrDefault(entry.getKey(), new String[0]);
            String[] add = entry.getValue().toArray(new String[0]);
            int len = origin.length + add.length;
            String[] result = Arrays.copyOf(origin, len);
            System.arraycopy(add, 0, result, origin.length, add.length);
            parameterMap.put(entry.getKey(), result);
        }
    }

    private String getHttpRequestHeaderString(){
        return StrUtil.subBefore(requestContent, SEPARATOR_LINES,false);
    }

    private String getHttpRequestBodyString(){
        return StrUtil.subAfter(requestContent, SEPARATOR_LINES,false);
    }
}
