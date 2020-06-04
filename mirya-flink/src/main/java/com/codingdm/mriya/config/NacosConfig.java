package com.codingdm.mriya.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 5:12 PM
 **/
@Slf4j
public class NacosConfig {
    public String content;
    public NacosConfig(){
        try {
            getConfigs();
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public void getConfigs() throws NacosException {
        System.out.println("get config");
        String serverAddr = "10.168.2.224:8848";
        String dataId = "MRIYA-FLINK";
        String group = "MRIYA";

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, "c2ae0ec9-50cc-4e85-88d4-06fa0f4496d6");

        ConfigService configService = NacosFactory.createConfigService(properties);
        this.content = configService.getConfig(dataId, group, 5000);
        System.out.println(configService.getConfig(dataId, group, 5000));

        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                content = configInfo;
            }
            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

    public static void main(String[] args) throws NacosException, InterruptedException {
        NacosConfig nacosConfig = new NacosConfig();
        while (true){
            System.out.println(nacosConfig.content);
            Thread.sleep(1000);
        }


    }
}
