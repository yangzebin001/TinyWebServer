package com.yzb;

import com.yzb.Exception.ParseHttpRequestException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 处理HTTP请求的实现类
 * @author: BeckoninGshy
 * @create: 2021/1/24 16:17
 */
public class HttpRequest extends Request {

    private static final String SEPARATOR_LINE = "\r\n\r\n";
    private static final String LINE_TERMINATOR = "\r\n";
    private static final String LINE_SEPARATOR = ": ";

    private String requestContent;
    private String outlineMessage;

    private Map<String, String> Headers;

    private final Socket socket;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        parseHttpRequestContent();
        parseHttpRequestHeader();
    }

    public Map<String,String> getHeaderMap(){
        return Headers;
    }

    public String getRequestContent(){
        return requestContent;
    }

    public String getOutlineMessage(){
        return outlineMessage;
    }


    private void parseHttpRequestContent() {

        Headers = new HashMap<String,String>();

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
            new ParseHttpRequestException("解析Http请求错误：读取socket输入流错误").printStackTrace();
        } finally {
            try {
                baso.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseHttpRequestHeader(){
        String[] headerStrings = getHttpRequestHeaderString().split(LINE_TERMINATOR);
        outlineMessage = headerStrings[0];
        for(int i = 1, n = headerStrings.length; i < n; i++){
            String[] line = headerStrings[i].split(LINE_SEPARATOR);
            Headers.put(line[0].trim(), line[1].trim());
        }
    }

    private String getHttpRequestHeaderString(){
        return requestContent.split(SEPARATOR_LINE)[0];
    }

    private String getHttpRequestBodyString(){
        String[] requestStrings = requestContent.split(SEPARATOR_LINE);
        if(requestStrings.length < 2) return "";
        return requestStrings[1];
    }
}
