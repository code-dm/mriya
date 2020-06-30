package com.codingdm.mriya.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/5/2020 11:08 AM
 **/
@Slf4j
public class NacosConfigTest {

    @Test
    public void getConfig() throws InterruptedException {
        while (true){
            System.out.println(NacosConfig.getList("mriya.message.filer"));
            Thread.sleep(1000);
        }
    }

    @Test
    public void getConfigList(){
        List list = NacosConfig.getList("mriya.message.filer");
        System.out.println(list);
    }

}
