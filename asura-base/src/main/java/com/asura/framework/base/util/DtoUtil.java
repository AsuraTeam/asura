package com.asura.framework.base.util;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataTransferObject工具类，用于简化DataTransferObject的相关开发
 *
 * Created by knight on 2017/4/23.
 */
public class DtoUtil {
    // 表示数据是一个对象
    private static final String DATA = "data";

    // 表示数据是一个List
    private static final String LIST = "list";

    // 表示数据是一个分页查询结果
    private static final String PAGING_RESULT = "pagingResult";

    // 构建失败的DataTransferObject
    private static DataTransferObject buildError(int code, String message) {
        DataTransferObject dto = new DataTransferObject(code, message, null);
        return dto;
    }

    // 构建成功的DataTransferObject
    private static DataTransferObject buildTrue(int code) {
        Map<String, Object> values = new HashMap<>();
        DataTransferObject dto = new DataTransferObject(code, null, values);
        return dto;
    }

    /**
     * 构建成功的DataTransferObject，且数据是一个对象
     *
     * @param object        数据对象
     * @param <T>           对象的类型
     * @return              DataTransferObject对象
     */
    public static <T> DataTransferObject buildSuccesObject(T object) {
        DataTransferObject dto = buildTrue(DataTransferObject.BUS_SUCCESS);
        dto.putValue(DATA, object);
        return dto;
    }

    /**
     * 构建成功的DataTransferObject，且数据是一个List
     *
     * @param list          数据List
     * @param <T>           List中元素的类型
     * @return              DataTransferObject对象
     */
    public static <T> DataTransferObject buildSuccessList(List<T> list) {
        DataTransferObject dto = buildTrue(DataTransferObject.BUS_SUCCESS);
        dto.putValue(LIST, list);
        return dto;
    }

    /**
     * 构建成功的DataTransferObject，且数据是一个分页查询结果
     *
     * @param pagingResult          分页查询结果
     * @param <T>                   PagingResult中的数据类型
     * @return                      DataTransferObject对象
     */
    public static <T> DataTransferObject buildSuccessPage(PagingResult<T> pagingResult) {
        DataTransferObject dto = buildTrue(DataTransferObject.BUS_SUCCESS);
        dto.putValue(PAGING_RESULT, pagingResult);
        return dto;
    }

    /**
     * 构建失败的DataTransferObject
     *
     * @param code          消息编码
     * @param message       错误信息
     * @return              DataTransferObject对象
     */
    public static DataTransferObject buildFailure(int code, String message) {
        DataTransferObject dto = buildError(code, message);
        return dto;
    }

    /**
     * 构建失败的DataTransferObject，且使用默认的错误编码
     *
     * @param message       错误信息
     * @return              DataTransferObject对象
     */
    public static DataTransferObject buildFailure(String message) {
        DataTransferObject dto = buildError(DataTransferObject.SYS_ERROR, message);
        return dto;
    }

    /**
     * 构建失败的DataTransferObject，且使用指定的错误编码，并使用MessageSource获取错误消息
     *
     * @param code              错误编码
     * @param messageSource     MessageSource对象
     * @param messageCode       消息编码
     * @return                  DataTransferObject对象
     */
    public static DataTransferObject buildFailure(int code, MessageSource messageSource, String messageCode) {
        String message = MessageSourceUtil.getChinese(messageSource, messageCode);
        return buildFailure(code, message);
    }

    /**
     * 从DataTransferObject的Json字符串中解析并获取其中的Object数据
     *
     * @param response          DataTransferObject的Json字符串
     * @param clazz             数据的类型的Class
     * @param <T>               数据类型
     * @return                  数据对象
     */
    public static <T> T parseObject(String response, Class<T> clazz) {
        T t = DataTransferObjectJsonParser.getValueFromDataByKey(response, DATA, clazz);
        return t;
    }

    /**
     *  从DataTransferObject的Json字符串中解析并获取其中的List数据
     *
     * @param response          DataTransferObject的Json字符串
     * @param clazz             List中元素的类型Class
     * @param <T>               List中元素的类型
     * @return                  数据List
     */
    public static <T> List<T> parseList(String response, Class<T> clazz) {
        List<T> list = DataTransferObjectJsonParser.getListValueFromDataByKey(response, LIST, clazz);
        return list;
    }

    /**
     * 从DataTransferObject的Json字符串中解析并获取其中的分页数据
     *
     * @param response          DataTransferObject的Json字符串
     * @param clazz             PagingResult中的数据类型的Class
     * @param <T>               PagingResult中的数据类型
     * @return                  分页查询数据
     */
    public static <T> PagingResult<T> parsePage(String response, Class<T> clazz) {
        TypeReference<PagingResult<T>> typeReference = new TypeReference<PagingResult<T>>() {};
        PagingResult<T> pagingResult = DataTransferObjectJsonParser.getValueFromDataByKey(response, PAGING_RESULT, typeReference);
        return pagingResult;
    }

    /**
     * 判断消息是否成功
     *      true:   代表消息成功
     *      false:  代表消息失败
     *
     * @param reponse       DataTransferObject的Json字符串
     * @return              消息的成功或失败
     */
    public static boolean isSuccess(String reponse) {
        return DataTransferObjectJsonParser.checkSOAReturnSuccess(reponse);
    }

    /**
     *  判断消息是否失败
     *      true:           代表消息失败
     *      false:          代表消息成功
     *
     * @param response      DataTransferObject的Json字符串
     * @return              消息的成功或失败
     */
    public static boolean isFailure(String response) {
        return !isSuccess(response);
    }

    /**
     * 从DataTranferObject中获取message
     *
     * @param response      DataTransferObject的Json字符串
     * @return              message
     */
    public static String getMessage(String response) {
        return DataTransferObjectJsonParser.getReturnMsg(response);
    }

    /**
     * 从DataTransferObject中获取code
     *
     * @param response      DataTransferObject的Json字符串
     * @return              code
     */
    public static int getCode(String response) {
        return DataTransferObjectJsonParser.getReturnCode(response);
    }
}
