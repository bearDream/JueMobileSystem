package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.NumberMapper;
import com.beardream.model.Business;
import com.beardream.model.Number;
import com.beardream.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

    // 取号 该方法应该为一个同步方法，避免多用户同时操作该方法,造成取号数据不准确
    public Result takeNum(Number number){
        synchronized (this) {
            Business business = mBusinessMapper.selectByPrimaryKey(number.getBusinessId());
            if (business == null || business.getIsTake() == 0){
                return ResultUtil.error(-1,"商家暂未开放取号，请稍候再来哦");
            }

            List<Number> queue = mNumberMapper.findBySelective(number);

            // 查找该用户是否已经取过号，防止用户重复取号
            for (Number number1 : queue) {
                if (number1.getUserId().equals(number.getUserId()) && number1.getBusinessId().equals(number.getBusinessId())){
                    return ResultUtil.error(-1,"您已在该商家取过号，不可重复取号");
                }
            }

            // 如果队列为空，则说明没有人排队,则直接插入即可
            if (queue.size() == 0){
                number.setNumber(1);
            }else {
                Collections.sort(queue, new Comparator<Number>() {
                    @Override
                    public int compare(Number o1, Number o2) {
                        if(o1 instanceof Number && o2 instanceof Number){
                            Number e1 = (Number) o1;
                            Number e2 = (Number) o2;
                            return e1.getNumber().compareTo(e2.getNumber());
                        }
                        throw new ClassCastException("不能转换为Number类型");
                    }
                });

                for (Number number1 : queue){
                    System.out.println(number1);
                }

                // 获得最大的number
                int maxNum = queue.get(queue.size()-1).getNumber();
                System.out.println(maxNum);
                //设置取的号为这个number+1添加到数据库
                number.setNumber(maxNum+1);
            }

            number.setIsExpired((byte) 1);
            number.setAddTime(new Date());


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
        if (numberList.size() > 0)
            return ResultUtil.success(numberList.get(0));
        else
            return ResultUtil.error(-1,"你还没取号呢，请先取号");
    }

    // 刷新队列，返回当前排到第几号（系统，商家访问）
    public Result refreshNum(Number number){

        List<Number> queue = mNumberMapper.findBySelective(number);

        Collections.sort(queue, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                if(o1 instanceof Number && o2 instanceof Number){
                    Number e1 = (Number) o1;
                    Number e2 = (Number) o2;
                    return e1.getNumber().compareTo(e2.getNumber());
                }
                throw new ClassCastException("不能转换为Number类型");
            }
        });

        return ResultUtil.success(queue.get(0));
    }

    // 过号
    public Result passNum(Number number) {

        List<Number> numberList = mNumberMapper.findBySelective(number);

        if (numberList.size() > 0){
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
        Collections.sort(remainNumberList, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                if(o1 instanceof Number && o2 instanceof Number){
                    Number e1 = (Number) o1;
                    Number e2 = (Number) o2;
                    return e1.getNumber().compareTo(e2.getNumber());
                }
                throw new ClassCastException("不能转换为Number类型");
            }
        });

        System.out.println("getRemainNums");
        for (Number number1 : remainNumberList) {
            System.out.println(number1.getNumber());
        }

        return remainNumberList;
    }
}