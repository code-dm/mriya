package com.codingdm.mriya.connection;

import com.codingdm.mriya.connection.impl.GpConnection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/9/2020 1:36 PM
 **/
@Slf4j
public class GpConnectionTest {

    @Test
    public void gpConnection() throws SQLException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            SinkConnection con = new GpConnection();
            Connection connection = con.getConnection();

            connection.createStatement().execute("select 1");

            System.out.println("sleep");
            Thread.sleep(4 * 1000);
        }

    }
}
