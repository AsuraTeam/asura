/**
 * @FileName: PropertiesUtils.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/22 17:22
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.utils;

import java.util.Properties;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @since 1.0
 * @version 1.0
 */
public final class PropertiesUtils {
    private static Properties properties;
    private static final String configUrl = "config.properties";

    static {
        reload();
    }

    private PropertiesUtils() {}

    public synchronized static void reload() {
        try {
            if (properties == null) {
                properties = new Properties();
            }
            properties.clear();
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(configUrl));
        } catch (Exception e) {
            throw new RuntimeException("reload config.properties error.", e);
        }
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}