package com.codingdm.mriya.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 模板渲染引擎
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/10/2020 3:28 PM
 **/
@Slf4j
public class TemplateUtil {

    private  static Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

    public static String rendering(String templateStr, Object model){

        try {
            Template strTemplate = new Template("strTemplate", templateStr, cfg);
            StringWriter result = new StringWriter();
            strTemplate.process(model, result);
            String s = result.toString();
            return s;
        } catch (IOException | TemplateException e) {
            log.error("TemplateUtil rendering Exception:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
