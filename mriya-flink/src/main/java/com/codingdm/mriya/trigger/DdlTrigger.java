package com.codingdm.mriya.trigger;

import com.codingdm.mriya.enums.EventType;
import com.codingdm.mriya.model.Message;
import lombok.extern.log4j.Log4j;
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
@Log4j
public class DdlTrigger extends Trigger<Message, TimeWindow> {

    private TriggerResult fireAndPurge(TimeWindow window, TriggerContext ctx) {
        clear(window, ctx);
        return TriggerResult.FIRE_AND_PURGE;
    }

    @Override
    public TriggerResult onElement(Message msg, long timestamp, TimeWindow window, TriggerContext ctx) {
        if (msg.getType() != EventType.INSERT
                && msg.getType() != EventType.UPDATE
                && msg.getType() != EventType.DELETE) {
            log.info("find ddl, start FIRE_AND_PURGE " + msg.getSql());
            return fireAndPurge(window, ctx);
        }
        return TriggerResult.CONTINUE;

    }

    @Override
    public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) {
        return TriggerResult.FIRE;
    }

    @Override
    public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) {

    }

    public static DdlTrigger build() {
        return new DdlTrigger();
    }
}
