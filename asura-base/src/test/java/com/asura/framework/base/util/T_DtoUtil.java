package com.asura.framework.base.util;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by knight on 2017/4/23.
 */

public class T_DtoUtil {
    @Test
    public void t_buildSuccesObject_01() {
        People people = new People();
        people.setName("wss");
        people.setSex("male");
        people.setAge(20);

        String response = DtoUtil.buildSuccesObject(people).toJsonString();

        Assert.assertTrue(DtoUtil.isSuccess(response));

        People ans = DtoUtil.parseObject(response, People.class);
        Assert.assertTrue(people.equals(ans));
    }

    @Test
    public void t_buildSuccessList_01() {
        People people1 = new People();
        people1.setName("wss");
        people1.setSex("female");
        people1.setAge(200);

        People people2 = new People();
        people2.setName("knight");
        people2.setSex("male");
        people2.setAge(10);

        People people3 = new People();
        people3.setName("sss");
        people3.setSex("female");
        people3.setAge(2);

        List<People> peoples = Arrays.asList(people1, people2, people3);

        String response = DtoUtil.buildSuccessList(peoples).toJsonString();

        Assert.assertTrue(DtoUtil.isSuccess(response));

        List<People> ansPeoples = DtoUtil.parseList(response, People.class);
        Assert.assertEquals(peoples.size(), ansPeoples.size());
    }

    @Test
    public void t_buildSuccessPage_01() {
        People people1 = new People();
        people1.setName("wss");
        people1.setSex("female");
        people1.setAge(200);

        People people2 = new People();
        people2.setName("knight");
        people2.setSex("male");
        people2.setAge(10);

        People people3 = new People();
        people3.setName("sss");
        people3.setSex("female");
        people3.setAge(2);

        List<People> peoples = Arrays.asList(people1, people2, people3);

        PagingResult<People> pagingResult = new PagingResult<>();
        pagingResult.setRows(peoples);
        pagingResult.setTotal(100);

        String response = DtoUtil.buildSuccessPage(pagingResult).toJsonString();

        Assert.assertTrue(DtoUtil.isSuccess(response));

        PagingResult<People> ansPagingResult = DtoUtil.parsePage(response, People.class);
        Assert.assertEquals(pagingResult.getTotal(), ansPagingResult.getTotal());
    }

    @Test
    public void t_buildFailure_01() {
        String resp = DtoUtil.buildFailure(DataTransferObject.SYS_ERROR, "测试错误").toJsonString();
        Assert.assertTrue(DtoUtil.isFailure(resp));
    }

    @Test
    public void t_buildFailure_02() {
        String resp = DtoUtil.buildFailure("测试错误2").toJsonString();
        Assert.assertTrue(DtoUtil.isFailure(resp));
    }

    @Test
    public void t_getMessage_01() {
        String message = "测试错误";
        String resp = DtoUtil.buildFailure(DataTransferObject.SYS_ERROR, message).toJsonString();
        Assert.assertEquals(message, DtoUtil.getMessage(resp));
    }

    @Test
    public void t_getCode_01() {
        int code = DataTransferObject.SYS_ERROR;
        String resp = DtoUtil.buildFailure(code, "sdfsdf").toJsonString();
        Assert.assertEquals(code, DtoUtil.getCode(resp));
    }
}
