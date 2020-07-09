# mriya (运输机) :airplane: 

### 介绍
使用Flink开发的实时ETL，数据从MySQL到Greenplum。使用canal解析MySQL的binlog，投放进kafka，使用Flink消费kafka并把数据组装进Greenplum，后续将会添加更多的数据源和目标源。

### 工作流程

![工作流程](http://image.wdmblog.cn/%E7%BD%91%E7%BB%9C%E6%8B%93%E6%89%91%E5%9B%BE%20%281%29.png "工作流程")

1. 利用canal解析MySQL的binary log，并将解析的log投入kafka中。
2. 使用mriya消费kafka中的消息，还原MySQL 的增删改。
3. 将MySQL的增删改转义成目标源的增删改语句

### 特性

1. 基于binlog的近实时同步ETL
2. 支持自动化表创建，自动化DDL变更同步
3. 使用nacos注册中心同步，变更配置不需要重启
4. 后续添加支持多目标源

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

### docker 极速体验
```
git clone https://github.com/JeasonPeople/mriya.git
cd docker-compose
docker-compose up
```
1. 访问http://docker-ip:8848/nacos修改配置(默认账号nacos/nacos)
在public下新增Properties文件, Data ID=MRIYA, group=MRIYA_GROUP

```
mriya.source.kafka.bootstrap.servers=kafka:9092
mriya.source.kafka.zookeeper.connect=zk:2181
mriya.source.kafka.group.id=dw-etl-prod-gp6
mriya.source.kafka.auto.offset.reset=earliest
mriya.source.kafka.topic=mriya

mriya.target.datasource.type=greenplum
mriya.target.datasource.url=jdbc:postgresql://greenplum:5432/gsdw?serverTimezone=GMT+8
mriya.target.datasource.schema=dw_ods
mriya.target.datasource.username=gpadmin
mriya.target.datasource.password=pivotal
# 支持freemarker语法，${table}为必写项
mriya.table.name.template=${topic}_${database}_${table}

# psql -d template1 -c "alter user gpadmin password 'pivotal'"
# mriya.message.filer=${topic}-${database}-${table}
# mriya.message.filer=mes-accounting_bak-*
```
2. 使用gpadmin账号连接greenplum创建database以及schema(默认账号root/pivotal gpadmin/pivotal)
```
CREATE DATABASE "mriya";
CREATE SCHEMA "dw_ods";
```
3. 访问http://docker-ip:8081/#/submit提交jar并运行jar

4. 使用连接工具连接MySql(默认账号root/Mriya@Mriya)运行sql
```aidl
CREATE DATABASE `mriya`;
CREATE TABLE `mriya`.`table_1`  (
  `k1` int(10) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `c1` varchar(255) NULL,
  `c2` varchar(255) NULL,
  `c3` varchar(255) NULL,
  `c4` datetime(2) NULL,
  PRIMARY KEY (`k1`)
);

```

### 安装教程
1.  安装MySql
2.  安装canal
3.  安装kafka
4.  安装zookeeper

1-4 安装教程(https://github.com/alibaba/canal/wiki)

5.  安装配置中心nacos

nacos 安装教程(https://nacos.io/zh-cn/docs/deployment.html)

6.  安装Flink

单机版安装(https://ci.apache.org/projects/flink/flink-docs-release-1.10/ops/deployment/cluster_setup.html#starting-flink)

7.  安装Greenplum

docker安装Greenplum
```
docker pull datagrip/greenplum
docker run -it -p 5432:5432 datagrip/greenplum
```
用户名: gpadmin 密码: pivotal
用户名: root 密码: pivotal 

### 使用说明

1.  使用源码编译
``` 
git clone https://github.com/JeasonPeople/mriya.git
cd mriya
mvn install -Dmaven.test.skip=true
cd mriya-flink/target
```
将打包好的jar包通过Flink Web上传并执行

### 同步速度
![同步速度](http://image.wdmblog.cn/Mriya-QPS.png "同步速度")
![同步速度](http://image.wdmblog.cn/Mriya-QPS2.png "同步速度")

### 添加管理员微信进入技术群
![工作流程](http://image.wdmblog.cn/codingdm2.jpg)

### 关注公众号

![工作流程](http://image.wdmblog.cn/qrcode_for_gh_e6fd53510eba_258.jpg "工作流程")

