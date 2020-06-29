package com.codingdm.mriya.utils;

import com.codingdm.mriya.aggregate.AggregateMessage;
import com.codingdm.mriya.config.NacosConfig;
import com.codingdm.mriya.connection.impl.GpConnection;
import com.codingdm.mriya.constant.PropertiesConstants;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.transformer.Transformer;
import com.codingdm.mriya.transformer.impl.MysqlTransformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/28/2020 2:55 PM
 **/
@Slf4j
public class GreenPlumUtilsTest {

    @Test
    public void getCopySql() throws IOException, SQLException {
        String jsonString = "{\"data\": [{\"id\": \"26734888\", \"catalog_no\": \"Z03025\", \"name\": \"IL-18, Rat\", \"keywords\": \"Z03025-10 <b>Interleukin-18 (IL18, also known as interferon-gamma inducing factor or IGIF)</b> is a cytokine that belongs to the IL-1 superfamily and is produced by macrophages and other cells. Its biological activity is pleiotropic and it has been shown to induce interferon-gamma production in KG-1 cells. The combination of this cytokine and IL12 has been shown to inhibit IL4 dependent IgE and IgG1 production, and enhance IgG2a production in B cells.<br>Recombinant <b>rat Interleukin-18 (rrIL-18)</b> produced in <i>E. coli</i> is a single non-glycosylated polypeptide chain containing 159 amino acids. A fully biologically active molecule, rrIL-18 has a molecular mass of 18.4 kDa analyzed by reducing SDS-PAGE and is obtained by proprietary chromatographic techniques at GenScript. protein: Interleukin-18 (IL-18), Rat,Rat, NKSF, CTL maturation factor (TCMF), Cytotoxic lymphocyte maturation factor (CLMF), TSF ,Interferon-gamma-inducing factor, IGIF, IL-1g, IL1F4, IL-1 gamma,IL-1 \\u00ce\\u00b3, IL 1 gamma,IL 1 \\u00ce\\u00b3, IL1 gamma,IL1 \\u00ce\\u00b3,Interleukin-1 \\u00ce\\u00b3,Interleukin-1 gamma,Interleukin 1 \\u00ce\\u00b3,Interleukin 1 gamma, NKSF, CTL matuion factor (TCMF), Cytotoxic lymphocyte matuion factor (CLMF), TSF ,Interferon-gamma-inducing factor, IGIF, IL-1g, IL1F4, IL-1 gamma,IL-1 \\u00ce\\u00b3, IL 1 gamma,IL 1 \\u00ce\\u00b3, IL1 gamma,IL1 \\u00ce\\u00b3,Interleukin-1 \\u00ce\\u00b3,Interleukin-1 gamma,Interleukin 1 \\u00ce\\u00b3,Interleukin 1 gamma, NKSF, CTL matuion factor (TCMF), Cytotoxic lymphocyte matuion factor (CLMF), TSF ,Interferongammainducing factor, IGIF, IL1g, IL1F4, IL1 gamma,IL1 \\u00ce\\u00b3, IL 1 gamma,IL 1 \\u00ce\\u00b3, IL1 gamma,IL1 \\u00ce\\u00b3,Interleukin1 \\u00ce\\u00b3,Interleukin1 gamma,Interleukin 1 \\u00ce\\u00b3,Interleukin 1 gamma,\", \"product_id\": \"1136283\", \"order\": \"\", \"feature\": \"\", \"type\": \"45\", \"modify_date\": \"2020-06-24 16:13:46\", \"loc_code\": \"US\"}], \"id\": 8908952, \"database\": \"product\", \"ts\": 1592986426417, \"pkNames\": [\"id\"], \"sql\": \"\", \"table\": \"product_search\", \"topic\": \"mes\", \"es\": 1592986426000, \"sqlType\": {\"id\": 4, \"catalog_no\": 12, \"name\": 2005, \"keywords\": 2005, \"product_id\": 4, \"order\": 12, \"feature\": 12, \"type\": 12, \"modify_date\": 93, \"loc_code\": 1}, \"old\": null, \"isDdl\": false, \"type\": \"INSERT\", \"mysqlType\": {\"id\": \"int(11)\", \"catalog_no\": \"varchar(20)\", \"name\": \"text\", \"keywords\": \"text\", \"product_id\": \"int(11)\", \"order\": \"varchar(20)\", \"feature\": \"varchar(20)\", \"type\": \"varchar(20)\", \"modify_date\": \"timestamp\", \"loc_code\": \"char(2)\"}, \"offect\": 2144016887}";
        Message message = Message.buildMessage(jsonString, "mes");

        // 执行copy
        Transformer transformer = new MysqlTransformer();
        List<String> columnsList = transformer.getColumnsList(message);
        if(CollectionUtils.isNotEmpty(columnsList)){
            String schemaName = NacosConfig.get(PropertiesConstants.MRIYA_TARGET_DATASOURCE_SCHEMA);
            String copySql = GreenPlumUtils.getCopySql(columnsList, schemaName, message.getTargetTable());

            System.out.println(copySql);

//            AggregateMessage aggregateMessage = new AggregateMessage();
//            Message add = aggregateMessage.add(message, null);
//            byte[] bytes = GreenPlumUtils.serializeRecord(new ArrayList<>(add.getRowData()));
//            InputStream in = new ByteArrayInputStream(bytes);
//            Connection con = new GpConnection().getConnection();
//            CopyManager copyManager = new CopyManager((BaseConnection) con);
//            copyManager.copyIn(copySql, in);
//            in.close();
        }
    }

}
