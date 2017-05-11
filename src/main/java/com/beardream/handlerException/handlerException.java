package com.beardream.handlerException;

import com.beardream.Utils.ResultUtil;
import com.beardream.exception.PermissionException;
import com.beardream.exception.UserException;
import com.beardream.model.Result;
import com.beardream.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by soft01 on 2017/4/18.
 */
@ControllerAdvice
public class handlerException {

    private final static Logger logger = LoggerFactory.getLogger(handlerException.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Result ExceptionResult(Exception e){
        if(e instanceof UserException){
            UserException userException = (UserException) e;
            return ResultUtil.error(userException.getCode(),userException.getMessage());
        }else if(e instanceof PermissionException){
            PermissionException permissionException = (PermissionException) e;
            return ResultUtil.error(permissionException.getCode(),permissionException.getMessage());
        }else {
            logger.error("异常={}",e);
            return ResultUtil.error(-1,"系统错误");
        }
    }
}
