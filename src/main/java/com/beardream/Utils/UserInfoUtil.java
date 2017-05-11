package com.beardream.Utils;

/**
 * Created by soft01 on 2017/5/10.
 */
public class UserInfoUtil {

    // 获取code
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";
    //替换字符串
    public static String getCode(String APPID, String REDIRECT_URI,String SCOPE) {
        return String.format(Get_Code,APPID,REDIRECT_URI,SCOPE);
    }

    public static void main(String[] arg){
//        String  REDIRECT_URI = "http://171590mx26.51mypc.cn/dish";
//        String SCOPE = "snsapi_userinfo";
//        //appId
//        String appId = "wx8483137e5d79b797";
//
//        String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE);
//        System.out.println("getCodeUrl:"+getCodeUrl);

        try {
            Menu.createCustomMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
