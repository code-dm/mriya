# mriya (运输机) :airplane: 

### 介绍
使用Flink开发的实时ETL，数据从MySQL到Greenplum。使用canal解析MySQL的binlog，投放进kafka，使用Flink消费kafka并把数据组装进Greenplum，后续将会添加更多的数据源和目标源。

### 工作流程

![工作流程](http://image.wdmblog.cn/%E7%BD%91%E7%BB%9C%E6%8B%93%E6%89%91%E5%9B%BE%20%281%29.png "工作流程")

1. 利用canal解析MySQL的binary log，并将解析的log投入kafka中。
2. 使用mriya消费kafka中的消息，还原MySQL 的增删改。
3. 将MySQL的增删改转义成目标源的增删改语句

#### MySql --> PostGreSql/Greenplum(使用delete+copy方式):

1.  支持近实时级别的数据增删改

2.  支持自动创建表

```
CREATE TABLE [IF NOT EXISTS] tbl_name create_definition: {...} 
```

3.  支持MySql表结构的变更

```
ALTER TABLE tbl_name

  | ADD [COLUMN] col_name column_definition
  
  | ADD [COLUMN] (col_name column_definition,...) 
  
  | DROP [COLUMN] col_name 
  
  | MODIFY [COLUMN] col_name column_definition
  
```

4.  支持主键的修改

5.  删除表

6.  修改表名

#### MySql --> Apache Kudu(待开发):

### 工作原理
1. 从kafka中获取canal解析完成的MySQLBinary log。
2. 使用Flink的keyBy对targetTable进行分组，并使用时间窗口。
3. 自定义一个trigger，触发事件为解析到DDL语句。
4. 步骤2和步骤3组成，时间窗口+自定义trigger组合使用，如果没有DDL语句则根据时间进行滚动，如果存在DDL语句数据立即滚动。
5. 定义aggregate，将同一张表的数据进行合并去重
6. 自定义Sink，定义GreenplumSink或者其他目标数据源。


### 安装教程

1.  安装配置中心nacos
2.  安装Flink
3.  安装Greenplum

### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

### 使用docker快速体验


### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

### 同步速度
![同步速度](http://image.wdmblog.cn/Mriya-QPS.png "同步速度")
