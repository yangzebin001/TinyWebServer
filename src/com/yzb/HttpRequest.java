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
    private static Socket socket;
    private Map<String, String> Headers;
    public HttpRequest(Socket socket) {
        this.socket = socket;
        HttpRequestContentParser.parseHttpRequestContent();
        HttpRequestContentParser.parseHttpRequestHeader();
    }

    public Map<String,String> getHeaders(){
        return HttpRequestContentParser.Headers;
    }

    public String getRequestContent(){
        return HttpRequestContentParser.requestContent;
    }

    public String getOutlineMessage(){
        return HttpRequestContentParser.outlineMessage;
    }

    public String getHttpRequestBodyString(){
        return HttpRequestContentParser.getHttpRequestBodyString();
    }




    static class HttpRequestContentParser{
        static Map<String, String> Headers;
        static String requestContent;
        static String outlineMessage;
        private static final String SEPARATOR_LINE = "\r\n\r\n";
        private static final String LINE_TERMINATOR = "\r\n";
        private static final String LINE_SEPARATOR = ": ";

        static void parseHttpRequestContent() {

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

        static void parseHttpRequestHeader(){
            String[] headerStrings = getHttpRequestHeaderString().split(LINE_TERMINATOR);
            outlineMessage = headerStrings[0];
            for(int i = 1, n = headerStrings.length; i < n; i++){
                String[] line = headerStrings[i].split(LINE_SEPARATOR);
                Headers.put(line[0].trim(), line[1].trim());
            }
        }

        static String getHttpRequestHeaderString(){
            return requestContent.split(SEPARATOR_LINE)[0];
        }
        static String getHttpRequestBodyString(){
            String[] RequestStrings = requestContent.split(SEPARATOR_LINE);
            if(RequestStrings.length < 2) return "";
            return RequestStrings[1];
        }
    }

}
