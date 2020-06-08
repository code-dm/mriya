package com.codingdm.mriya.copy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/8/2020 1:26 PM
 **/
@Slf4j
public class CopyManagerTest {

    private String constructColumnNameList(List<String> columnList) {
        List<String> columns = new ArrayList<String>();

        for (String column : columnList) {
            if (column.endsWith("\"") && column.startsWith("\"")) {
                columns.add(column);
            } else {
                columns.add("\"" + column + "\"");
            }
        }

        return StringUtils.join(columns, ",");
    }

    @Test
    public void getCopySql(){
        List<String> columnList = Arrays.asList("id", "t1", "t2");
        String tableName = "test_table";

        StringBuilder sb = new StringBuilder().append("COPY ").append(tableName).append("(")
                .append(constructColumnNameList(columnList))
                .append(") FROM STDIN WITH DELIMITER '|' NULL '' CSV QUOTE '\"' ESCAPE E'\\\\';");

        System.out.println(sb.toString());

    }
}
