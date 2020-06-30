package com.codingdm.mriya.model;



import com.codingdm.mriya.enums.EventType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 6/4/2020 1:24 PM
 **/
@Slf4j
@Data
public class RowData {
    /**
     * 用于数据合并的唯一键 组成：targetTableName+主键字段名+主键值
     */
    private String pkValuesId;

    private EventType type;

    private List<ColumnData> data;

    public RowData(String pkValuesId, EventType type, List<ColumnData> data) {
        setPkValuesId(pkValuesId);
        setType(type);
        setData(data);
    }

    public static RowData build(String pkValuesId, EventType type, List<ColumnData> data){
        return new RowData(pkValuesId, type, data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkValuesId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RowData rowData = (RowData) o;

        return new EqualsBuilder()
                .append(pkValuesId, rowData.pkValuesId)
                .isEquals();
    }

    public List<ColumnData> getData() {
        if(data != null){
            return data.stream()
                    .sorted(Comparator.comparingInt(o -> o.getColumnName().hashCode()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
