package com.codingdm.mriya.utils;


import com.codingdm.mriya.filter.MsgFilter;
import com.codingdm.mriya.model.Message;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @create 2019-08-19 13:17
 **/
public class MysqlUtils {
    public static final String SHOW_DATABASES = "SHOW DATABASES;";
    public static final String GET_TABLE = "SELECT table_name FROM information_schema.tables WHERE table_schema = '%s' and table_type = 'BASE TABLE';";
    public static final String CREATE_TABLE_SQL = "show create table `%s`.`%s`;";


    public MysqlUtils(){}

    /**
     * 根据topic获取table
     * @return
     */
    public static List<Message> getTableBySource(String topic, Connection con) throws Exception{
        List<Message> msTable = new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(SHOW_DATABASES);
        List<Message> databases = new ArrayList<>();
        while (resultSet.next()) {
            if(!StringUtils.isEmpty(resultSet.getString(1))){
                Message databaseMsg = new Message();
                databaseMsg.setTopic(topic);
                databaseMsg.setDatabase(resultSet.getString(1));
                databases.add(databaseMsg);
            }
        }
        databases.stream()
                .filter(MsgFilter::filter)
                .forEach(ms->{
                    try {
                        msTable.addAll(
                                MysqlUtils.getTableByDatabase(ms.getTopic(), ms.getDatabase(), con)
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return msTable;
    }

    /**
     * 根据topic and database获取table
     * @return
     */
    public static List<Message> getTableByDatabase(String source, String database, Connection con)throws SQLException{
        List<Message> msTable = new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet resultTable = statement.executeQuery(String.format(GET_TABLE, database));
        while (resultTable.next()) {
            Message message = new Message(source, database, resultTable.getString(1));
            msTable.add(message);
        }

        return msTable;
    }


    /**
     * 根据topic and database and table获取table
     * @return
     */
    public static List<Message> getTableByTable(String source, String database, String table){
        List<Message> msTable = new ArrayList<>();
        Message message = new Message(source, database, table);
        msTable.add(message);
        return msTable;
    }

    public static void getCreateTableSql(List<Message> tables, Connection con)throws SQLException{
        Statement statement = con.createStatement();
        for (Message table : tables) {
            ResultSet resultTable = statement.executeQuery(String.format(CREATE_TABLE_SQL, table.getDatabase(), table.getTable()));
            System.out.println(table.getTable());
            while (resultTable.next()) {
                table.setSql(resultTable.getString(2));
            }
        }
    }
}
