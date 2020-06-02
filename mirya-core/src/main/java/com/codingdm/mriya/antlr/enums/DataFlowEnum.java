package com.codingdm.mriya.antlr.enums;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @create 2019-08-09 13:23
 **/
public enum DataFlowEnum {
    OLTP_TO_ODS(0),
    ODS_TO_DWD(1);

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer type;

    DataFlowEnum(Integer type){
        this.type = type;
    }

}
