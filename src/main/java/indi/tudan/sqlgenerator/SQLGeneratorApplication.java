package indi.tudan.sqlgenerator;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import indi.tudan.sqlgenerator.service.SQLGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

@SpringBootApplication
public class SQLGeneratorApplication {

    public static void main(String... args) {

        SpringApplication.run(SQLGeneratorApplication.class, args);

        // 程序开始执行
        init(args);
    }

    /**
     * --d=mysql
     * --t=create
     * --m=console
     * --p="C:/Users/tudan/Desktop/表结构设计.xlsx"
     * --r="C:/Users/tudan/Desktop/ddl"
     *
     * @param args 初始化参数
     * @date 2019-09-21 16:49:59
     */
    private static void init(String... args) {

        // 默认数据库方言或模板组
        String dialect = "mysql";

        // 默认待解析 Excel 路径
        String excelPath = StrUtil.format("{}{}/{}",
                ClassUtil.getClassPath(), "static", "表结构设计.xlsx");

        //当前用户桌面
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        // 默认 sql 输出路径
        String resultPath = StrUtil.format("{}/sql", desktopDir.getAbsolutePath());

        // 默认模式：console:打印到控制台；file:生成 sql 文件
        String mode = "console";

        // 默认类型
        String type = "create";

        if (ObjectUtil.isNotEmpty(args) || args.length != 0) {
            for (String arg : args) {
                int equalIndex = arg.indexOf("=");
                switch (arg.substring(0, equalIndex)) {

                    // 数据库方言或模板组
                    case "--dialect":
                    case "--dl":
                    case "--dia":
                    case "--d":
                        dialect = arg.substring(equalIndex + 1);
                        break;

                    // 模板类型
                    case "--type":
                    case "--t":
                        type = arg.substring(equalIndex + 1);
                        break;
                    default:
                        break;

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
                }
            }
        }

        // 开始解析
        SQLGenerator.builder()
                .dialect(dialect)
                .ftlName(type)
                .excelPath(excelPath)
                .resultPath(resultPath)
                .mode(mode)
                .type(type)
                .build()
                .exec();
    }

}
