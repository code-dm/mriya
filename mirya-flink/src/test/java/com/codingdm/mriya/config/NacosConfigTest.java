package com.codingdm.mriya.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/5/2020 11:08 AM
 **/
@Slf4j
public class NacosConfigTest {

    @Test
    public void getConfig() throws InterruptedException {
        while (true){
            System.out.println(NacosConfig.get("testconfig3"));
            Thread.sleep(1000);
        }
    }
}
