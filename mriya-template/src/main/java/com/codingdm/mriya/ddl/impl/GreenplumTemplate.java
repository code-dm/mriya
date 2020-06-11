package com.codingdm.mriya.ddl.impl;

import com.codingdm.mriya.SqlParser;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.constant.TemplateConstant;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.model.Column;
import com.codingdm.mriya.model.GPColumn;
import com.codingdm.mriya.mysql.MysqlSqlParserImpl;
import com.codingdm.mriya.utils.TemplateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mriya
 * @description: Greenplum SQL模板
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/09 21:34
 */
public class GreenplumTemplate implements DDLTemplate {

    private final SqlParser sqlParser;

    public GreenplumTemplate(){
        sqlParser = new MysqlSqlParserImpl();
    }

    @Override
    public String alterSql(String sql, String tableName) {
        // 首先解析sql
        List<Column> columns = sqlParser.parserAlterSql(sql);

        List<String> alterList = new ArrayList<>();
        List<String> comments = new ArrayList<>();

        for (Column column : columns) {
            GPColumn gpColumn = new GPColumn(column.toMap());
            gpColumn.setSchema(NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA));
            gpColumn.setTable(tableName);
            switch (gpColumn.getAlterType()){
                case DROP:
                    alterList.add(rendering(TemplateConstant.ALTER_DROP_COLUMN, gpColumn));
                    break;
                case CHANGE:
                    alterList.add(rendering(TemplateConstant.ALTER_CHANGE_COLUMN, gpColumn));
                    break;
                case MODIFY:
                    alterList.add(rendering(TemplateConstant.ALTER_MODIFY_COLUMN, gpColumn));
                    getCommentSql(gpColumn, comments);
                    break;
                case ADD:
                    alterList.add(rendering(TemplateConstant.ALTER_ADD_COLUMN, gpColumn));
                    getCommentSql(gpColumn, comments);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + column.getType());
            }
        }
        if (alterList.size() > 0){
            return StringUtils.join(alterList, CommonConstants.EMPTY).concat(StringUtils.join(comments, CommonConstants.EMPTY));
        }
        return null;
    }

    @Override
    public String createSql(String sql) {
        List<Column> columns = sqlParser.parserAlterSql(sql);



        return null;
    }

    @Override
    public String renameTableSql(String sql) {

        return null;
    }

    @Override
    public String eraseTable(String sql) {
        return null;
    }

    private String rendering(String template, GPColumn column) {
        return TemplateUtil.rendering(template, column);
    }

    /**
     * 拼接 alter table 的 备注sql
     * @param column
     * @return
     */
    private void getCommentSql(GPColumn column, List<String> comments){
        if(StringUtils.isNotBlank(column.getComment())) {
            comments.add(TemplateUtil.rendering(TemplateConstant.COLUMN_COMMENT, column));
        }
    }
}
