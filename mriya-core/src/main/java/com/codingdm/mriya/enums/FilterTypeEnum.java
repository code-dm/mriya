package com.codingdm.mriya.enums;

/**
 * 过滤器类型 BLACKLIST 黑名单 WHITELIST白名单
 * @author wudongming
 */

public enum FilterTypeEnum {
    // 黑名单
    BLACKLIST,

    //白名单
    WHITELIST,

    // 拼接json事件
    BUILD_JSON_EVENT,

    // 公共维度数据
    PUBLIC_JSON_EVENT,

    // order维度
    ORDER_EVENT
}
