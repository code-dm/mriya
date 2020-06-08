package com.codingdm.mriya.transformer.imp;

import com.codingdm.mriya.model.MergeData;
import com.codingdm.mriya.model.Message;
import com.codingdm.mriya.transformer.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/8/2020 9:38 AM
 **/
@Slf4j
public class MysqlTransformer implements Transformer {

    @Override
    public List<String> getColumnsList(Message message) {
//        if(CollectionUtils.isNotEmpty(message.getMergeData())){
//            for (MergeData mergeDatum : message.getMergeData()) {
//                return mergeDatum.getData()
//                        .keySet()
//                        .stream()
//                        .sorted()
//                        .collect(Collectors.toList());
//            }
//        }
        return null;
    }

    @Override
    public String getDeleteSql(Message message) {
//        if(CollectionUtils.isNotEmpty(message.getMergeData())){
//            ArrayList<MergeData> mergeData = new ArrayList<>(message.getMergeData());
//            String pkNames = mergeData.get(0).getPkNames();
//            List<String> collect = mergeData
//                    .stream()
//                    .map(m -> m.getData().get(pkNames))
//                    .collect(Collectors.toList());
//
//        }
        return null;
    }

    @Override
    public List<String> getDdlSql(Message message) {
        return null;
    }

}
