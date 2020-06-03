package com.codingdm.mriya.trigger;

import com.codingdm.mriya.common.enums.EventType;
import com.codingdm.mriya.model.Message;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * @program: mriya
 * @description: DDL change trigger
 * @author: wudongming
 * @Email wdmcode@aliyun.com
 * @created: 2020/06/02 21:36
 */
public class DdlTrigger extends Trigger<Message, TimeWindow> {

    private TriggerResult fireAndPurge(TimeWindow window, TriggerContext ctx){
        clear(window, ctx);
        return TriggerResult.FIRE_AND_PURGE;
    }

    @Override
    public TriggerResult onElement(Message msg, long timestamp, TimeWindow window, TriggerContext ctx) {
        if(msg.getType() != EventType.INSERT
                && msg.getType() != EventType.UPDATE
                && msg.getType() != EventType.DELETE){
            return fireAndPurge(window, ctx);
        }
//        if (timestamp >= window.getEnd()) {
//            return fireAndPurge(window, ctx);
//        } else {
            return TriggerResult.CONTINUE;
//        }
    }

    @Override
    public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext){
        return TriggerResult.FIRE;
    }

    @Override
    public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext){
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx){

    }

    public static DdlTrigger build(){
        return new DdlTrigger();
    }
}
