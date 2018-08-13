/**
 * @FileName: T_AsuraCommonsHttpclient.java
 * @Package: com.asura.framework.commons.net
 * @author liusq23
 * @created 2016/11/30 下午3:33
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.net;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class T_AsuraCommonsHttpclient {

    /**
     *
     */
    @Test
    public void t_doGet_01() throws IOException {
        String url = "http://www.baidu.com";
        String s = AsuraCommonsHttpclient.getInstance().doGet(url);
        Assert.assertThat(s, CoreMatchers.containsString("百度一下"));
    }


    /**
     *
     */
    @Test
    public void t_doGet_02() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo?name=sence&command=CODE";
        String s = AsuraCommonsHttpclient.getInstance().doGet(url);
        Assert.assertThat(s, CoreMatchers.containsString("出现了自定义code业务异常"));
    }

    /**
     *
     */
    @Test
    public void t_doPost_01() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo";
        String s = AsuraCommonsHttpclient.getInstance().doPostForm(url, new HashMap<String, String>());
        Assert.assertThat(s, CoreMatchers.containsString("名称不能为空"));
    }

    /**
     *
     */
    @Test
    public void t_doPost_02() throws IOException {
        String url = "http://10.30.27.16:8083/exdemo";
        Map<String, String> map = new HashMap<>();
        map.put("name", "sence");
        map.put("command", "CODE");
        String s = AsuraCommonsHttpclient.getInstance().doPostForm(url, map);
        Assert.assertThat(s, CoreMatchers.containsString("出现了自定义code业务异常"));
    }


    @Test
    public void t_doPostJson_01() throws IOException {
        String url = "http://api.push.t.ziroom.com/push";
        String json = "{\"token\":\"1d853e39bea247b4b9416220ec9633a5\",\"content\":\"!!!恭喜！房客姜筱已完成支付，成功预订了您的460210549252Z房 源，订单编号：161104023F0ZFL124248。Ta将于2016-11-11入住，2016-11-14退房，请及时与房客沟通入住指南、交通路线、门锁状态等信息。房 款将按照您设置的结算方式到账。\",\"alias\":[\"f3ef4fb05f690377fb234b3793725a51\",\"a6daac461dda1c1f15bb7290b4219bb2\"],\"extras\":{\"push_time\":\"1478251962337\",\"msg_sub_type\":\"1\",\"url\":\"http://bnb.m.ziroom.com/orderland/43e881/showDetail?requestType=2&orderSn=161104023F0ZFL124248&landlordUid=287a2bdc-8aae-467d-a77c-7f07e51aef6e\",\"msg_tag_type\":\"1\",\"msg_has_response\":\"1\",\"msg_body_type\":\"minsu_notify\"},\"platform\":[\"android\",\"ios\"],\"title\":\"亲，您有新的消息\"}";
        String result = AsuraCommonsHttpclient.getInstance().doPostJson(url, json);
        Assert.assertThat(result, CoreMatchers.containsString("已发送"));
    }

    @Test
    public void t_doGetWithParams_01() throws IOException {
        String employeeId = "ff808081343d9ec901344085d3a604ea";
        String url = "http://zshmsa.t.ziroom.com/v1/house_have_smartlock?op_userid=" + employeeId;

        String employeeName = "王素巧";
        String employeeMobile = "13488863395";
        String houseCode = "5015003933233";

        final String OP_USER_ID = "op_userid";
        final String OP_NAME = "op_name";
        final String OP_PHONE = "op_phone";
        final String HOUSE_ID = "house_id";
        final String ROOM_ID = "room_id";

        Map<String, Object> params = new HashMap<>();
        params.put(OP_NAME, employeeName);
        params.put(OP_PHONE, employeeMobile);
        params.put(HOUSE_ID, houseCode);
        // 房间编号(双周保洁不需要房间号)
        params.put(ROOM_ID, null);
        String resp = AsuraCommonsHttpclient.getInstance().doGetWithParams(url, params);
        Assert.assertNotNull(resp);
    }
}
