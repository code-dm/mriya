package com.codingdm.mriya.template;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.ddl.impl.GreenplumTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
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
        String target_demo = template.alterSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
        System.out.println(target_demo);
    }

    @Test
    public void testAlter2(){
        String alterSql = "ALTER TABLE `mriya`.`alter_table` \n" +
                "MODIFY COLUMN `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键' FIRST,\n" +
                "MODIFY COLUMN `rename_column` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话1' AFTER `idcard_pic`,\n" +
                "MODIFY COLUMN `add_column` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试新增字段' AFTER `update_time`,\n" +
                "DROP PRIMARY KEY,\n" +
                "ADD PRIMARY KEY (`id`, `idcard`, `rename_column`, `update_column`) USING BTREE;";

        DDLTemplate template = new GreenplumTemplate();
        String target_demo = template.alterSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
        System.out.println(target_demo);
    }

    @Test
    public void testAlter3(){
        String alterSql = "ALTER TABLE `mriya`.`alter_table` DROP PRIMARY KEY ,ADD PRIMARY KEY ( `id`, idcard )";

        DDLTemplate template = new GreenplumTemplate();
        String target_demo = template.alterSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
        System.out.println(target_demo);
    }

    @Test
    public void testCreate(){
        String alterSql = "create table data_center.users\n" +
                "(\n" +
                "    id           varchar(50)    not null comment '主键ID'\n" +
                "        primary key,\n" +
                "    name         varchar(30)    null comment '姓名',\n" +
                "    key_word     varchar(255)   null comment '关键词',\n" +
                "    punch_time   datetime       null comment '打卡时间',\n" +
                "    salary_money decimal(10, 3) null comment '工资',\n" +
                "    bonus_money  double(10, 2)  null comment '奖金',\n" +
                "    sex          varchar(2)     null comment '性别 {男:1,女:2}',\n" +
                "    age          int            null comment '年龄',\n" +
                "    birthday     date           null comment '生日',\n" +
                "    email        varchar(50)    null comment '邮箱',\n" +
                "    content      varchar(1000)  null comment '个人简介',\n" +
                "    create_by    varchar(32)    null comment '创建人',\n" +
                "    create_time  datetime       null comment '创建时间',\n" +
                "    update_by    varchar(32)    null comment ' ',\n" +
                "    update_time  datetime       null comment '',\n" +
                "    sys_org_code varchar(64)    null\n" +
                ");";

        DDLTemplate template = new GreenplumTemplate();
        String target_demo = template.createSql(alterSql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
        System.out.println(target_demo);

    }

    @Test
    public void renameTable(){
        String sql = "RENAME TABLE `test_table`.`demo` TO `test_table`.`demo2`";
        DDLTemplate template = new GreenplumTemplate();
        String target_demo = template.renameTableSql(sql, "target_demo",
                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA),
                "topic_database_%s");
        System.out.println(target_demo);
    }

}
