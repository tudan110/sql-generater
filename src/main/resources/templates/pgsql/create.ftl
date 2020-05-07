/* 建表语句 */
CREATE TABLE IF NOT EXISTS "${tableName}" (
<#if table??>
    <#list table as field>
        "${field.field}" ${field.type}<#if field.nullable == "N"> NOT NULL</#if><#if field.definition?index_of("唯一标识")!=-1 || field.remark?index_of("序列")!=-1><#assign pk = field.field></#if><#if (field_index + 1 == table?size && !pk??)><#else>,</#if>
    </#list>
    <#if pk??>
        PRIMARY KEY ("${pk}")
    </#if>
</#if>
);

/* 字段注释 */
<#if table??>
    <#list table as row>
        <#list row as field>
COMMENT ON COLUMN "${tableName}"."${field.field}" IS '${field.definition}<#if field.remark?? && field.remark != "">【${field.remark}】</#if>';
        </#list>
    </#list>
</#if>

/* 表注释 */
COMMENT ON TABLE "${tableName}" IS '${tableComment}';

