package com.codingdm.mriya.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 5:12 PM
 **/
@Slf4j
@Data
public class NacosConfig {

    private String content;

    private static class LazyHolder {
        private static final NacosConfig INSTANCE = new NacosConfig();
    }


    public static final NacosConfig getInstance() {
        return LazyHolder.INSTANCE;
    }

    private NacosConfig(){
        String serverAddr = "127.0.0.1:8848";
        String dataId = "MRIYA";
        String group = "MRIYA-GROUP";

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
//        properties.put(PropertyKeyConst.NAMESPACE, "dabf5d10-b374-4d7d-b1d2-a1fad9c70b41");
        properties.put(PropertyKeyConst.NAMESPACE, "public");
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            content = configService.getConfig(dataId, group, 5000);
            configService.addListener(dataId, group, new ConfingListener());
        }catch (NacosException exception){
            log.error("nacos config exception" + exception.getErrMsg());
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        NacosConfig nacosConfig = NacosConfig.getInstance();
        while (true){
            System.out.println(nacosConfig.getContent());
            Thread.sleep(1000);
        }
    }
}
