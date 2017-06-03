package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.EvaluateMapper;
import com.beardream.dao.UserCollectionMapper;
import com.beardream.model.Evaluate;
import com.beardream.model.Result;
import com.beardream.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    public List getEvaluateList(Evaluate evaluate){
        return mEvaluateMapper.findEvaluate(evaluate);
    }
}
