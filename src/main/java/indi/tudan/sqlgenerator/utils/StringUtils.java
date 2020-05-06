package indi.tudan.sqlgenerator.utils;

/**
 * String 工具类
 *
 * @author wangtan
 * @date 2019-08-30 13:54:17
 * @since 1.0
 */
public class StringUtils {

    /**
     * Don't let anyone else instantiate this class
     */
    private StringUtils() {
    }

    /**
     * 字符串是否为空
     *
     * @param str 字符串
     * @return boolean
     * @author wangtan
     * @date 2019-08-30 13:59:35
     * @since 1.0
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 字符串是否不为空
     *
     * @param str 字符串
     * @return boolean
     * @author wangtan
     * @date 2019-08-30 13:59:45
     * @since 1.0
     */
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 若字符串为空，则返回 defaultString
     *
     * @param string        字符串
     * @param defaultString 默认字符串
     * @return String
     * @author wangtan
     * @date 2019-09-06 13:53:42
     * @since 1.0
     */
    public static String orElse(String string, String defaultString) {
        return isEmpty(string) ? defaultString : string;
    }

    /**
     * 获取字符串
     *
     * @param object 对象数据
     * @return String
     * @date 2019-09-19 16:53:30
     */
    public static String getStr(Object object) {
        if (isNotEmpty(object)) {
            return (String) object;
        } else {
            return "";
        }
    }

    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return String
     * @date 2019-09-24 14:49:11
     */
    public static String capitalStr(String str) {

        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }

    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }
}