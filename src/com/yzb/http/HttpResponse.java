package com.yzb.http;

import cn.hutool.core.util.StrUtil;
import com.yzb.common.Response;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description Handle HTTP response
 * @Date 2021/1/26 上午11:15
 * @Creater BeckoninGsy
 */
public class HttpResponse extends Response {

    private Socket socket;

    private Map<String, String> headers;
    private int statusCode;
    private boolean commited;
    private boolean callGetOutputStream = false;
    private boolean callGetWriter = false;
    private ByteArrayOutputStream baos;

    public HttpResponse(Socket socket){
        headers = new HashMap<>();
        statusCode = HttpContant.RESPONSE_SC_OK;
        commited = false;
        this.socket = socket;
        baos = new ByteArrayOutputStream(){
            @Override
            public void flush() throws IOException {
                super.flush();
                HttpResponse.this.commitResponse();
            }

            @Override
            public void close() throws IOException {
                if(!isCommitted()) flush();
                super.close();

            }
        };
    }

    @Override
    public void setHeader(String s, String s1) {
        headers.put(s,s1);
    }

    @Override
    public void addHeader(String s, String s1) {
        setHeader(s,s1);
    }

    @Override
    public void setIntHeader(String s, int i) {
        setHeader(s,Integer.toString(i));
    }

    @Override
    public void addIntHeader(String s, int i) {
        setHeader(s,Integer.toString(i));
    }

    @Override
    public void setDateHeader(String s, long l) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(l);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        setHeader(s,simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void addDateHeader(String s, long l) {
        setDateHeader(s, l);
    }

    @Override
    public boolean containsHeader(String s) {
        return headers.containsKey(s);
    }

    @Override
    public void setStatus(int i) {
        statusCode = i;
    }

    @Override
    public int getStatus() {
        return statusCode;
    }

    @Override
    public String getHeader(String s) {
        return headers.get(s);
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return StrUtil.splitTrim(getHeader(s), HttpContant.HEADER_SEPARATOR);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }


    @Override
    public boolean isCommitted() {
        return commited;
    }


    @Override
    public void setCharacterEncoding(String s) {
        String contentType = headers.getOrDefault(HttpContant.HEADER_CONTENT_TYPE, HttpContant.DEFAULT_CONTENT_TYPE);
        if(StrUtil.indexOfIgnoreCase(contentType,HttpContant.CONTENT_TYPE_SEPARATOR) == -1) {
            setContentType(contentType +"; " + HttpContant.CONTENT_TYPE_SEPARATOR + s);
        } else {
            setContentType(StrUtil.subBefore(contentType,HttpContant.CONTENT_TYPE_SEPARATOR, false) + HttpContant.CONTENT_TYPE_SEPARATOR + s);
        }
    }

    @Override
    public void setContentLength(int i) {
        setHeader(HttpContant.HEADER_CONTENT_LENGTH, Integer.toString(i));
    }

    @Override
    public void setContentType(String s) {
        setHeader(HttpContant.HEADER_CONTENT_TYPE, s);
    }

    @Override
    public String getCharacterEncoding() {
        String contentType = getContentType();
        if(contentType == null) return HttpContant.DEFAULT_CHARACTERENCODING;
        return StrUtil.subAfter(contentType, HttpContant.CONTENT_TYPE_SEPARATOR, false);
    }

    @Override
    public String getContentType() {
        return getHeader(HttpContant.HEADER_CONTENT_TYPE);
    }


    @Override
    public void setBufferSize(int i) {
        if(isCommitted()) throw new IllegalStateException("can not set buffer size, because it has been commited!");
        baos = new ByteArrayOutputStream(i){
            @Override
            public void flush() throws IOException {
                super.flush();
                HttpResponse.this.commitResponse();
            }

            @Override
            public void close() throws IOException {
                if(!isCommitted()) flush();
                super.close();

            }
        };
    }

    @Override
    public int getBufferSize() {
        return baos.size();
    }

    @Override
    public void flushBuffer() throws IOException {
        baos.flush();
    }

    @Override
    public void resetBuffer() {
        if(isCommitted()) throw new IllegalStateException("can not reset buffer, because it has been commited!");
        baos.reset();
    }

    @Override
    public void reset() {
        if(isCommitted()) throw new IllegalStateException("can not reset response, because it has been commited!");
        baos.reset();
        headers.clear();
        statusCode = HttpContant.RESPONSE_SC_OK;
        callGetOutputStream = false;
        callGetWriter = false;
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(callGetWriter) {
            throw new IllegalStateException("already call getOutputStream!");
        }
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }

            @Override
            public void flush() throws IOException {
                baos.flush();
            }

            @Override
            public void close() throws IOException {
                baos.close();
            }
        } ;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(callGetOutputStream) {
            throw new IllegalStateException("already call getOutputStream!");
        }
        callGetWriter = true;
        return new PrintWriter(baos);
    }

    @Override
    public void sendRedirect(String s) throws IOException {
        if (callGetWriter || callGetOutputStream) {
            throw new IllegalStateException("already call getWriter or getOutputStream!");
        }
        setStatus(HttpContant.RESPONSE_SC_MOVED_TEMPORARILY);
        setHeader("Location", s);
        commitRespnseOnlyWithHeaders();
    }


    @Override
    public void sendError(int code, String s) throws IOException {
        if(isCommitted()) throw new IllegalStateException("can not send error, because it has been commited!");
        setStatus(code);
        setContentType(HttpContant.DEFAULT_CONTENT_TYPE);
        PrintWriter writer = getWriter();
        writer.println(s);
        writer.close();
    }

    @Override
    public void sendError(int code) throws IOException {
        sendError(code, StrUtil.format(HttpContant.textFormat_normal, code));
    }


    private String generateResponseHeader(){
        StringBuilder sb = new StringBuilder(64);
        sb.append(HttpContant.DEFAULT_PROTOCOL);
        sb.append(" ");
        sb.append(getStatus());
        sb.append(HttpContant.getResponseHead(getStatus()));
        sb.append(HttpContant.LINE_TERMINATOR);
        for (Map.Entry<String,String> entry : headers.entrySet()){
            sb.append(entry.getKey());
            sb.append(HttpContant.LINE_SEPARATOR);
            sb.append(entry.getValue());
            sb.append(HttpContant.LINE_TERMINATOR);
        }
        sb.append(HttpContant.LINE_TERMINATOR);
        return sb.toString();
    }

    private void commitRespnseOnlyWithHeaders() throws IOException {
        String header = generateResponseHeader();
        socket.getOutputStream().write(header.getBytes(StandardCharsets.UTF_8));
        socket.getOutputStream().flush();
        commited = true;
    }

    private void commitResponse() throws IOException {
        setContentLength(baos.size());
        String header = generateResponseHeader();
        socket.getOutputStream().write(header.getBytes(StandardCharsets.UTF_8));
        socket.getOutputStream().write(baos.toByteArray());
        socket.getOutputStream().flush();
        commited = true;
    }


}
