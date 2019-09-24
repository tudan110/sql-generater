package indi.tudan.sqlgenerater.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import indi.tudan.sqlgenerater.utils.ExcelUtils;
import indi.tudan.sqlgenerater.utils.FreemarkerUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 创建表 DDL 生成器
 *
 * @author wangtan
 * @date 2019-09-19 16:40:10
 * @since 1.0
 */
@Data
@Builder
@Slf4j
public class SQLGenerater {

    /**
     * 待解析 Excel 路径
     */
    private String excelPath;

    /**
     * sql 输出路径
     */
    private String resultPath;

    /**
     * 模式：console:打印到控制台；file:生成 sql 文件
     */
    private String mode;

    /**
     * 类型
     */
    private String type;

    /**
     * 模板名称
     */
    private String ftlName;

    /**
     * 执行
     *
     * @date 2019-09-20 15:25:35
     */
    public void exec() {

        if (check()) {
            parseTablesDDL();
        }
    }

    /**
     * 检查
     *
     * @date 2019-09-20 16:08:21
     */
    private boolean check() {
        boolean result;
        Scanner scanner = new Scanner(System.in);

        // 打印配置
        result = printSettings(scanner);

        return result;
    }

    /**
     * 打印配置
     *
     * @param scanner 控制台输入
     * @date 2019-09-21 20:10:24
     */
    private boolean printSettings(Scanner scanner) {

        System.out.println("\n=======================================================================================\n");
        System.out.println(StrUtil.format("根据模板 【 {}.ftl 】 生成 sql", type));
        System.out.println("\n当前配置如下");
        System.out.println(StrUtil.format("待解析文件：{}", excelPath));
        if (!FileUtil.isFile(excelPath)) {
            System.out.println("Excel 文件不存在");
            return false;
        }
        System.out.println(StrUtil.format("输出模式：{}", mode));

        if ("file".equals(mode)) {
            System.out.println(StrUtil.format("输出路径：{}", resultPath));
            if (!FileUtil.isDirectory(resultPath)) {
                System.out.println("输出路径不存在，是否创建？(Y/N)");
                if (yesOrNo(scanner.next())) {
                    FileUtil.mkdir(resultPath);
                    System.out.println("路径创建成功");
                } else {
                    System.out.println("输出路径不存在，正在退出程序 ...");
                    return false;
                }
            }
        }
        System.out.println("\n=======================================================================================\n");

        // 询问是否需要更新配置
        askUpdateSettings(scanner);

        return true;
    }

    /**
     * 更新配置
     *
     * @param scanner 控制台输入
     * @date 2019-09-21 20:06:02
     */
    private void askUpdateSettings(Scanner scanner) {

        System.out.println("是否需要修改配置？(Y/N)");

        if (yesOrNo(scanner.next())) {
            System.out.println("请选择需要修改的配置项序号：");
            System.out.println("1. 待解析 Excel 路径");
            System.out.println(StrUtil.format("2. 模板类型（{}）", getAllTplName()));
            System.out.println("3. 输出模式（console、file）");
            if ("file".equals(mode)) {
                System.out.println("4. 输出路径");
            }

            switch (scanner.next()) {
                case "1":
                    System.out.println("请输入 Excel 路径");
                    setExcelPath(scanner.next());
                    break;
                case "2":
                    System.out.println("请输入模板类型（如：create.ftl，则输入 create）");
                    String type = scanner.next();
                    setType(type);
                    setFtlName(type);
                    break;
                case "3":
                    System.out.println("请输入输出模式");
                    setMode(scanner.next());
                    if ("file".equals(mode)) {
                        System.out.println(StrUtil.format("当前输出路径：{}，是否需要修改？(Y/N)", resultPath));
                        if (yesOrNo(scanner.next())) {
                            System.out.println("请输入输出路径");
                            setResultPath(scanner.next());
                        }
                        if (!FileUtil.isDirectory(resultPath)) {
                            System.out.println("输出路径不存在，是否创建？(Y/N)");
                            if (yesOrNo(scanner.next())) {
                                FileUtil.mkdir(resultPath);
                                System.out.println("路径创建成功");
                            } else {
                                System.out.println("输出路径不存在，请务必指定一个路径，若不指定或者路径错误，则创建当前路径");
                                String tempResultPath = scanner.next();
                                try {
                                    System.out.println(StrUtil.format("路径创建成功：{}", FileUtil.mkdir(tempResultPath).getAbsolutePath()));
                                    setResultPath(tempResultPath);
                                } catch (Exception e) {
                                    log.error("路径创建失败，使用默认路径创建", e);
                                    System.out.println(StrUtil.format("路径创建成功：{}", FileUtil.mkdir(resultPath).getAbsolutePath()));
                                }
                            }
                        }
                    }
                    break;
                case "4":
                    System.out.println("请输入输出路径");
                    setResultPath(scanner.next());
                    break;
                default:
                    System.out.println("没有您要修改的配置项，程序继续执行");
                    break;
            }

            askUpdateSettings(scanner);
        }

    }

    /**
     * 判断是或否
     *
     * @param yesOrNo 字符串
     * @return boolean
     * @date 2019-09-21 18:12:47
     */
    private boolean yesOrNo(String yesOrNo) {
        return "Y".equalsIgnoreCase(yesOrNo);
    }

    /**
     * 获取所有模板名称
     *
     * @return String
     * @date 2019-09-21 18:22:23
     */
    private String getAllTplName() {
        return Arrays.stream(FileUtil.ls(StrUtil.format("{}{}", ClassUtil.getClassPath(), "templates")))
                .map(File::getName).collect(Collectors.joining("、"));
    }

    /**
     * 解析 Excel 所有工作簿
     *
     * @date 2019-09-19 21:47:04
     */
    public void parseTablesDDL() {
        for (int i = 0, len = ExcelUtils.getNumberOfSheets(excelPath); i < len; i++) {
            parseTablesDDL(i);
        }
    }

    /**
     * 解析 Excel 指定工作簿
     *
     * @param sheetIndex 工作簿序号
     * @date 2019-09-19 21:24:22
     */
    public void parseTablesDDL(int sheetIndex) {
        JSONObject tables = SQLReader.getTables(excelPath, sheetIndex);
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
