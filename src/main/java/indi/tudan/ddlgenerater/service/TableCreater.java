package indi.tudan.ddlgenerater.service;

import indi.tudan.ddlgenerater.utils.FreemarkerUtil;

/**
 * 创建表 DDL 生成器
 *
 * @author wangtan
 * @date 2019-09-19 16:40:10
 * @since 1.0
 */
public class TableCreater {

    public static String createTable() {
        return FreemarkerUtil.parseTpl("create", null);
    }
}
