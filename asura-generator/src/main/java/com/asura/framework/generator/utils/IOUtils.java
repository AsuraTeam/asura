/**
 * @FileName: IOUtils.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/22 16:59
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.utils;

import java.io.*;
import java.net.URL;

import static com.asura.framework.generator.utils.Utils.isEmpty;

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
public final class IOUtils {

    private IOUtils() {}

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("stream close exception.", e);
        }
    }

    public static File newFile(String path) {
        return !isEmpty(path) ? new File(path) : null;
    }

    public static InputStream getResourceAsStream(String relativePath) {
        return IOUtils.class.getClassLoader().getResourceAsStream(relativePath);
    }

    public static String getResource(String relativePath) {
        URL url = IOUtils.class.getClassLoader().getResource(relativePath);
        return url == null ? "" : url.getPath();
    }

    public static void readAndFlush(String readPath, String writePath, WriterHelper helper) {
        readAndWrite(readPath, writePath, true, helper);
    }

    public static boolean mkdirs(String path) {
        File newFile = new File(path);
        return newFile.exists() || newFile.mkdirs();
    }

    public static void readAndAdd(String readPath, String writePath, WriterHelper helper) {
        readAndWrite(readPath, writePath, false, helper);
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

    public static String read(String path) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("read file exception path[" + path + "].", e);
        } finally {
            close(reader);
        }
    }

    public static void write(String path, String content) {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader(content));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"), true);
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("read file exception path[" + path + "].", e);
        } finally {
            close(reader);
            close(writer);
        }
    }

    private static void readAndWrite(String readPath, String writePath, boolean isFlush, WriterHelper helper) {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(readPath), "UTF-8"));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(writePath), "UTF-8"), isFlush);
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(helper.process(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("read and write exception read path[" + readPath + "] write path[" + writePath + "].", e);
        } finally {
            close(reader);
            close(writer);
        }
    }
}