package indi.tudan.sqlgenerator.utils;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

/**
 * 模板工具类
 *
 * @author wangtan
 * @date 2019-09-19 16:48:58
 * @since 1.0
 */
@Slf4j
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
    public static String parseFTL(String ftlName, JSONObject params) {
        Configuration cfg = SpringBeanUtils.getBean(Configuration.class);
        String content = null;
        try {
            Template t = cfg.getTemplate(ftlName + ".ftl");
            content = FreeMarkerTemplateUtils.processTemplateIntoString(t, params);
        } catch (IOException | TemplateException e) {
            log.error("模板解析失败", e);
        }
        return content;
    }
}
