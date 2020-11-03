package com.haiot.springcloud.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper {
    /**
     * String.format渲染模板
     * @param template 模版 | java无法识别params对应实参的name，所以params还是要按顺序传入
     * @param params   参数
     * @return
     */
    public static String Format(String template, Object... params) {
        if (template == null || params == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        int i =0;
        while (m.find()) {
            String param = m.group();
            Object value = params[i];
            m.appendReplacement(sb, value == null ? "" : value.toString());
            i++;
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 自定义渲染模板
     * @param template 模版 | hashmap可以无序
     * @param params   参数
     * @return
     */
    public static String Format(String template, Map<String, Object> params) {
        if (template == null || params == null)
            return null;
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
