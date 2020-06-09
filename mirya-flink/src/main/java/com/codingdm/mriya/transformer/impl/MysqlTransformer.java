package com.codingdm.mriya.transformer.impl;

import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.constant.SqlConstants;
import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.RowData;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.transformer.Transformer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/8/2020 9:38 AM
 **/
@Slf4j
public class MysqlTransformer implements Transformer {
    private final int DELETE_PARTITION_SIZE = 500;

    @Override
    public List<String> getColumnsList(Message message) {
        if(message.getSqlType() != null){
                return message.getSqlType()
                        .keySet()
                        .stream()
                        .sorted(Comparator.comparingInt(String::hashCode))
                        .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getDeleteSql(Message message) {
        if(CollectionUtils.isNotEmpty(message.getRowData())){
            List<RowData> rowData = new ArrayList<>(message.getRowData());
            List<String> primaryKeyNames = new ArrayList<>(message.getPkNames());
            String primaryKeyName = String.join(CommonConstants.LINE_DOUBLE, primaryKeyNames);

            List<String> pkValuesList = rowData.stream()
                    .map(md -> {
                        StringBuilder pkValue = new StringBuilder();
                        for (ColumnData datum : md.getData()) {
                            for (String keyName : primaryKeyNames) {
                                if (keyName.equals(datum.getColumnName())) {
                                    pkValue.append(datum.getColumnValue());
                                }
                            }
                        }
                        return pkValue.toString();
                    })
                    .map(s-> String.format(CommonConstants.PERCENT_S, s))
                    .collect(Collectors.toList());

            List<List<String>> pkValuesPartition = Lists.partition(pkValuesList, DELETE_PARTITION_SIZE);

            return pkValuesPartition.stream()
                    .map(v-> {
                        StringBuilder deleteSql = new StringBuilder();
                        String whereStr = String.join(CommonConstants.SEPARATOR, v);
                        deleteSql.append(String.format(
                                SqlConstants.DELETE_SQL,
                                NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA),
                                message.getTargetTable(),
                                primaryKeyName,
                                whereStr));
                        return deleteSql.toString();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getDdlSql(Message message) {
        return null;
    }

}
