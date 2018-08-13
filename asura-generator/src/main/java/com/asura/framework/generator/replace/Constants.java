/**
 * @FileName: Constants.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/22 16:53
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.replace;

import javax.swing.filechooser.FileSystemView;
import java.text.SimpleDateFormat;

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
public class Constants {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");

    public static final String DEFAULT_REPLACE_ATOM_DATE_TIME = "{dateTime}";
    public static final String DEFAULT_REPLACE_ATOM_YEAR = "{year}";
    public static final String DEFAULT_REPLACE_ATOM_DATE = "{date}";
    public static final String DEFAULT_REPLACE_ATOM_FIELDS_AREA = "{fieldsArea}";
    public static final String DEFAULT_REPLACE_ATOM_FIELDS_SQL_AREA = "{fieldsSQLArea}";
    public static final String DEFAULT_REPLACE_ATOM_INSERT_SQL_AREA = "{insertSQLArea}";
    public static final String DEFAULT_REPLACE_ATOM_UPDATE_SQL_AREA = "{updateSQLArea}";
    public static final String DEFAULT_REPLACE_ATOM_RESULT_MAP_AREA = "{resultMapArea}";

    public static final String MUST_REPLACE_ATOM_TABLE_NAME = "{tableName}";
    public static final String MUST_REPLACE_ATOM_TABLE_NAME_ACRONYM = "{tableNameAcronym}";
    public static final String MUST_REPLACE_ATOM_TABLE_LITTLE_NAME = "{tableLittleName}";
    public static final String MUST_REPLACE_ATOM_AUTHOR = "{author}";
    public static final String MUST_REPLACE_ATOM_MODULE_NAME = "{moduleName}";
    public static final String MUST_REPLACE_ATOM_STANDARD_NAME = "{standardName}";

    public static final String DEFAULT_TEMPLATE_URL = "template/";
    public static final String DEFAULT_OUTPUT_URL =
            FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "output/";
}