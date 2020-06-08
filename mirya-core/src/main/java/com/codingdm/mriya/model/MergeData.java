package com.codingdm.mriya.model;



import com.codingdm.mriya.enums.EventType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.List;
import java.util.Objects;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 1:24 PM
 **/
@Slf4j
@Data
public class MergeData {
    /**
     * 用于数据合并的唯一键 组成：targetTableName+主键字段名+主键值
     */
    private String pkValuesId;

    private EventType type;

    private List<ColumnData> data;

    public MergeData(String pkValuesId, EventType type, List<ColumnData> data) {
        setPkValuesId(pkValuesId);
        setType(type);
        setData(data);
    }

    public static MergeData build(String pkValuesId, EventType type, List<ColumnData> data){
        return new MergeData(pkValuesId, type, data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkValuesId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MergeData mergeData = (MergeData) o;

        return new EqualsBuilder()
                .append(pkValuesId, mergeData.pkValuesId)
                .isEquals();
    }
}
