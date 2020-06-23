package com.codingdm.mriya.init;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/23/2020 4:57 PM
 **/
public interface Init {
    /**
     * 初始化表结构
     * @return bool
     */
    boolean initTableSchema();

    /**
     * 初始化数据
     * @return bool
     */
    boolean initTableData();
}
