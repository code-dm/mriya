package com.codingdm.mriya.utils;

import com.codingdm.mriya.enums.EventType;
import com.codingdm.mriya.model.ColumnData;
import com.codingdm.mriya.model.RowData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author wudongming1
 * @email wdmcode@aliyun.com
 * @Date 6/9/2020 3:20 PM
 **/
@Slf4j
public class GreenPlumUtils {

    private static final char QUOTE = '"';
    private static final char ESCAPE = '\\';
    private static final char FIELD_DELIMITER = '|';
    private static final char NEWLINE = '\n';
    private static final char COMMA = ',';
    private static final char POINT = '.';

    private static String constructColumnNameList(List<String> columnList) {
        List<String> columns = new ArrayList<String>();

        for (String column : columnList) {
            if (column.endsWith("\"") && column.startsWith("\"")) {
                columns.add(column);
            } else {
                columns.add("\"" + column + "\"");
            }
        }

        return StringUtils.join(columns, COMMA);
    }


    public static String getCopySql(List<String> columnList, String schemaName, String tableName){
        if(CollectionUtils.isNotEmpty(columnList)){
            StringBuilder sb = new StringBuilder()
                    .append("COPY ")
                    .append(QUOTE)
                    .append(schemaName)
                    .append(QUOTE)
                    .append(POINT)
                    .append(QUOTE)
                    .append(tableName)
                    .append(QUOTE)
                    .append("(")
                    .append(constructColumnNameList(columnList))
                    .append(") FROM STDIN WITH DELIMITER '|' NULL '' CSV QUOTE '\"' ESCAPE E'\\\\';");
            return sb.toString();
        }
        return null;
    }
    /**
     * Any occurrence within the value of a QUOTE character or the ESCAPE
     * character is preceded by the escape character.
     */
    private static String escapeString(String data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length(); ++i) {
            char c = data.charAt(i);
            switch (c) {
                case 0x00:
                    log.warn("字符串中发现非法字符 0x00，已经将其删除");
                    continue;
                case QUOTE:
                case ESCAPE:
                    sb.append(ESCAPE);
            }

            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Non-printable characters are inserted as '\nnn' (octal) and '\' as '\\'.
     */
    private static String escapeBinary(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; ++i) {
            if (data[i] == '\\') {
                sb.append('\\');
                sb.append('\\');
            } else if (data[i] < 0x20 || data[i] > 0x7e) {
                byte b = data[i];
                char[] val = new char[3];
                val[2] = (char) ((b & 07) + '0');
                b >>= 3;
                val[1] = (char) ((b & 07) + '0');
                b >>= 3;
                val[0] = (char) ((b & 03) + '0');
                sb.append('\\');
                sb.append(val);
            } else {
                sb.append((char) (data[i]));
            }
        }
        return sb.toString();
    }

    /**
     * 将ColumnData转换成byte
     * @param rowData
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] serializeRecord(List<RowData> rowData) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (RowData row : rowData) {
            List<ColumnData> columnDataList = row.getData();
            if(row.getType() == EventType.DELETE){
                continue;
            }
            for (int i = 0; i < columnDataList.size(); i++) {
                int columnSqlType = columnDataList.get(i).getType();
                Object columnValue = columnDataList.get(i).getColumnValue();

                switch (columnSqlType) {
                    case Types.CHAR:
                    case Types.NCHAR:
                    case Types.VARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGNVARCHAR: {
                        if (columnValue != null) {
                            String dataString = columnValue.toString();
                            sb.append(QUOTE);
                            sb.append(escapeString(dataString));
                            sb.append(QUOTE);
                        }
                        break;
                    }
                    case Types.BINARY:
                    case Types.BLOB:
                    case Types.CLOB:
                    case Types.LONGVARBINARY:
                    case Types.NCLOB:
                    case Types.VARBINARY: {
                        if(columnValue != null){
                            byte[] data = columnValue.toString().getBytes();
                            sb.append(escapeBinary(data));
                        }
                        break;
                    }
                    default: {
                        if (columnValue != null) {
                            sb.append(columnValue.toString());
                        }
                        break;
                    }
                }

                if (i + 1 < columnDataList.size()) {
                    sb.append(FIELD_DELIMITER);
                }
            }
            sb.append(NEWLINE);
        }
        return sb.toString().getBytes("UTF-8");
    }
}
