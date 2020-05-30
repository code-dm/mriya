package com.codingdm.mriya.mysql.parser.druid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.codingdm.mriya.mysql.parser.MysqlParser;
import com.codingdm.mriya.mysql.visitor.MysqlAlterTableVisitor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/29/2020 9:15 AM
 **/
@Slf4j
public class TestDruid {

    public static void main(String[] args) {



        String dbType = JdbcConstants.MYSQL; // JdbcConstants.MYSQL或者JdbcConstants.POSTGRESQL
        String sql = "create table data_center.demo\n" +
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
}
