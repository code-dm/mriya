CREATE DATABASE IF NOT EXISTS mriya DEFAULT CHARACTER SET utf8;

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