package com.codingdm.mriya.mysql.parser.druid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.codingdm.mriya.mysql.parser.MysqlParser;
import com.codingdm.mriya.mysql.visitor.MysqlAlterTableVisitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


import java.util.List;

/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 5/29/2020 9:15 AM
 **/
@Slf4j
public class TestDruid {

    @Test
    public void createTable() {
        String dbType = JdbcConstants.MYSQL;
        String sql = "create table data_center.users\n" +
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
                "    update_by    varchar(32)    null comment '修改人',\n" +
                "    update_time  datetime       null comment '修改时间',\n" +
                "    sys_org_code varchar(64)    null comment '所属部门编码'\n" +
                ");";
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        MysqlParser parser = new MysqlParser();
        MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }
        parser.getColumns().forEach(System.out::println);
    }

    @Test
    public void createTable2() {
        String dbType = JdbcConstants.MYSQL;
        String sql = "/* ApplicationName=DataGrip 2020.1.4 */ CREATE TABLE `mriya`.`table_t1`  (     `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 't1',     `t1` varchar(100) NULL COMMENT 't1',     `t2` varchar(200) NULL COMMENT 't2',     `t3` float NULL COMMENT 't3 float',     `t4` date NULL COMMENT 't4 date',     `t5` time NULL COMMENT 't5 time',     `t6` datetime NULL COMMENT 't6 datetime',     `t7` timestamp NULL COMMENT 't7 timestamp',     `t8` double NULL COMMENT 't8 double',     `t9` text NULL COMMENT 't8 text',     `t10` decimal(10, 3) NULL COMMENT 't10 decimal(10, 3)',     PRIMARY KEY (`id`, `t1`))";

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        MysqlParser parser = new MysqlParser();
        MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }
        parser.getColumns().forEach(System.out::println);
    }

    @Test
    public void createTablePrivateKey() {
        String dbType = JdbcConstants.MYSQL;
        String sql = "CREATE TABLE `mriya`.`table_private_key`  (\n" +
                "   PRIMARY KEY (t2, t1),\n" +
                "  `t1` int(10) NOT NULL,\n" +
                "  `t2` int(10) NOT NULL\n" +
                ");";

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        MysqlParser parser = new MysqlParser();
        MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }
        parser.getColumns().forEach(System.out::println);
    }


    @Test
    public void alterTable(){
        String sql = "ALTER TABLE `jeecg-boot`.`demo` \n" +
                "CHANGE COLUMN `sys_org_code` `sys_org_code2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "CHANGE COLUMN `sys_org_code_temp` `sys_org_code_4` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门编码' AFTER `update_time`,\n" +
                "MODIFY COLUMN `update_time` varchar(10) NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_by`,\n" +
                "MODIFY COLUMN `update_time_2` varchar(10) NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "ADD COLUMN `t1` varchar(255) NULL COMMENT 't1-comment' AFTER `sys_org_code2`,\n" +
                "DROP COLUMN col_name ,\n" +
                "DROP COLUMN col_name2 ,\n" +
                "ADD COLUMN `t2` varchar(100) NULL COMMENT 't2-comment' AFTER `t1`, ADD COLUMN(t5 varchar(255), t6 varchar(255), t7 varchar(255));";

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        MysqlParser parser = new MysqlParser();
        MysqlAlterTableVisitor visitor = new MysqlAlterTableVisitor(parser);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }
        parser.getColumns().forEach(System.out::println);
    }



}
