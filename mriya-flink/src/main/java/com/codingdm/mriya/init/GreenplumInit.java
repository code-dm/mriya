package com.codingdm.mriya.init;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/23/2020 4:59 PM
 **/
@Slf4j
public class GreenplumInit implements Init{

    @Override
    public boolean initTableSchema() {
        return false;
    }

    @Override
    public boolean initTableData() {
        return false;
    }

}
