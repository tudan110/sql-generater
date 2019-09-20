package indi.tudan.sqlgenerater.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.sqlgenerater.utils.ExcelUtils;
import indi.tudan.sqlgenerater.utils.FreemarkerUtil;
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
public class SQLGenerater {

    /**
     * 模板名称
     */
    private String ftlName;

    /**
     * 程序开始
     *
     * @param args 控制台输入参数
     * @date 2019-09-20 15:25:35
     */
    public static void exec(String... args) {

        // 默认输入 Excel 路径
        String excelPath = "C:/Users/tudan/Desktop/差异化结算大屏/集团大屏新专题与预警模型0919.xlsx";

        // 默认 sql 输出路径
        String resultPath = "C:/Users/tudan/Desktop/差异化结算大屏/ddl1";

        // 默认模式：console:打印到控制台；file:生成 sql 文件
        String mode = "console";

        // 默认类型
        String type = "create";

        if (ObjectUtil.isNotEmpty(args) || args.length != 0) {
            for (String arg : args) {
                int equalIndex = arg.indexOf("=");
                switch (arg.substring(0, equalIndex)) {

                    // 输入文件
                    case "--ep":
                    case "--path":
                    case "--p":
                        excelPath = arg.substring(equalIndex + 1);
                        break;

                    // 输出模式
                    case "--m":
                    case "--mode":
                        mode = arg.substring(equalIndex + 1);
                        break;

                    // 输出路径
                    case "--rp":
                    case "--r":
                    case "--result":
                        resultPath = arg.substring(equalIndex + 1);
                        break;

                    // 什么类型
                    case "--type":
                    case "--t":
                        type = arg.substring(equalIndex + 1);
                        break;
                    default:
                        break;
                }
            }
        }

        check(excelPath, mode, resultPath, type);

        SQLGenerater.builder().ftlName(type).build().parseTablesDDL(excelPath, mode, resultPath);
    }

    /**
     * 检查
     *
     * @param excelPath  Excel 路径
     * @param mode       输出模式
     * @param resultPath 输出路径
     * @param type       类型
     * @date 2019-09-20 16:08:21
     */
    private static void check(String excelPath, String mode, String resultPath, String type) {
        System.out.println(StrUtil.format("\n生成 {} DDL", type));
        System.out.println(StrUtil.format("待解析文件：{}", excelPath));
        if (!FileUtil.isFile(excelPath)) {
            System.out.println("Excel 文件不存在");
            System.exit(0);
        }
        System.out.println(StrUtil.format("输出模式：{}", mode));
        if ("file".equals(mode)) {
            System.out.println(StrUtil.format("输出路径：{}\n", resultPath));
            if (!FileUtil.isDirectory(resultPath)) {
                System.out.println("输出路径不存在，程序自动创建");
                FileUtil.mkdir(resultPath);
            }
        } else {
            System.out.println();
        }
    }

    /**
     * 解析 Excel 所有工作簿
     *
     * @param excelPath  Excel 路径
     * @param mode       输出模式
     * @param resultPath 输出路径
     * @date 2019-09-19 21:47:04
     */
    public void parseTablesDDL(String excelPath, String mode, String resultPath) {
        for (int i = 0, len = ExcelUtils.getNumberOfSheets(excelPath); i < len; i++) {
            parseTablesDDL(excelPath, i, mode, resultPath);
        }
    }

    /**
     * 解析 Excel 指定工作簿
     *
     * @param path       Excel 路径
     * @param sheetIndex 工作簿序号
     * @param mode       输出模式
     * @param resultPath 输出路径
     * @date 2019-09-19 21:24:22
     */
    public void parseTablesDDL(String path, int sheetIndex, String mode, String resultPath) {
        JSONObject tables = SQLReader.getTables(path, sheetIndex);
        for (Map.Entry<String, Object> table : tables.entrySet()) {
            parseTableDDL((JSONObject) table.getValue(), mode, resultPath);
        }
    }

    /**
     * 解析单个表 DDL
     *
     * @param table      表结构对象
     * @param mode       输出模式
     * @param resultPath 输出路径
     * @date 2019-09-19 21:02:59
     */
    private void parseTableDDL(JSONObject table, String mode, String resultPath) {

        switch (mode) {
            case "console":

                System.out.println(FreemarkerUtil.parseFTL(ftlName, table));
                break;
            case "file":

                FileWriter writer = new FileWriter(
                        StrUtil.format("{}/{}.sql",
                                resultPath,
                                table.getString("tableName")));
                writer.write(FreemarkerUtil.parseFTL(ftlName, table));
                break;
            default:
                break;
        }
    }

}
