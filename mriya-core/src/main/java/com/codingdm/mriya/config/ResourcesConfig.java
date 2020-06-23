package com.codingdm.mriya.config;

import com.codingdm.mriya.constant.PropertiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/5/2020 9:51 AM
 **/
@Slf4j
public class ResourcesConfig {

    public static final ParameterTool CONFIG = createParameterTool();

    private static ParameterTool createParameterTool() {
        try {
            return ParameterTool
                    .fromPropertiesFile(ResourcesConfig.class.getResourceAsStream(PropertiesConstants.PROPERTIES_FILE_NAME))
                    .mergeWith(ParameterTool.fromSystemProperties())
                    .mergeWith(ParameterTool.fromMap(getenv()));
        } catch (IOException e) {
            log.info("ResourcesConfig createParameterTool IOException");
            e.printStackTrace();
        }
        return null;
    }
    private static Map<String, String> getenv() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
