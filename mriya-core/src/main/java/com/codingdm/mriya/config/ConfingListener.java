package com.codingdm.mriya.config;

import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.Executor;

/**
 * @program: mriya
 * @description: 配置监听器
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/04 21:07
 */
@Log4j
public class ConfingListener implements Listener {
    @Override
    public Executor getExecutor() {
        return null;
    }
    @Override
    public void receiveConfigInfo(String config) {
        try {
            NacosConfig.getInstance().getProperties().load(new StringReader(config));
        } catch (IOException e) {
            log.error("nacos config IOException in receiveConfigInfo --> \n" + e.getMessage());
        }
    }
}
