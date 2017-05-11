package com.beardream.Utils;

import com.beardream.model.User;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by laxzh on 2017/5/6.
 * Gson将model转换为json格式
 */
public class Json {


    public static <T> String toJson(T model){
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    public static <T> T fromJson(String model, Class<T> modelName){
        Gson gson = new Gson();
        return gson.fromJson(model, (Type) modelName);
    }
}
