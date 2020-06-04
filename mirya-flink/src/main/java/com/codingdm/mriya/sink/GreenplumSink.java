package com.codingdm.mriya.sink;

import com.codingdm.mriya.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 6/4/2020 3:21 PM
 **/
@Slf4j
public class GreenplumSink extends RichSourceFunction<Message> {
    @Override
    public void run(SourceContext<Message> sourceContext) throws Exception {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }
}
