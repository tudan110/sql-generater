/*建表语句*/
CREATE TABLE IF NOT EXISTS `${tableName}` (
<#if table?exists>
    <#list table as row>
        <#list row as field>
            `${field.field}` ${field.type} <#if field.nullable == "N">NOT NULL</#if> <#if field.remark?index_of("自增长")!=-1>AUTO_INCREMENT</#if> <#if field.definition?index_of("唯一标识")!=-1>PRIMARY KEY</#if>,
            <#--field:${field.field}
            type:${field.type}
            nullable:${field.nullable}
            definition:${field.definition}
            remark:${field.remark}-->
        </#list>
    </#list>
    <#--PRIMARY KEY (`id`),-->
</#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*sharding语句*/
sharding @@table name='${tableName}' set type='sharding' and sharding_algo='PartitionByMod' and sharding_id='imsi' and dn='bigscreen_1,bigscreen_2,bigscreen_3,bigscreen_4,bigscreen_5,bigscreen_6,bigscreen_7,bigscreen_8';

