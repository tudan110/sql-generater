package indi.tudan.ddlgenerater.excel;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.ddlgenerater.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取表定义
 *
 * @author wangtan
 * @date 2019-09-19 15:46:59
 * @since 1.0
 */
public class DDLReader {

    /**
     * 读取 excel
     *
     * @date 2019-09-19 16:02:28
     */
    public static void reader(String path, int sheetIndex) {

        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(path), sheetIndex);
        List<List<Object>> readAll = reader.read();
        List<JSONObject> tables = new ArrayList<>();
        for (List<Object> row : readAll) {
            boolean isTitle = isTitle(row);
            if (isTitle) {
                String str = StringUtils.getStr(row.get(0));
                String tableName = str.substring(0, str.indexOf("（")).toLowerCase();
                Console.log(tableName);
            }
            /*if (isHeader(row)) {
                Console.log(row);
            }*/
        }
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
        return "属性名称".equals(row.get(0))
                || "数据类型".equals(row.get(1))
                || "是否可空".equals(row.get(2))
                || "属性定义".equals(row.get(3))
                || "备注".equals(row.get(4));
    }

    public static void main(String[] args) {
        reader("C:\\Users\\tudan\\Desktop\\差异化结算大屏\\集团大屏新专题与预警模型0919 - 副本.xlsx", 0);
    }
}
