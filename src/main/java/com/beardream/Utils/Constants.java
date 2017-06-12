package com.beardream.Utils;

/**
 * Created by soft01 on 2017/4/28.
 */
public class Constants {

    public final static String USER = "user";

    //登录的url
    public final static String LOGIN_URL = "/api/mobile/index";

    //小程序登录的url
    public final static String MINI_LOGIN_URL = "/api/mobile/mini";

    //判断小程序是否登录的url
    public final static String MINI_ISLOGIN_URL = "/api/mobile/mini/isLogin";

    // 文件的url
    public final static String FILE_URL = "/api/file";

    //微信验证token的url
    public final static String TOKEN_URL = "/api/mobile/wechat/portal";

    //微信创建菜单
    public final static String MENU_URL = "/api/mobile/wechat/portal/menucreate";

    //微信支付成功后的回调
    public final static String PAYNOTIFY_URL = "/api/mobile/order/payNotify";
}
