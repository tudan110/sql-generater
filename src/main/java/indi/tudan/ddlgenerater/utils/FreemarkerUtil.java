package indi.tudan.ddlgenerater.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import indi.tudan.ddlgenerater.context.SpringContextHolder;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 模板工具类
 *
 * @author wangtan
 * @date 2019-09-19 16:48:58
 * @since 1.0
 */
public class FreemarkerUtil {

    /**
     * Don't let anyone else instantiate this class
     */
    private FreemarkerUtil() {
    }

    /**
     * @param ftlName 模板名称
     * @param params  数据
     * @return String
     * @date 2019-09-19 16:50:55
     */
    public static String parseTpl(String ftlName, Map<String, Object> params) {
        Configuration cfg = SpringContextHolder.getBean(Configuration.class);
        String html = null;
        try {
            Template t = cfg.getTemplate(ftlName + ".ftl");
            html = FreeMarkerTemplateUtils.processTemplateIntoString(t, params);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return html;
    }
}
