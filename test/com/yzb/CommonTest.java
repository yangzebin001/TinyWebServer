package com.yzb;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.Locale;

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
}
