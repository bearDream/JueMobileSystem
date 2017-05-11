package com.beardream.Utils;

import com.beardream.model.Result;

/**
 * Created by soft01 on 2017/4/17.
 */
public class ResultUtil {

    public final static Result success(Object data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("后台正常");
        result.setData(data);
        return result;
    }

    public final static Result success(Integer code, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg("后台正常");
        result.setData(data);
        return result;
    }

    public final static void success(){
        success(null);
    }

    public final static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public final static Result error(Integer code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
