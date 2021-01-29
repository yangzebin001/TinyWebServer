package com.yzb;

import cn.hutool.core.util.RandomUtil;
import com.yzb.Service;
import com.yzb.StandardServer;
import com.yzb.StandardService;
import org.junit.*;

/**
 * @Description
 * @Date 2021/1/29 下午1:37
 * @Creater BeckoninGshy
 */
public class ServerTest {
    public static StandardServer ss = null;
    public static Service[] services = new Service[0];

    @BeforeClass
    public static void beforeClass(){
        ss = StandardServer.getServerInstance();
        services = new Service[100];
    }



    @Test
    public void addServicesTest(){
        for(int i = 0; i < 100; i++){
            services[i] = new StandardService();
            ss.addService(services[i]);
        }
        Assert.assertEquals(100,ss.findServices().length);
    }


    @Test
    public void findServicesTest(){
        Assert.assertEquals(100,ss.findServices().length);
    }


    @Test
    public void removeServicesTest(){
        Assert.assertEquals(100,ss.findServices().length);
        for(int i = 0; i < 100; i++){
            ss.removeService(services[i]);
        }
        Assert.assertEquals(0,ss.findServices().length);
    }

    @Test
    public void removeServicesRandomTest(){
        for(int i = 0; i < 100; i++){
            services[i] = new StandardService();
            ss.addService(services[i]);
        }
        int[] cnt = new int[100];
        for(int i = 0; i < 100; i++){
            int t = RandomUtil.randomInt(0,100);
            while(cnt[t] != 0) t = RandomUtil.randomInt(0,100);
            cnt[t] = 1;
            ss.removeService(services[t]);
        }
        Assert.assertEquals(0,ss.findServices().length);
    }


}
