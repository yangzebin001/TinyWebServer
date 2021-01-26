package com.yzb.http;

/**
 * @description:
 * @author: BeckoninGshy
 * @create: 2021/1/25 12:03
 */
public class HttpContant {

    public static final String SEPARATOR_LINES = "\r\n\r\n";
    public static final String LINE_TERMINATOR = "\r\n";
    public static final String LINE_SEPARATOR = ": ";
    public static final String HEADER_SEPARATOR = ",";
    public static final String PARAMATERS_SEPARATOR = "&";
    public static final String PARAMATERSKV_SEPARATOR = "=";


    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_DELETE = "DELETE";
    public static final String REQUEST_METHOD_HEAD = "HEAD";
    public static final String REQUEST_METHOD_CONNECT = "CONNECT";
    public static final String REQUEST_METHOD_OPTIONS = "OPTIONS";
    public static final String REQUEST_METHOD_TRACE = "TRACE";
    public static final String REQUEST_METHOD_PATCH = "PATCH";

    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public static final String DEFAULT_CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String DEFAULT_CHARACTERENCODING = "UTF-8";
    public static final String CONTENT_TYPE_SEPARATOR = "charset=";

    public static final int RESPONSE_STATUS_CODE_OK = 200;

    public static final String DEFAULT_PROTOCOL = "HTTP/1.1";

}
