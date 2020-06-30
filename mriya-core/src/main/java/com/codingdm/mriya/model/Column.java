package com.codingdm.mriya.model;

import com.codingdm.mriya.enums.AlterTypeEnum;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * @author wudongming
 * @email wdmcode@aliyun.com
 * @Date 5/27/2020 1:37 PM
 **/
@Slf4j
@Data
@ToString
public class Column {

    private String name;

    private String comment;

    private String oldName;

    private AlterTypeEnum alterType;

    private String type;

    private int length;

    private int numericScale;

    private String defaultValue;

    private Boolean isPrivateKey;

    private Boolean isUpdatePrivateKey = false;

    public Column(){

    }
    public Column(String name){
        this.name = name;
    }

    public Map<?, ?> toMap(){

        return new BeanMap(this);
    }

    public void setOldName(String oldName) {

        this.oldName = eraseName(oldName);
    }

    public void setName(String name) {
        this.name = eraseName(name);
    }

    public void setComment(String comment) {
        this.comment = eraseComment(comment);
    }

    public static String eraseName(String nameStr){
        if(StringUtils.isNoneBlank(nameStr)){
            nameStr = nameStr.replace("`", "");
        }
        return nameStr;
    }

    public static String eraseComment(String commentStr){
        if(StringUtils.isNoneBlank(commentStr)){
            commentStr = commentStr.replaceAll("^[']", "")
                    .replaceAll("[']$", "");
        }
        return commentStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        return new EqualsBuilder()
                .append(name, column.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }
}
