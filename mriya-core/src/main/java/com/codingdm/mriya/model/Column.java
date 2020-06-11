package com.codingdm.mriya.model;

import com.codingdm.mriya.enums.AlterTypeEnum;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:37 PM
 **/
@Slf4j
@Data
@ToString
public class Column {

    private String name;

    private String comment;

    private String oldName;

    AlterTypeEnum alterType;

    private String type;

    private int length;

    private int numericScale;

    String defaultValue;

    boolean isPrivateKey;

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

    private String eraseName(String nameStr){
        if(StringUtils.isNoneBlank(nameStr)){
            nameStr = nameStr.replace("`", "");
        }
        return nameStr;
    }

    private String eraseComment(String commentStr){
        if(StringUtils.isNoneBlank(commentStr)){
            commentStr = commentStr.replaceAll("^[']", "")
                    .replaceAll("[']$", "");
        }
        return commentStr;
    }
}
