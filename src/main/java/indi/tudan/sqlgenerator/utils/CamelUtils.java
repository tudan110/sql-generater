package indi.tudan.sqlgenerator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰与下划线互转
 *
 * @author wangtan
 * @date 2019-09-24 14:42:29
 * @since 1.0
 */
public class CamelUtils {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param 驼峰格式字符串
     * @return String
     * @date 2019-09-24 14:38:39
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param 下划线格式字符串
     * @return String
     * @date 2019-09-24 14:47:45
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串（开头大写）
     *
     * @param param 下划线格式字符串
     * @return String
     * @date 2019-09-25 17:39:50
     */
    public static String underlineToUpperCamel(String param) {
        return StringUtils.capitalStr(underlineToCamel(param));
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串2
     *
     * @param param 划线格式字符串
     * @return String
     * @date 2019-09-24 14:59:04
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String aaa = "app_version_fld";
        long start = System.currentTimeMillis();
        System.out.println(underlineToCamel(aaa));
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        System.out.println(underlineToCamel2(aaa));
        System.out.println(System.currentTimeMillis() - start);
        aaa = "appVersionFld";
        System.out.println(camelToUnderline(aaa));

        System.out.println(StringUtils.capitalStr(underlineToCamel(aaa)));

        System.out.println(underlineToCamel("cnet_fee_with_p2p_d_sum"));
    }

}
