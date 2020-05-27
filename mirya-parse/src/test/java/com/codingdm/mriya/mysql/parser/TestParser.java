package com.codingdm.mriya.mysql.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLTransformVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:57 PM
 **/
@Slf4j
public class TestParser {

    public static void main(String[] args) {

        String sql = "ALTER TABLE `shipping`.`wo_reservaion` \n" +
                "CHANGE COLUMN `mto_flag` `mto_flag1` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SAP侧服务和产品定义' AFTER `invoice_entity`,\n" +
                "MODIFY COLUMN `invoice_entity` int(10) NULL DEFAULT NULL COMMENT '查看票是开给谁的' AFTER `invoice_amount`,\n" +
                "ADD COLUMN `test_t1` varchar(255) NULL COMMENT '这是才是增加' AFTER `mto_flag1`;";
        SQLStatement statement = SQLUtils.parseSingleMysqlStatement(sql);
        SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.MYSQL);
        statement.accept(visitor);

        System.out.println(visitor);

    }
}
