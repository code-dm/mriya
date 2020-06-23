package com.codingdm.mriya.model;

import com.codingdm.mriya.constant.CommonConstants;
import com.codingdm.mriya.enums.PgEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/10/2020 3:43 PM
 **/
@Slf4j
@Data
public class GPColumn extends Column{

    private String schema;

    private String table;

    private String pgColumnType;

    public GPColumn() {}

    public GPColumn(Map<?, ?> map){
        try {
            BeanUtils.populate(this, (Map<String, ? extends Object>) map);
            if(StringUtils.isNoneBlank(getType())){
                setPgColumnType(PgEnum.getMapping(getType().toUpperCase()).concat(getFieldLength()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Map to GPColumn Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }


    private String getFieldLength(){
        if (!StringUtils.isEmpty(getType())) {
            String typeName = getType().toUpperCase();
            if (CommonConstants.DECIMAL.equals(typeName)
                    || CommonConstants.NUMERIC.equals(typeName)) {
                return String.format(CommonConstants.FIELD_LENGTH, getLength(), getNumericScale());
            }else if(CommonConstants.BIT.equals(typeName)
                    || CommonConstants.BPCHAR.equals(typeName)){
                return String.format(CommonConstants.FIELD_LENGTH_ONLY, getLength());
            }
        }
        return "";
    }
}
