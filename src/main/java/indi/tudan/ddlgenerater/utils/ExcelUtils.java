package indi.tudan.ddlgenerater.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

/**
 * Excel 工具类
 *
 * @author wangtan
 * @date 2019-09-19 21:24:11
 * @since 1.0
 */
@Slf4j
public class ExcelUtils {

    public static int getNumberOfSheets(String path) {

        int numberOfSheets = 0;
        switch (FileUtil.extName(path)) {
            case "xls":
                try (FileInputStream finput = new FileInputStream(path);
                     HSSFWorkbook hs = new HSSFWorkbook(finput)) {
                    numberOfSheets = hs.getNumberOfSheets();
                } catch (Exception e) {
                    log.error("获取 Excel 2003 工作簿数量失败", e);
                }
                break;
            case "xlsx":
                try (XSSFWorkbook xs = new XSSFWorkbook(path)) {
                    numberOfSheets = xs.getNumberOfSheets();
                } catch (Exception e) {
                    log.error("获取 Excel 2007 工作簿数量失败", e);
                }
                break;
            default:
                break;
        }

        return numberOfSheets;
    }
}
