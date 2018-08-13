/**
 * @FileName: ParamRequest.java
 * @Package: com.asura.test
 * @author lig134
 * @created 2017/6/23 下午2:31
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lig134
 * @version 1.0
 * @since 1.0
 */
public class ParamRequest {
    private String id;
    private String name;
    private String code;

    public void set(String id, String name, String code) {
        this.id = id;
        this.name=name;
        this.code=code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
}