package com.yzb;

import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Client {
    Client client;
    @Before
    public void before(){
        client = new Client();
    }

    @Test
    public void RequestByUtil(){
        String body = HttpRequest.get("127.0.0.1:9090")
                .header(Header.USER_AGENT, "yzb's client")
                .header(Header.REFERER, "http://www.baidu.com")
                .execute().body();
        Console.log(body);
    }

    @Test
    public void RequestPostByUtil(){
        Map<String,Object> formData = new HashMap<>();
        formData.put("username","zhangsan");
        formData.put("password","password");
        String body = HttpRequest.post("127.0.0.1:9090").header(Header.USER_AGENT, "yzb's client")
                .form(formData)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        Console.log(body);
    }

    @Test
    public void RequestBySocket() throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 9090);
        Socket socket = new Socket();
        socket.connect(serverAddress);
        OutputStream outputStream = socket.getOutputStream();
        String sendMessage = "hello Server!";
        outputStream.write(sendMessage.getBytes(StandardCharsets.UTF_8));

        InputStream inputStream = socket.getInputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        while(true){
            int len = inputStream.read(buffer);
            if(len == -1) break;
            baso.write(buffer, 0 , len);
            if(len != bufferSize){
                break;
            }
        }
        System.out.println(baso.toString());
        baso.close();
        socket.close();
    }


}
