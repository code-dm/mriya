package com.codingdm.mriya.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.codingdm.mriya.constant.PropertiesConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 5:12 PM
 **/
@Slf4j
@Data
public class NacosConfig {

    private Properties properties;

    private static class LazyHolder {
        private static final NacosConfig INSTANCE = new NacosConfig();
    }

    public static final NacosConfig getInstance() {
        return LazyHolder.INSTANCE;
    }

    private NacosConfig(){
        ParameterTool resourcesConfig = ResourcesConfig.CONFIG;
        if(resourcesConfig == null){
            log.error("nacos resourcesConfig is null, please check resources/application.properties");
            throw new NullPointerException();
        }
        Properties pro = new Properties();

        pro.put(PropertyKeyConst.SERVER_ADDR, resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_SERVERADDR));
        pro.put(PropertyKeyConst.NAMESPACE, resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_NAMESPACE));

        try {
            ConfigService configService = NacosFactory.createConfigService(pro);
            String config = configService.getConfig(
                    resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_DATAID),
                    resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_GROUP),
                    resourcesConfig.getInt(PropertiesConstants.MRIYA_NACOS_CONFIG_TIMEOUT)
            );
            System.out.println(config);
            properties = new Properties();
            properties.load(new StringReader(config));
            configService.addListener(
                    resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_DATAID),
                    resourcesConfig.get(PropertiesConstants.MRIYA_NACOS_CONFIG_GROUP),
                    new ConfingListener());
        }catch (NacosException e){
            log.error("nacos config NacosException --> \n" + e.getErrMsg());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("nacos config IOException --> \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return getInstance().getProperties().getProperty(key);
    }

}
