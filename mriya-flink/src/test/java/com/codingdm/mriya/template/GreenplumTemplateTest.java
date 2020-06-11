package com.codingdm.mriya.template;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.ddl.impl.GreenplumTemplate;
import com.codingdm.mriya.model.GPColumn;
import com.codingdm.mriya.utils.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/10/2020 9:47 AM
 **/
@Slf4j
public class GreenplumTemplateTest {

    @Test
    public void testAlter(){
        String alterSql = "ALTER TABLE `jeecg-boot`.`demo` \n" +
                "CHANGE COLUMN `sys_org_code` `sys_org_code2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "CHANGE COLUMN `sys_org_code_temp` `sys_org_code_4` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "MODIFY COLUMN `update_time` varchar(10) NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_by`,\n" +
                "MODIFY COLUMN `update_time_2` varchar(10) NULL DEFAULT NULL COMMENT '修改''时间',\n" +
                "ADD COLUMN `t1` varchar(255) NULL COMMENT 't1-comment' AFTER `sys_org_code2`,\n" +
                "DROP COLUMN col_name ,\n" +
                "DROP COLUMN col_name2 ,\n" +
                "ADD COLUMN `t2` varchar(100) NULL COMMENT 't2-comment' AFTER `t1`, ADD COLUMN(t5 varchar(255), t6 varchar(255), t7 varchar(255));";

        DDLTemplate template = new GreenplumTemplate();
        template.alterSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));

    }

    @Test
    public void testCreate(){
        String alterSql = "ALTER TABLE `jeecg-boot`.`demo` \n" +
                "CHANGE COLUMN `sys_org_code` `sys_org_code2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "CHANGE COLUMN `sys_org_code_temp` `sys_org_code_4` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "MODIFY COLUMN `update_time` varchar(10) NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_by`,\n" +
                "MODIFY COLUMN `update_time_2` varchar(10) NULL DEFAULT NULL COMMENT '修改''时间',\n" +
                "ADD COLUMN `t1` varchar(255) NULL COMMENT 't1-comment' AFTER `sys_org_code2`,\n" +
                "DROP COLUMN col_name ,\n" +
                "DROP COLUMN col_name2 ,\n" +
                "ADD COLUMN `t2` varchar(100) NULL COMMENT 't2-comment' AFTER `t1`, ADD COLUMN(t5 varchar(255), t6 varchar(255), t7 varchar(255));";

        DDLTemplate template = new GreenplumTemplate();
        template.alterSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));

    }

}
