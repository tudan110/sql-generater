package indi.tudan.ddlgenerater.service;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.ddlgenerater.utils.ExcelUtils;
import indi.tudan.ddlgenerater.utils.FreemarkerUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 创建表 DDL 生成器
 *
 * @author wangtan
 * @date 2019-09-19 16:40:10
 * @since 1.0
 */
@Data
@Builder
public class TableCreater {

    /**
     * 模板名称
     */
    private String ftlName;

    /**
     * 解析 Excel 所有工作簿
     *
     * @param path Excel 路径
     * @author wangtan
     * @date 2019-09-19 21:47:04
     * @since 1.0
     */
    public void parseTablesDDL(String path) {
        for (int i = 0, len = ExcelUtils.getNumberOfSheets(path); i < len; i++) {
            parseTablesDDL(path, i);
        }
    }

    /**
     * 解析 Excel 指定工作簿
     *
     * @param path       Excel 路径
     * @param sheetIndex 工作簿序号
     * @author wangtan
     * @date 2019-09-19 21:24:22
     * @since 1.0
     */
    public void parseTablesDDL(String path, int sheetIndex) {
        JSONObject tables = DDLReader.getTables(path, sheetIndex);
        for (Map.Entry<String, Object> table : tables.entrySet()) {
            parseTableDDL((JSONObject) table.getValue());
        }
    }

    /**
     * 解析单个表 DDL
     *
     * @param table 表结构对象
     * @date 2019-09-19 21:02:59
     */
    private void parseTableDDL(JSONObject table) {
        //System.out.println(FreemarkerUtil.parseFTL(ftlName, table));

        FileWriter writer = new FileWriter(
                StrUtil.format("C:/Users/tudan/Desktop/差异化结算大屏/ddl/{}.sql",
                        table.getString("tableName")));
        writer.write(FreemarkerUtil.parseFTL(ftlName, table));
    }

}
