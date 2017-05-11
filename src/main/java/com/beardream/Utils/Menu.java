package com.beardream.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by soft01 on 2017/5/10.
 */
public class Menu {
    public static String getMenuStr() throws JSONException {
        JSONObject firstLevelMenu = new JSONObject();//一级菜单
        JSONArray firstLevelMenuArray = new JSONArray();//一级菜单列表


        //一级菜单内容1
        JSONObject firstLevelMenuContext1 = new JSONObject();
        firstLevelMenuContext1.put("type", "click");
        firstLevelMenuContext1.put("name", "订座");
        firstLevelMenuContext1.put("key", "V1001_TODAY_MUSIC");

        //一级菜单内容2
        JSONObject firstLevelMenuContext2 = new JSONObject();
        //一级菜单内容2的二级菜单列表
        JSONArray firstLevelMenuContext2Array = new JSONArray();
        //一级菜单内容2的二级菜单内容1
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("type", "click");
        jsonObject1.put("name", "歌曲");
        jsonObject1.put("key", "V1001_TODAY_MUSIC");
        //一级菜单内容2的二级菜单内容2
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("type", "view");
        jsonObject2.put("name", "视频");
        jsonObject2.put("url", "http://171590mx26.51mypc.cn/dish");
        firstLevelMenuContext2Array.put(jsonObject1);
        firstLevelMenuContext2Array.put(jsonObject2);
        firstLevelMenuContext2.put("name", "菜单");
        firstLevelMenuContext2.put("sub_button", firstLevelMenuContext2Array);

        //一级菜单内容3
        JSONObject firstLevelMenuContext3 = new JSONObject();
        firstLevelMenuContext3.put("type", "click");
        firstLevelMenuContext3.put("name", "视频");
        firstLevelMenuContext3.put("key", "V1001_TODAY_MOVIE");


        firstLevelMenuArray.put(firstLevelMenuContext1);
        firstLevelMenuArray.put(firstLevelMenuContext2);
        firstLevelMenuArray.put(firstLevelMenuContext1);


        firstLevelMenu.put("button", firstLevelMenuArray);

        return firstLevelMenu.toString();
    }

    public static String getAccessToken() throws Exception{
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8483137e5d79b797&secret=33956bffeb03975f38827202c6521b99";
        URL url = new URL(accessTokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        //获取返回的字符
        InputStream inputStream = connection.getInputStream();
        int size =inputStream.available();
        byte[] bs =new byte[size];
        inputStream.read(bs);
        String message=new String(bs,"UTF-8");

        //获取access_token
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        return jsonObject.getString("access_token");
    }

    public static void createCustomMenu() throws Exception{
        String custmMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

        //获取access_token
        String accessToken = getAccessToken();
        custmMenuUrl = custmMenuUrl + accessToken;

        URL url = new URL(custmMenuUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(getMenuStr().getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = connection.getInputStream();
        int size =inputStream.available();
        byte[] bs =new byte[size];
        inputStream.read(bs);
        String message=new String(bs,"UTF-8");

        System.out.println(message);
    }
}
