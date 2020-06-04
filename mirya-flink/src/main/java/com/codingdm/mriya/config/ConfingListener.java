package com.codingdm.mriya.config;

import com.alibaba.nacos.api.config.listener.Listener;

import java.util.concurrent.Executor;

/**
 * @program: mriya
 * @description: 配置监听器
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/04 21:07
 */
public class ConfingListener implements Listener {


    @Override
    public Executor getExecutor() {

        return null;
    }

    @Override
    public void receiveConfigInfo(String config) {
        NacosConfig.getInstance().setContent(config);
    }
}
