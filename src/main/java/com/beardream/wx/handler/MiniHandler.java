package com.beardream.wx.handler;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by laxzh on 2017/6/11.
 * 微信小程序的相关服务
 */
public class MiniHandler {


    private String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    public String getMiniSession(String appId, String secret, String code) {
        String result = null;
        HttpClient httpClient = new HttpClient();
        url = url.replace("APPID", appId);
        url = url.replace("SECRET", secret);
        url = url.replace("JSCODE", code);
        GetMethod getMethod = new GetMethod(url);
        try {
            httpClient.executeMethod(getMethod);
            Header[] headers = getMethod.getResponseHeaders();
            int statusCode = getMethod.getStatusCode();
            result = new String(getMethod.getResponseBodyAsString().getBytes("UTF-8"));
            getMethod.releaseConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
