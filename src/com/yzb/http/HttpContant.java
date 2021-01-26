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


    public static final int	RESPONSE_SC_ACCEPTED = 202;
    public static final int	RESPONSE_SC_BAD_GATEWAY = 502;
    public static final int	RESPONSE_SC_BAD_REQUEST = 400;
    public static final int	RESPONSE_SC_CONFLICT = 409;
    public static final int	RESPONSE_SC_CONTINUE = 100;
    public static final int	RESPONSE_SC_CREATED = 201;
    public static final int	RESPONSE_SC_EXPECTATION_FAILED = 417;
    public static final int	RESPONSE_SC_FORBIDDEN = 403;
    public static final int	RESPONSE_SC_FOUND = 302;
    public static final int	RESPONSE_SC_GATEWAY_TIMEOUT = 504;
    public static final int	RESPONSE_SC_GONE = 410;
    public static final int	RESPONSE_SC_HTTP_VERSION_NOT_SUPPORTED = 505;
    public static final int	RESPONSE_SC_INTERNAL_SERVER_ERROR = 500;
    public static final int	RESPONSE_SC_LENGTH_REQUIRED = 411;
    public static final int	RESPONSE_SC_METHOD_NOT_ALLOWED = 405;
    public static final int	RESPONSE_SC_MOVED_PERMANENTLY = 301;
    public static final int	RESPONSE_SC_MOVED_TEMPORARILY = 302;
    public static final int	RESPONSE_SC_MULTIPLE_CHOICES = 300;
    public static final int	RESPONSE_SC_NO_CONTENT = 204;
    public static final int	RESPONSE_SC_NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int	RESPONSE_SC_NOT_ACCEPTABLE = 406;
    public static final int	RESPONSE_SC_NOT_FOUND = 404;
    public static final int	RESPONSE_SC_NOT_IMPLEMENTED = 501;
    public static final int	RESPONSE_SC_NOT_MODIFIED = 304;
    public static final int	RESPONSE_SC_OK = 200;
    public static final int	RESPONSE_SC_PARTIAL_CONTENT = 206;
    public static final int	RESPONSE_SC_PAYMENT_REQUIRED = 402;
    public static final int	RESPONSE_SC_PRECONDITION_FAILED = 412;
    public static final int	RESPONSE_SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int	RESPONSE_SC_REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int	RESPONSE_SC_REQUEST_TIMEOUT = 408;
    public static final int	RESPONSE_SC_REQUEST_URI_TOO_LONG = 414;
    public static final int	RESPONSE_SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int	RESPONSE_SC_RESET_CONTENT = 205;
    public static final int	RESPONSE_SC_SEE_OTHER = 303;
    public static final int	RESPONSE_SC_SERVICE_UNAVAILABLE = 503;
    public static final int	RESPONSE_SC_SWITCHING_PROTOCOLS = 101;
    public static final int	RESPONSE_SC_TEMPORARY_REDIRECT = 307;
    public static final int	RESPONSE_SC_UNAUTHORIZED = 401;
    public static final int	RESPONSE_SC_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int	RESPONSE_SC_USE_PROXY = 305;



    public static final String DEFAULT_PROTOCOL = "HTTP/1.1";

}
