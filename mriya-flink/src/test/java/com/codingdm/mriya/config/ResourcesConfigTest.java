package com.codingdm.mriya.config;

import com.codingdm.mriya.constant.PropertiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;
import org.junit.Test;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/5/2020 9:54 AM
 **/
@Slf4j
public class ResourcesConfigTest {

    @Test
    public void getConfig(){
        ParameterTool parameterTool = ResourcesConfig.CONFIG;
        System.out.println(parameterTool.get(PropertiesConstants.MRIYA_NACOS_CONFIG_DATAID));
    }
}
