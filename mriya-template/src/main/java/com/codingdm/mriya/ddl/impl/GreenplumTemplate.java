package com.codingdm.mriya.ddl.impl;

import com.codingdm.mriya.SqlParser;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.constant.TemplateConstant;
import com.codingdm.mriya.ddl.DDLTemplate;
import com.codingdm.mriya.model.Column;
import com.codingdm.mriya.model.GPColumn;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.mysql.MysqlSqlParserImpl;
import com.codingdm.mriya.utils.TemplateUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: mriya
 * @description: Greenplum SQL模板
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/09 21:34
 */
@Log4j
public class GreenplumTemplate implements DDLTemplate {
    private final static String SCHEMA = "schema";
    private final static String TABLE = "table";
    private final static String GPCOLUMNS = "gpColumns";
    private final static String PRIMARYKEYS = "primaryKeys";
    private final static String OLDTABLENAME = "oldTableName";

    private final SqlParser sqlParser;

    public GreenplumTemplate(){
        sqlParser = new MysqlSqlParserImpl();
    }

    @Override
    public String alterSql(String sql, String tableName, String schema) {
        StringBuilder sqlBuild = new StringBuilder();
        // 首先解析sql
        List<Column> columns = sqlParser.parserAlterSql(sql);

        List<String> alterList = new ArrayList<>();
        List<String> comments = new ArrayList<>();

        for (Column column : columns) {
            GPColumn gpColumn = new GPColumn(column.toMap());
            gpColumn.setSchema(schema);
            gpColumn.setTable(tableName);
            switch (gpColumn.getAlterType()){
                case DROP:
                    String dropSql = rendering(TemplateConstant.ALTER_DROP_COLUMN, gpColumn);
                    if(!alterList.contains(dropSql)){
                        alterList.add(dropSql);
                    }
                    break;
                case CHANGE:
                    String changeSql = rendering(TemplateConstant.ALTER_CHANGE_COLUMN, gpColumn);
                    if(!alterList.contains(changeSql)){
                        alterList.add(changeSql);
                    }
                    break;
                case MODIFY:
                    String modifySql = rendering(TemplateConstant.ALTER_MODIFY_COLUMN, gpColumn);
                    if(!alterList.contains(modifySql)){
                        alterList.add(modifySql);
                    }
                    getCommentSql(gpColumn, comments);
                    break;
                case ADD:
                    String addSql = rendering(TemplateConstant.ALTER_ADD_COLUMN, gpColumn);
                    if(!alterList.contains(addSql)){
                        alterList.add(addSql);
                    }
                    getCommentSql(gpColumn, comments);
                    break;
                case UPDATE_PRIMARY:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + column.getType());
            }
        }
        if (alterList.size() > 0){
            sqlBuild.append(StringUtils.join(alterList, CommonConstants.EMPTY).concat(StringUtils.join(comments, CommonConstants.EMPTY)));
        }
        // 处理主键更新
        List<String> primaries = columns.stream()
                .filter(Column::getIsUpdatePrivateKey)
                .map(Column::getName)
                .map(s->String.format(CommonConstants.PERCENT_S_DOUBLE, s))
                .collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(primaries)){
            Map<String, Object> model = new HashMap<>();
            model.put(SCHEMA, schema);
            model.put(TABLE, tableName);
            model.put(PRIMARYKEYS, primaries);
            sqlBuild.append(TemplateUtil.rendering(TemplateConstant.UPDATE_PRIMARY, model));
        }
        return sqlBuild.toString();
    }

    @Override
    public String createSql(String sql, String tableName, String schema) {
        List<Column> columns = sqlParser.parserAlterSql(sql);
        if(CollectionUtils.isNotEmpty(columns)){
            List<GPColumn> gpColumns = columns.stream()
                    .map(c -> new GPColumn(c.toMap()))
                    .collect(Collectors.toList());
            List<String> primaryKeys = columns.stream()
                    .filter(Column::getIsPrivateKey)
                    .map(Column::getName)
                    .map(s->String.format(CommonConstants.PERCENT_S_DOUBLE, s))
                    .collect(Collectors.toList());
            if(primaryKeys.size() > 0){
                Map<String, Object> model = new HashMap<>();
                model.put(SCHEMA, schema);
                model.put(TABLE, tableName);
                model.put(GPCOLUMNS, gpColumns);
                model.put(PRIMARYKEYS, primaryKeys);
                return TemplateUtil.rendering(TemplateConstant.CREATE_TABLE_DISTRIBUTED, model);
            }else {
                log.error(tableName + " not have primary key");
            }
        }
        return "";
    }

    @Override
    public String renameTableSql(String sql, String tableName, String schema, String oldTableFormat) {
        String oldTableName = sqlParser.renameTable(sql);
        if(StringUtils.isNotBlank(oldTableName)){
            Map<String, String> model = new HashMap<>(3);
            model.put(SCHEMA, schema);
            model.put(TABLE, tableName);
            Message message = new Message();
            message.setTable(oldTableName);
            model.put(OLDTABLENAME, TemplateUtil.rendering(oldTableFormat, message));
            return TemplateUtil.rendering(TemplateConstant.TABLE_RENAME, model);
        }
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
