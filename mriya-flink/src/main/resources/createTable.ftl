CREATE TABLE "${schema}"."${table}"
(
<#list gpColumns as gpColumn>
    "${gpColumn.name}" ${gpColumn.pgColumnType},
</#list>
PRIMARY KEY (${primaryKeys?join(", ")})
 )
distributed by(${primaryKeys?join(", ")});
<#list gpColumns as gpColumn>
<#if (gpColumn.comment)?default("")?length gt 1 >COMMENT ON COLUMN "${schema}"."${table}"."${gpColumn.name}" IS '${gpColumn.comment}';</#if>
</#list>