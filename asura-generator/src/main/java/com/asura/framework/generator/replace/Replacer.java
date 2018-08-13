/**
 * @FileName: Replacer.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/23 11:16
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.replace;

import com.asura.framework.generator.utils.PropertiesUtils;
import com.asura.framework.generator.utils.WriterHelper;

import java.io.File;
import java.util.Arrays;

import static com.asura.framework.generator.replace.Constants.*;
import static com.asura.framework.generator.utils.IOUtils.*;
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
public final class Replacer {
    private static final String templateFolderUrl = getResource(PropertiesUtils.get("template.url", DEFAULT_TEMPLATE_URL));
    private static final String outputFolderUrl = PropertiesUtils.get("output.url", DEFAULT_OUTPUT_URL);
    private static String replaceUrl = getResource(PropertiesUtils.get("replace.url", "replace.txt"));
    private static String fieldsUrl = getResource(PropertiesUtils.get("fields.url", "fields.txt"));

    private Replacer() {}

    public static void go() {
        File templateFolder = newFile(templateFolderUrl);
        if (templateFolder == null || !templateFolder.isDirectory()) {
            throw new RuntimeException("template folder url is not exist or not a directory url[" + templateFolderUrl + "].");
        }

        ReplaceRepository replaceRepository = new ReplaceRepository();
        read(replaceUrl, line -> {
            String[] result;
            if (isEmpty(line)) return;
            if (line.startsWith("#") || (result = line.split("=")).length != 2) return;
            replaceRepository.add(result[0], result[1]);
        });

        FieldsRepository fieldsRepository = new FieldsRepository();
        read(fieldsUrl, line -> {
            String[] result;
            if (isEmpty(line)) return;
            if (line.startsWith("#") || (result = line.split("@")).length != 2) return;
            fieldsRepository.add(result[0], result[1]);
        });

        ReplaceRepository.ReplaceAtom tableName = replaceRepository.get(MUST_REPLACE_ATOM_TABLE_NAME);

        replaceRepository.add(DEFAULT_REPLACE_ATOM_FIELDS_AREA, buildFieldsArea(fieldsRepository));
        replaceRepository.add(DEFAULT_REPLACE_ATOM_FIELDS_SQL_AREA,
                buildFieldSQLArea(fieldsRepository, replaceRepository.get(MUST_REPLACE_ATOM_TABLE_NAME_ACRONYM).getValue()));
        replaceRepository.add(DEFAULT_REPLACE_ATOM_INSERT_SQL_AREA, buildInsertSQLArea(fieldsRepository, tableName));
        replaceRepository.add(DEFAULT_REPLACE_ATOM_UPDATE_SQL_AREA, buildUpdateSQLArea(fieldsRepository));
        replaceRepository.add(DEFAULT_REPLACE_ATOM_RESULT_MAP_AREA, buildResultMapArea(fieldsRepository));

        mkdirs(outputFolderUrl);
        searchAndReplace(templateFolder, replaceRepository);
    }

    private static String buildResultMapArea(FieldsRepository fieldsRepository) {
        StringBuilder sb = new StringBuilder();
        fieldsRepository.getFields().forEach(field ->
                sb.append("\t\t<result column=\"").append(field.getFieldName()).append("\" property=\"")
                        .append(replaceUnderline(field.getFieldName())).append("\" jdbcType=\"")
                        .append(jdbcType(field.getType())).append("\" />\n")
        );
        return sb.substring(0, sb.length() - 1);
    }

    private static String buildUpdateSQLArea(FieldsRepository fieldsRepository) {
        StringBuilder sb = new StringBuilder();
        fieldsRepository.getFields().forEach(field ->
                sb.append("\t\t\t").append(field.getFieldName()).append(" = #{").append(replaceUnderline(field.getFieldName())).append("},\n")
        );
        return sb.substring(0, sb.length() - 2);
    }

    private static String buildInsertSQLArea(FieldsRepository fieldsRepository, ReplaceRepository.ReplaceAtom tableName) {
        StringBuilder sb = new StringBuilder("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        sb.append("\t\t\tcid,\n");
        fieldsRepository.getFields().forEach(field -> {
            if (!"cid".equals(field.getFieldName())) {
                sb.append("\t\t\t<if test=\"").append(replaceUnderline(field.getFieldName())).append(" != null\">\n")
                        .append("\t\t\t\t").append(field.getFieldName()).append(",\n")
                        .append("\t\t\t</if>\n");
            }
        });
        sb.append("\t\t</trim>\n");
        sb.append("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
        sb.append("\t\t\tSEQ_").append(tableName.getValue()).append(".nextval,\n");
        fieldsRepository.getFields().forEach(field -> {
            if (!"cid".equals(field.getFieldName())) {
                sb.append("\t\t\t<if test=\"").append(replaceUnderline(field.getFieldName())).append(" != null\">\n")
                        .append("\t\t\t\t#{").append(replaceUnderline(field.getFieldName())).append("},\n")
                        .append("\t\t\t</if>\n");
            }
        });
        sb.append("\t\t</trim>");
        return sb.toString();
    }

    private static String buildFieldSQLArea(FieldsRepository fieldsRepository, String tableNameAcronym) {
        StringBuilder sb = new StringBuilder("");
        fieldsRepository.getFields().forEach(field ->
                sb.append(tableNameAcronym).append(".").append(field.getFieldName()).append(", ")
        );
        return sb.substring(0, sb.length() - 2);
    }

    private static String buildFieldsArea(FieldsRepository fieldsRepository) {
        String fields = buildFields(fieldsRepository);
        String getters = buildGetters(fieldsRepository);
        String setters = buildSetters(fieldsRepository);
        return fields + "\r\n" + getters + setters;
    }

    private static String buildFields(FieldsRepository fieldsRepository) {
        StringBuilder fields = new StringBuilder();
        fieldsRepository.getFields().forEach(field ->
                fields.append("\tprivate ").append(field.getType()).append(" ")
                        .append(replaceUnderline(field.getFieldName())).append(";\n")
        );
        return fields.toString();
    }

    private static String buildGetters(FieldsRepository fieldsRepository) {
        StringBuilder fields = new StringBuilder();
        fieldsRepository.getFields().forEach(field ->
                fields.append("\tpublic ").append(field.getType()).append(" get").append(initial(field.getFieldName()))
                        .append("() {\n").append("\t\treturn ").append(replaceUnderline(field.getFieldName())).append(";\n")
                        .append("\t}\n\n")
        );
        return fields.toString();
    }

    private static String buildSetters(FieldsRepository fieldsRepository) {
        StringBuilder fields = new StringBuilder();
        fieldsRepository.getFields().forEach(field ->
                fields.append("\tpublic void ").append("set").append(initial(field.getFieldName()))
                        .append("(").append(field.getType()).append(" ").append(replaceUnderline(field.getFieldName())).append(") {\n")
                        .append("\t\tthis.").append(replaceUnderline(field.getFieldName())).append(" = ")
                        .append(replaceUnderline(field.getFieldName())).append(";\n")
                        .append("\t}\n\n")
        );
        return fields.toString();
    }

    /**
     * javaType jdbcType
     *  String   VARCHAR
     *  Integer  INTEGER
     * @param javaType
     * @return
     */
    private static String jdbcType(String javaType) {
        switch (javaType) {
            case "String":
                return "VARCHAR";
            case "Integer":
            case "Long":
                return "INTEGER";
        }
        return "VARCHAR";
    }

    /**
     * update_time => UpdateTime
     */
    private static String initial(String fieldName) {
        if (isEmpty(fieldName)) return "";
        char[] chars = replaceUnderline(fieldName).toCharArray();
        char initial = chars[0];
        chars[0] = upperCase(initial);
        return new String(chars);
    }

    /**
     * c => C; a => A
     */
    private static Character upperCase(char c) {
        if (c >= 97 && c <= 122) {
            c -= 32;
        }
        return c;
    }

    /**
     * update_time => updateTime
     */
    private static String replaceUnderline(String fieldName) {
        if (isEmpty(fieldName)) return "";
        char[] chars = fieldName.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '_') {
                chars[i - count] = upperCase(chars[++i]);
                count++;
            } else {
                chars[i - count] = chars[i];
            }
        }
        return new String(chars).substring(0, chars.length - count);
    }

    private static void searchAndReplace(File folder, ReplaceRepository replaceRepository) {
        if (folder == null || !folder.isDirectory()) {
            return;
        }

        File[] files = folder.listFiles(file ->
                file.isDirectory() || file.getName().endsWith(".template"));

        Arrays.stream(files).forEach(file -> {
            if (file.isDirectory()) {
                if (copyFolder(file))
                    searchAndReplace(file, replaceRepository);
            } else {
                replace(file, replaceRepository);
            }
        });
    }

    private static boolean copyFolder(File folder) {
        String readPath = folder.getAbsolutePath();
        String relativePath = readPath.substring(readPath.lastIndexOf("template" + File.separator) + 8);
        String writePath = outputFolderUrl + relativePath;
        return mkdirs(writePath);
    }

    private static void replace(File template, ReplaceRepository replaceRepository) {
        if (template == null || !template.getName().endsWith(".template")) {
            return;
        }
        String readPath = template.getAbsolutePath();
        String relativePath = readPath.substring(readPath.lastIndexOf("template" + File.separator) + 8);
        String writePath = outputFolderUrl + replace(relativePath, replaceRepository).replace(".template", "");

        readAndFlush(readPath, writePath, new WriterHelper() {
            @Override
            public String process(String line) {
                return replace(line, replaceRepository);
            }
        });
    }

    private static String replace(String text, ReplaceRepository replaceRepository) {
        if (!isEmpty(text)) {
            for (ReplaceRepository.ReplaceAtom replaceAtom : replaceRepository.getReplaceAtoms()) {
                text = text.replace(replaceAtom.getTarget(), replaceAtom.getValue());
            }
        }
        return text;
    }
}
