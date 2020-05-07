/* 建表语句 */
CREATE TABLE IF NOT EXISTS `${tableName}` (
<#if table??>
    <#list table as field>
        `${field.field}` ${field.type}<#if field.nullable == "N"> NOT NULL</#if><#if field.remark?index_of("自增长")!=-1> AUTO_INCREMENT</#if><#if field.definition?index_of("唯一标识")!=-1 || field.remark?index_of("序列")!=-1><#assign pk = field.field></#if> COMMENT '${field.definition}<#if field.remark?? && field.remark != "">【${field.remark}】</#if>'<#if (field_index + 1 == table?size && !pk??)><#else>,</#if>
    </#list>
    <#if pk??>
        PRIMARY KEY (`${pk}`)
    </#if>
</#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${tableComment}';

/* sharding语句 */
sharding @@table name='${tableName}' set type='sharding' and sharding_algo='PartitionByMod' and sharding_id='${pk}' and dn='bigscreen_1,bigscreen_2,bigscreen_3,bigscreen_4,bigscreen_5,bigscreen_6,bigscreen_7,bigscreen_8';

