CREATE DATABASE IF NOT EXISTS mriya DEFAULT CHARACTER SET utf8;
drop table `mriya`.`table_t1`;
CREATE TABLE `mriya`.`table_t1`  (
     `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 't1',
     `t1` varchar(100) NULL COMMENT 't1',
     `t2` varchar(200) NULL COMMENT 't2',
     `t3` float NULL COMMENT 't3 float',
     `t4` date NULL COMMENT 't4 date',
     `t5` time NULL COMMENT 't5 time',
     `t6` datetime NULL COMMENT 't6 datetime',
     `t7` timestamp NULL COMMENT 't7 timestamp',
     `t8` double NULL COMMENT 't8 double',
     `t9` text NULL COMMENT 't8 text',
     `t10` decimal(10, 3) NULL COMMENT 't10 decimal(10, 3)',
     PRIMARY KEY (`id`)
);

INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('1', '1', 1, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 1, '1', 1);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('2', '2', 2, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 2, '2', 2);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('3', '3', 3, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 3, '3', 3);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('4', '4', 4, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 4, '4', 4);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('5', '5', 5, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 5, '5', 5);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('6', '6', 6, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 6, '6', 6);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('7', '7', 7, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 7, '7', 7);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('8', '8', 8, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 8, '8', 8);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('9', '9', 9, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 9, '9', 9);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('10', '10', 10, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 10, '10', 10);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('11', '11', 11, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 11, '11', 11);
INSERT INTO mriya.table_t1 (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES ('12', '12', 12, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 12, '12', 12);

INSERT INTO mriya.table_t1 (id, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) VALUES
(20, '20', '13', 13, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 13, '13', 13),
(21, '21', '14', 14, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 14, '14', 14),
(22, '22', '15', 15, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 15, '15', 15),
(23, '23', '16', 16, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 16, '16', 16),
(24, '24', '17', 17, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 17, '17', 17),
(25, '25', '18', 18, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 18, '18', 18),
(26, '26', '19', 19, '2020-06-03', '01:00:00', '2020-06-03 09:27:28', '2020-06-03 09:27:34', 19, '19', 19);

delete from mriya.table_t1 where id in (20, 21, 22, 23, 24, 25);

-- 创建表
CREATE TABLE `mriya`.`Untitled`  (
  `id` int NOT NULL COMMENT '主键',
  `t1` varchar(255) NULL,
  `t2` bigint(10) NULL,
  `t3` text NULL,
  `t4` date NULL,
  `t5` time NULL,
  `t6` datetime NULL,
  `t7` timestamp NULL,
  `t8` float NULL,
  `t9` year NULL,
  `t10` char(1) NULL,
  `t11` smallint(10) NULL COMMENT '测试注释',
  PRIMARY KEY (`id`)
);

CREATE TABLE `mriya`.`rename_table`  (
  `id` int(255) NOT NULL,
  PRIMARY KEY (`id`)
);

RENAME TABLE mriya.rename_table TO mriya.rename_new_table;

CREATE TABLE `mriya`.`drop_table`  (
  `id` int(255) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE `mriya`.`drop_table`;

CREATE TABLE `mriya`.`alter_table`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户名',
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `idcard` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `idcard_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证扫描件',
  `telphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话1',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

ALTER TABLE `mriya`.`alter_table`
CHANGE COLUMN `telphone` `rename_column` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话1' AFTER `idcard_pic`,
CHANGE COLUMN `order_id` `update_column` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外键' AFTER `rename_column`,
MODIFY COLUMN `update_time` datetime(0) NULL DEFAULT NULL COMMENT '测试修改备注' AFTER `update_by`,
ADD COLUMN `add_column` varchar(255) NULL COMMENT '测试新增字段' AFTER `update_time`;