/**
 * @FileName: People.java
 * @Package: com.asura.test
 * @author Wangshanshan
 * @created 2017/6/19 11:58
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author Wangshanshan
 * @since 1.0
 * @version 1.0
 */
public class People {
    public static interface FirstGroup {}

    @NotBlank(message = "{name.null}")
    private String name;

    @NotBlank(message = "{sex.null}")
    private String sex;

    @Min(value = 5, message = "{age.valid}")
    private int age;

    @NotBlank(message = "{school.null}", groups = {FirstGroup.class})
    private String school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", school='" + school + '\'' +
                '}';
    }
}