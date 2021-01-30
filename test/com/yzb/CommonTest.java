package com.yzb;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.yzb.common.ServerContext;
import com.yzb.exception.ParseHttpRequestException;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: use to some common test
 * @author: BeckoninGshy
 * @create: 2021/1/24 20:41
 */
public class CommonTest {
    @Test
    public void test1(){
        String outlineMessage1 = "GET /index.html HTTP/1.1";
        String outlineMessage2 = "POST / HTTP/1.1";
        String method1 = StrUtil.subBefore(outlineMessage1, " ", false);
        String method2 = StrUtil.subBefore(outlineMessage2, " ", false);
        Console.log(method1);
        Console.log(method2);
    }
    @Test
    public void test2(){
        String outlineMessage1 = "GET /index.html HTTP/1.1";
        String outlineMessage2 = "POST / HTTP/1.1";
        String queryString1 = StrUtil.subBetween(outlineMessage1, " ", " ");
        String queryString2 = StrUtil.subBetween(outlineMessage2, " ", " ");
        Console.log(queryString1);
        Console.log(queryString2);
    }

    @Test
    public void test3(){
        String outlineMessage1 = "GET /index.html HTTP/1.1";
        String outlineMessage2 = "POST / HTTP/1.1";
        String schema1 = outlineMessage1.substring(outlineMessage1.lastIndexOf(' ')+1);
        String schema2 = outlineMessage2.substring(outlineMessage2.lastIndexOf(' ')+1);
        Console.log(schema1);
        Console.log(schema2);
    }

    @Test
    public void test4(){
        String outlineMessage = "HTTP/1.1";
        String schema1 = StrUtil.subBefore(outlineMessage, "/", false);

        Console.log(schema1.toLowerCase(Locale.ROOT));
    }

    @Test
    public void test5(){
        String outlineMessage1 = "/index.html";
        String outlineMessage2 = "/index.html?username=lisi&password=123";
        String outlineMessage3 = "/";
        String schema1 = StrUtil.subAfter(outlineMessage1, "?", false);
        String schema2 = StrUtil.subAfter(outlineMessage2, "?", false);
        String schema3 = StrUtil.subAfter(outlineMessage3, "?", false);
        Console.log(schema1);
        Console.log(schema2);
        Console.log(schema3);
    }

    @Test
    public void test6(){
        String outlineMessage1 = "/index.html";
        String outlineMessage2 = "/index.html?username=lisi&password=123";
        String outlineMessage3 = "/";
        String schema1 = StrUtil.subBefore(outlineMessage1, "?", false);
        String schema2 = StrUtil.subBefore(outlineMessage2, "?", false);
        String schema3 = StrUtil.subBefore(outlineMessage3, "?", false);
        Console.log(schema1);
        Console.log(schema2);
        Console.log(schema3);
    }
    @Test
    public void test7() throws ParseHttpRequestException {
        String parameterString = "username=zhangsan&password=123&sex=male&vehicle=Bike&vehicle=Car&city=v_shanghai&message=++++++++++++The+cat+was+playing+in+the+garden.%0D%0A++++++++++++";
        String[] parameters = parameterString.split("&");
        Map<String, List<String>> parameterKVs = new HashMap<>();
        for(int i = 0, n = parameters.length; i < n; i++){
            String[] entries = parameters[i].split("=");
            if(entries.length != 2) {
                throw new ParseHttpRequestException("解析请求参数错误！");
            }
            List<String> values = parameterKVs.getOrDefault(entries[0], new ArrayList<>());
            values.add(entries[1]);
            parameterKVs.put(entries[0], values);
        }

        Map<String, String[]> ans = new HashMap<>();
        ans.put("vehicle",new String[]{"BMW"});
        for(Map.Entry<String,List<String>> entry : parameterKVs.entrySet()){
            String[] origin = ans.getOrDefault(entry.getKey(), new String[0]);
            String[] add = entry.getValue().toArray(new String[0]);
            int len = origin.length + add.length;
            String[] result = Arrays.copyOf(origin, len);
            System.arraycopy(add,0,result,origin.length,add.length);
            ans.put(entry.getKey(),result);
        }
        for(Map.Entry<String, String[]> entry : ans.entrySet()){
            System.out.println(entry.getKey() + "===" + Arrays.toString(entry.getValue()));
        }
        System.out.println(ans.getOrDefault("123",new String[1])[0]);
    }

    @Test
    public void test8(){
        String sendMessage1 = "POST / HTTP/1.1\r\n"
                + "User-Agent: yzb's client\r\n"
                + "Content-Length: 26\r\n"
                + "\r\n"
                + "username=lisi&password=123\r\n";
        String sendMessage2 = "POST / HTTP/1.1\r\n"
                + "User-Agent: yzb's client\r\n"
                + "Content-Length: 26\r\n"
                + "\r\n";
//        System.out.println(StrUtil.subBefore(sendMessage1, "\r\n\r\n", false));
//        System.out.println(StrUtil.subBefore(sendMessage2, "\r\n\r\n", false));
//        System.out.println(StrUtil.subAfter(sendMessage1, "\r\n\r\n", false));
        System.out.println(StrUtil.subAfter(sendMessage2, "\r\n\r\n", false));
    }

    @Test
    public void test9(){
        String language = "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2";
        System.out.println(StrUtil.splitTrim(language, ","));
    }

    @Test
    public void test10(){
        String ifum = "Wd, 21 Oct 2015 07:28:00 GMT";
        Date date = null;
        try{
             date = new Date("-1");
        } catch (IllegalArgumentException e){
            System.out.println(-1);
        }

        System.out.println(date.getTime());
    }

    @Test
    public void test11(){
//        System.out.println(new Date(0).toString());
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(0);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
    }

    @Test
    public void test12(){
        String characterEncoding = "utf-8";
        String ContentType = "multipart/form-data; boundary=something";
        Map<String,String> header = new HashMap<>();
        header.put("ContentType", ContentType);
        String contentType = header.getOrDefault("ContentType", "");
        if(StrUtil.indexOfIgnoreCase(contentType,"charset=") == -1) {
            System.out.println(-1);
            return;
        }
        String ans =  StrUtil.subBefore(contentType,"charset=", false) + "charset=" + characterEncoding;
        System.out.println(ans);
    }

    @Test
    public void test13(){

        String contentType = "multipart/form-data; boundary=something";
        String ans = StrUtil.subAfter(contentType,"charset=", false);
        System.out.println(ans);
    }

    @Test
    public void test14() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("webapps/form.html")));
        System.out.println(bufferedReader.readLine());

    }

    @Test
    public void test15(){
        System.out.println(ServerContext.serverBasePath);
        System.out.println(ServerContext.serverXMLPath);
    }

}
