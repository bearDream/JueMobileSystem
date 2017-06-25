package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.EvaluateMapper;
import com.beardream.dao.UserCollectionMapper;
import com.beardream.model.Evaluate;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.model.UserCollection;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by beardream on 2017/6/3
 */
@Component
@Service
public class EvaluateService {

    @Autowired
    private EvaluateMapper mEvaluateMapper;

    //发表评价
    public Result add(UserCollection userCollection) {
        //TODO
        return ResultUtil.success("");
    }

    public List getEvaluateList(Evaluate evaluate) throws UnsupportedEncodingException {
        List<Evaluate> evaluates = mEvaluateMapper.findEvaluate(evaluate);
        for (Evaluate evaluate1 : evaluates) {
            evaluate1.setUsername(MimeUtility.decodeText(evaluate1.getUsername()));
        }
        return mEvaluateMapper.findEvaluate(evaluate);
    }

    public Result add(Evaluate evaluate, User user){
        evaluate.setUserId(user.getUserId());
        evaluate.setStar((byte) 1);
        evaluate.setIsAnonymous((byte) 0);
        evaluate.setAddTime(new Date());
        Integer res = mEvaluateMapper.insert(evaluate);
        if (res != 0)
            return ResultUtil.success("发表成功");
        return ResultUtil.success("发表失败");
    }
}
