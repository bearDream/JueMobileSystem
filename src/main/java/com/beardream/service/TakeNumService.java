package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.Sort;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.NumberMapper;
import com.beardream.dao.UserMapper;
import com.beardream.model.Business;
import com.beardream.model.Number;
import com.beardream.model.Result;
import com.beardream.model.User;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.*;

/**
 * Created by bearDream on 2017/5/22.
 *
 * 取号：
 * 1、先判断商家是否开放取号,判断is_take是否为1
 * 2、查找num表，根据business,is_expire取出数据
 * 3、将数据装到list中，得到前面还有几桌以及当前排到号
 */
@Component
@Service
public class TakeNumService {

    @Autowired
    private NumberMapper mNumberMapper;

    @Autowired
    private BusinessMapper mBusinessMapper;

    @Autowired
    private UserMapper mUserMapper;

    // 取号 该方法应该为一个同步方法，避免多用户同时操作该方法,造成取号数据不准确
    public Result takeNum(Number number){
        synchronized (this) {
            // * 先保存取号用户的id，后面需要用到
            int userId = number.getUserId();
            // 1、判断商家是否开放取号功能
            Business business = mBusinessMapper.selectByPrimaryKey(number.getBusinessId());
            if (business == null || business.getIsTake() == 0){
                return ResultUtil.error(-1,"商家暂未开放取号，请稍候再来哦");
            }

            // 2、查出该商家当前已经取号的队列
            number.setUserId(null);
            List<Number> queue = mNumberMapper.findBySelective(number);

            // 3、查找该用户是否已经取过号，防止用户重复取号
            for (Number number1 : queue) {
                if (number1.getUserId().equals(number.getUserId()) && number1.getBusinessId().equals(number.getBusinessId())){
                    return ResultUtil.error(-1,"您已在该商家取过号，不可重复取号");
                }
            }

            // 4、如果队列为空，则说明没有人排队,则直接插入即可
            if (queue.size() == 0){
                number.setNumber(1);
            }else {
                // 5、队列有人排队，则先将队列按照取号大小正序排列
                queue = Sort.sortNumberDesc(queue, "asc");


                // 6、获得最大的number
                int maxNum = queue.get(queue.size()-1).getNumber();
                // 7、设置取的号为这个number+1添加到数据库
                number.setNumber(maxNum+1);
            }

            number.setIsExpired((byte) 1);
            number.setAddTime(new Date());
            number.setUserId(userId);

            mNumberMapper.insert(number);
            // 将取的号返回给控制器
            return ResultUtil.success(number.getNumber().toString());
        }
    }

    // 获取用户所取的号 （顾客访问）
    public Result getCurrentNum(Number number){

        // 查找用户在某商家取的号
        List<Number> numberList = mNumberMapper.findBySelective(number);

        // numberList有值说明取过号，否则说明还没有取号
        if (numberList.size() > 0){
            // 1、查找他需要等待的号数  businessID,is_expire
            number.setUserId(null);
            List<Number> waitQue = mNumberMapper.findBySelective(number);

            // 2、排序
            waitQue = Sort.sortNumberDesc(waitQue, "asc");

            // 3、将排序后的第一个元素取出来，用 用户取得的号减去第一个元素的号即是需要等待的号
            int waitNum = numberList.get(0).getNumber() - waitQue.get(0).getNumber();
            Map<String, Object> map = new HashedMap();
            map.put("waitNum", waitNum);
            map.put("myNum", numberList.get(0));

            return ResultUtil.success(map);
        } else
            return ResultUtil.error(-1,"你还没取号呢，请先取号");
    }

    // 刷新队列，返回当前排到第几号（系统，商家访问）
    public Result refreshNum(Number number){

        List<Number> queue = mNumberMapper.findBySelective(number);

        queue = Sort.sortNumberDesc(queue, "asc");

        return ResultUtil.success(queue.get(0));
    }

    // 获取商家当前的排队信息
    public List<Number> getBusinessNum(Business business){
        // 根据商家id查询当前排队数，等待人数
        Number number = new Number();
        // 只要有效的号
        number.setIsExpired((byte) 1);
        number.setBusinessId(business.getBusinessId());
        // 获取排队队列
        List<Number> queue = mNumberMapper.findBySelective(number);

        queue = Sort.sortNumberDesc(queue, "asc");

        return queue;
    }

    // 叫号  获取这个被叫号的用户的用户信息  根据user_id获取
    public Result callNum(Number number) {
        number.setIsExpired((byte) 1);
        List<Number> numberList = mNumberMapper.findBySelective(number);
        int userId = numberList.get(0).getUserId();

        User user = mUserMapper.selectByPrimaryKey(userId);

        if (user == null){
            return ResultUtil.error(-1,"用户不存在");
        }else {
            return ResultUtil.success(user);
        }
    }

    // 过号
    public Result passNum(Number number) {

        // 查找过号的 这个号 的相关信息
        List<Number> numberList = mNumberMapper.findBySelective(number);

        if (numberList.size() > 0){
            // 将该号设置过期
            number.setNumId(numberList.get(0).getNumId());
            number.setAddTime(numberList.get(0).getAddTime());
            number.setIsExpired((byte) 2);
            mNumberMapper.updateByPrimaryKeySelective(number);
            // 获取后面的所有号
            List<Number> remainList = getRemainNums(number);
            return ResultUtil.success(remainList);
        }else {
            return ResultUtil.error(-1,"该号不存在");
        }
    }

    // 取得剩下的所有号的集合  需要知道当前刚过的号（根据business_id, number, add_time拿到这个值）
    public List<Number> getRemainNums(Number number){
        List<Number> remainNumberList = mNumberMapper.findCurrentNumListBySelective(number);

        // 将该队列排序
        remainNumberList = Sort.sortNumberDesc(remainNumberList, "asc");

        System.out.println("getRemainNums");
        for (Number number1 : remainNumberList) {
            System.out.println(number1.getNumber());
        }

        return remainNumberList;
    }

}