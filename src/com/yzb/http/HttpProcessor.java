package com.yzb.http;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Description processor one http request
 * @Date 2021/1/26 下午10:00
 * @Creater BeckoninGshy
 */
public class HttpProcessor {

    public void execute(Socket socket, HttpRequest httpRequest, HttpResponse httpResponse){
        try{

            PrintWriter outputStream = httpResponse.getWriter();
            httpResponse.setHeader(HttpContant.HEADER_CONTENT_TYPE, HttpContant.DEFAULT_CONTENT_TYPE);
            httpResponse.setHeader(HttpContant.HEADER_SERVER,  httpRequest.getServerName());
            outputStream.print("<html><body>hello client");
            outputStream.print("<br>this page handled by ");
            outputStream.print(Thread.currentThread());
            outputStream.print("</body></html>");
            outputStream.close();


        }  catch (IOException e) {
            //500
            e.printStackTrace();
        } finally {
            try {
                if(!socket.isClosed())
                    socket.close();
            } catch (IOException e) {
                //send 500
                e.printStackTrace();
            }
        }


    }

}
