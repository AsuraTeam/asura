/**
 * @FileName: ZiroomSmsSender.java
 * @Package: com.asrua.framework.sms.log
 * @author sence
 * @created 10/10/2015 3:16 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asrua.framework.sms.send;

import com.asrua.framework.sms.conf.TokenSmsSenderConfig;
import com.asura.framework.base.util.JsonEntityTransform;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>token短信网关通道</p>
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
public class TokenSmsSender extends AbstractHttpSmsSender {


    public TokenSmsSender(TokenSmsSenderConfig config) {
        super(config);
    }

    /**
     * post 参数
     * @param smsMessage
     */
    @Override
    protected HttpPost buildPostParam(SmsMessage smsMessage) throws UnsupportedEncodingException, URISyntaxException {
        URI uri =  super.buildURIByConfig();
        HttpPost httpPost = new HttpPost(uri);
        TokenSmsSenderConfig config = (TokenSmsSenderConfig)getConfig();
        HashMap<String,String> map = new HashMap<>();
        map.put("token", config.getToken());
        map.put("to", smsMessage.getToPhone());
        map.put("content", smsMessage.getContent());
        StringEntity entity = new StringEntity(JsonEntityTransform.Object2Json(map),config.getCharset());
        httpPost.setEntity(entity);
        return httpPost;
    }
}
