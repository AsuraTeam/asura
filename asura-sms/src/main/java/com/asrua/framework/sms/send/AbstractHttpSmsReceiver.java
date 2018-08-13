/**
 * @FileName: AbstractHttpSmsReceiver.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 12/3/2015 11:36 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asrua.framework.sms.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asrua.framework.sms.conf.AbstractSmsSenderConfig;
import com.asrua.framework.sms.entity.SmsReceiveMessage;
import com.asrua.framework.sms.log.ISmsLogger;
import com.asrua.framework.sms.log.LogInfoBean;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractHttpSmsReceiver implements ISmsReceiver {


    private Logger LOGGER = LoggerFactory.getLogger(AbstractHttpSmsReceiver.class);
    /**
     * 配置
     */
    private AbstractSmsSenderConfig config;


    private ISmsLogger logger;

    public AbstractHttpSmsReceiver(AbstractSmsSenderConfig config) {
        this.config = config;
        this.logger = config.getLogger();
    }


    @Override
    public DataTransferObject receive() throws IOException {
        CloseableHttpClient httpClient = null;
        DataTransferObject dto = new DataTransferObject();
        CloseableHttpResponse response = null;
        LogInfoBean logInfoBean = new LogInfoBean();
        try {
            /**
             * HTTP connections are complex, stateful, thread-unsafe objects which need to be properly managed to function correctly.
             * HTTP connections can only be used by one execution thread at a time
             */
            httpClient = HttpClients.createDefault();
            logInfoBean.setUrl(config.getSendUrl());
            //根据请求设置URL
            HttpPost httpPost = buildPostParam();
            response = httpClient.execute(httpPost);
            String result = HttpUtil.handlerRespone(response, logInfoBean);
            logger.saveLog(logInfoBean);
            //解析JSON串
            dto = parseReceive(result);
            return dto;
        } catch (Exception e) {
            dto.setErrCode(1);
            dto.setMsg("发送短信失败:" + e.getMessage());
            LogUtil.error(LOGGER, "send error:{}", e);
            logInfoBean.setReturnInfo(e.getMessage());
            logger.saveLog(logInfoBean);
            return dto;
        } finally {
            if (!Check.NuNObj(httpClient)) {
                httpClient.close();
            }
            if (!Check.NuNObj(response)) {
                response.close();
            }
        }
    }

    /**
     * 创建URI
     *
     * @return
     */
    public URI buildURIByConfig() throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setHost(config.getHost());
        builder.setPort(config.getPort());
        builder.setPath(config.getPath());
        builder.setScheme(config.getProtocol());
        builder.setCharset(Charset.forName(config.getCharset()));
        return builder.build();
    }


    protected DataTransferObject parseReceive(String jsonResult) {
        DataTransferObject dto = new DataTransferObject();
        //判断为空
        if (Check.NuNStr(jsonResult)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.putValue("data", "未取得返回数据");
            return dto;
        }
        //转成map
        JSONObject jsonObject = JSON.parseObject(jsonResult);
        String code = jsonObject.getString("response_code");
        //如果服务器返回异常
        if (!"0".equals(code)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.putValue("data", jsonObject.get("error_info"));
            return dto;
        }
        //返回正确，将数据取回到dto
        String uplinks =  jsonObject.getString("uplinks");
        List<SmsReceiveMessage> receiveMessages = JSON.parseArray(uplinks,SmsReceiveMessage.class);
        dto.putValue("data", receiveMessages);
        return dto;
    }

    protected abstract HttpPost buildPostParam() throws UnsupportedEncodingException, URISyntaxException;

    public AbstractSmsSenderConfig getConfig() {
        return config;
    }

    public void setConfig(AbstractSmsSenderConfig config) {
        this.config = config;
    }

    public ISmsLogger getLogger() {
        return logger;
    }

    public void setLogger(ISmsLogger logger) {
        this.logger = logger;
    }
}
