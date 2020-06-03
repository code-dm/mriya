package com.codingdm.mriya.common.enums;

/**
 * Protobuf enum {@code com.alibaba.otter.canal.protocol.EventType}
 *
 * <pre>
 ** 事件类型 *
 * </pre>
 */

public enum EventType {
    /**
     * <code>INSERT = 1;</code>
     */
    INSERT,
    /**
     * <code>UPDATE = 2;</code>
     */
    UPDATE,
    /**
     * <code>DELETE = 3;</code>
     */
    DELETE,
    /**
     * <code>CREATE = 4;</code>
     */
    CREATE,
    /**
     * <code>ALTER = 5;</code>
     */
    ALTER,
    /**
     * <code>ERASE = 6;</code>
     */
    ERASE,
    /**
     * <code>QUERY = 7;</code>
     */
    QUERY,
    /**
     * <code>TRUNCATE = 8;</code>
     */
    TRUNCATE,
    /**
     * <code>RENAME = 9;</code>
     */
    RENAME,
    /**
     * <code>CINDEX = 10;</code>
     *
     * <pre>
     **CREATE INDEX*
     * </pre>
     */
    CINDEX,
    /**
     * <code>DINDEX = 11;</code>
     */
    DINDEX,
    /**
     * <code>GTID = 12;</code>
     */
    GTID,
    /**
     * <code>XACOMMIT = 13;</code>
     *
     * <pre>
     ** XA *
     * </pre>
     */
    XACOMMIT,
    /**
     * <code>XAROLLBACK = 14;</code>
     */
    XAROLLBACK,
    /**
     * <code>MHEARTBEAT = 15;</code>
     *
     * <pre>
     ** MASTER HEARTBEAT *
     * </pre>
     */
    MHEARTBEAT,
    ;


}

