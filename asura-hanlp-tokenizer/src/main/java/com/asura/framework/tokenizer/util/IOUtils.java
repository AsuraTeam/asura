/**
 * @FileName: IOUtils.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/22 16:59
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.util;

import java.io.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @version 1.0
 * @since 1.0
 */
public final class IOUtils {

    private IOUtils() {
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("stream close exception.", e);
        }
    }

    public static void read(String path, ReaderHelper helper) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                helper.process(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("read file exception path[" + path + "].", e);
        } finally {
            close(reader);
        }
    }
}