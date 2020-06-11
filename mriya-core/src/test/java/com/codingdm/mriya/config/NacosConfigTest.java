package com.codingdm.mriya.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Properties;

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
            System.out.println(NacosConfig.get("mriya.target.datasource.url"));
            Thread.sleep(1000);
        }
    }

}
