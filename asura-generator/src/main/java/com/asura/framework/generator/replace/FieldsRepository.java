/**
 * @FileName: FieldsRepository.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/27 10:59
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.replace;

import java.util.ArrayList;
import java.util.List;

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
public class FieldsRepository {

    private List<Field> fields;

    public FieldsRepository() {
        fields = new ArrayList<>();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void add(String fieldName, String type) {
        fields.add(new Field(fieldName, type));
    }

    public void add(Field field) {
        fields.add(field);
    }

    class Field {
        private String fieldName;
        private String type;

        Field(String fieldName, String type) {
            this.fieldName = fieldName;
            this.type = type;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}