/**
 * @FileName: SOAResParseUtil.java
 * @Package: com.ziroom.cleaning.util
 * @author sence
 * @created 9/14/2015 4:18 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>SOA接口返回数据解析工具类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @Deprecated in favor of DataTransferObjectJsonParser
 * @since 1.0
 */
@Deprecated
public class SOAResParseUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(SOAResParseUtil.class);

    /**
     * 私有化构造，工具类
     */
    private SOAResParseUtil() {

    }

    /**
     * 获取返回的JSON对象
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午5:59:58
     */
    public static JSONObject getJsonObj(final String result) {
        try {
            return JSON.parseObject(result);
        } catch (final JSONException e) {
            LOGGER.error("解析SOA返回JSON结果错误！", e);
            return null;
        }
    }

    /**
     * 获取返回对象中的data对象，需要SOA响应状态码是0
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月9日 上午11:22:17
     */
    public static JSONObject getDataObj(final String result) {
        final JSONObject obj = getJsonObj(result);
        JSONObject data = null;
        if (null != obj
                && (DataTransferObject.BUS_SUCCESS == obj.getIntValue("code") || DataTransferObject.SUCCESS == obj.getIntValue("code"))
                && obj.containsKey("data")) {
            data = obj.getJSONObject("data");
        }
        return data;
    }

    /**
     * 根据key获取返回对象data中的字符串值(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static String getStrFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getStrFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Integer getIntFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getIntFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的长整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Long getLongFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getLongFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的float(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Float getFloatFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getFloatFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }


    /**
     * 根据key获取返回对象data中的double(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Double getDoubleFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getDoubleFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的Boolean值(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Boolean getBooleanFromDataByKey(final String result, final String key) {
        try {
            return DataTransferObjectJsonParser.getBooleanFromDataByKey(result, key);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的JSON对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static JSONObject getJsonObjFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getJSONObject(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的Array对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static JSONArray getArrayFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getJSONArray(key);
        } else {
            return null;
        }
    }

    /**
     * 获取返回JSON对象的响应状态码
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:01:09
     */
    public static int getReturnCode(final String result) {
        try {
            return DataTransferObjectJsonParser.getReturnCode(result);
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return -1;
        }
    }

    /**
     * 从SOA内直接取得对应的class
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:58:18
     */
    public static <T> T getValueFromDataByKey(final String result, final String key, Class<T> clazz) throws SOAParseException {
        try {
            return DataTransferObjectJsonParser.getValueFromDataByKey(result, key, clazz);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 从SOA内直接取得对应的class list
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午10:19:52
     */
    public static <T> List<T> getListValueFromDataByKey(final String result, final String key, Class<T> clazz) throws SOAParseException {
        try {
            return DataTransferObjectJsonParser.getListValueFromDataByKey(result, key, clazz);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }

    /**
     * 判断SOA返回结果是否符合期望结果
     *
     * @param result
     * @param expectCode
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:48:45
     */
    public static boolean checkSOAReturnExpect(String result, Integer expectCode) {
        int returnCode = getReturnCode(result);
        return returnCode == expectCode;
    }

    /**
     * 判断SOA返回结果是否符合期望结果,使用默认期望code 0 兼容新老
     *
     * @param result
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:50:06
     */
    public static boolean checkSOAReturnSuccess(String result) {
        return checkSOAReturnExpect(result, DataTransferObject.BUS_SUCCESS)
                || checkSOAReturnExpect(result, DataTransferObject.SUCCESS);
    }

    /**
     * 获取返回JSON对象的响应信息
     *
     * @param result
     * @created 2014年5月7日 下午6:01:09
     */
    public static String getReturnMsg(String result) {
        try {
            return DataTransferObjectJsonParser.getReturnMsg(result);
        } catch (BusinessException e) {
            LOGGER.warn("WARN:", e);
            return null;
        } catch (SystemException e) {
            LOGGER.error("ERROR:", e);
            return null;
        }
    }
}
