package indi.tudan.sqlgenerator.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.sqlgenerator.utils.CamelUtils;
import indi.tudan.sqlgenerator.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 读取数据库设计文档
 *
 * @author wangtan
 * @date 2019-09-19 15:46:59
 * @since 1.0
 */
@Slf4j
public class DbDesignReader {

    /**
     * 读取 excel，获取表结构对象
     *
     * @param path       Excel 路径
     * @param sheetIndex 工作簿序号
     * @date 2019-09-19 16:02:28
     */
    public static JSONObject getTables(String path, int sheetIndex) {

        // 读取数据库设计文档
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(path), sheetIndex);
        List<List<Object>> readAll = reader.read();

        // 所有表结构对象
        JSONObject tables = new JSONObject(true);

        // 当前正在处理的表名
        String tableName = "";

        // 开始遍历设计数据
        for (List<Object> row : readAll) {
//            Console.log(row);

            if (ObjectUtil.isEmpty(row)) {
                continue;
            }

            // 如果是表头，则跳过
            if (isHeader(row)) {
//                Console.log(row);
                continue;
            }

            // 原字符串
            String rawString = StringUtils.getStr(row.get(0));

            // 如果是标题行，则提取表名
            boolean isTitle = isTitle(row);
            if (isTitle) {
                tableName = getTableName(rawString);
                tables.put(tableName, new JSONObject(true)
                        .fluentPut("tableName", tableName)
                        .fluentPut("tableComment", getTableComment(rawString))
                        .fluentPut("tableCapitalStr", CamelUtils.underlineToUpperCamel(tableName))
                        .fluentPut("table", new JSONArray()));
//                Console.log(tableName);
            } else {
                JSONObject rowJSON = new JSONObject();
                rowJSON.fluentPut("field", row.get(0));
                rowJSON.fluentPut("type", row.get(1));
                rowJSON.fluentPut("nullable", row.get(2));
                rowJSON.fluentPut("definition", row.get(3));
                rowJSON.fluentPut("remark", row.get(4));
                tables.getJSONObject(tableName).getJSONArray("table").add(rowJSON);
            }
        }

        // 关闭文件
        reader.close();

        return tables;
    }

    /**
     * 获取表名
     *
     * @param rawString 原字符串
     * @return String
     * @date 2019-09-19 20:36:11
     */
    private static String getTableName(String rawString) {
        return rawString.substring(0, rawString.indexOf("（")).toLowerCase();
    }

    /**
     * 获取表注释
     *
     * @param rawString 原字符串
     * @return String
     * @date 2019-09-20 14:07:15
     */
    private static String getTableComment(String rawString) {
        return rawString.substring(rawString.indexOf("（") + 1, rawString.lastIndexOf("）"));
    }

    /**
     * 是否是标题行
     *
     * @param row 行数据
     * @return boolean
     * @date 2019-09-19 16:02:19
     */
    private static boolean isTitle(List<Object> row) {
        boolean result = true;
        if (StrUtil.isNotBlank((String) row.get(0))) {
            for (int i = 1; i < row.size(); i++) {
                if (StrUtil.isNotBlank(StringUtils.getStr(row.get(i)))) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 是否是表头
     *
     * @param row 行数据
     * @return boolean
     * @date 2019-09-19 16:10:40
     */
    private static boolean isHeader(List<Object> row) {
        try {
            return "属性名称".equals(row.get(0))
                    || "数据类型".equals(row.get(1))
                    || "是否可空".equals(row.get(2))
                    || "属性定义".equals(row.get(3))
                    || "备注".equals(row.get(4));
        } catch (IndexOutOfBoundsException e) {
            log.error("excel 内容格式有误。"/*, e*/);
            return false;
        }
    }

}
