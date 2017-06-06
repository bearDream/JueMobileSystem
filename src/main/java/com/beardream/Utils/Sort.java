package com.beardream.Utils;

import com.beardream.model.Business;
import com.beardream.model.Number;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by soft01 on 2017/5/26.
 */
public class Sort {

    // 封装的排序方法，根据sort不同而不同
    public static List<Number> sortNumberDesc(List<Number> numberList, String sort){
        Collections.sort(numberList, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                if(o1 instanceof Number && o2 instanceof Number){
                    Number e1 = (Number) o1;
                    Number e2 = (Number) o2;
                    if (sort.equals("asc"))
                        return e1.getNumber().compareTo(e2.getNumber());
                    if (sort.equals("desc"))
                        return e2.getNumber().compareTo(e1.getNumber());
                }
                throw new ClassCastException("不能转换为Number类型");
            }
        });
        return numberList;
    }

    // 将等待桌数进行排序
    public static List<Business> sortBusinessDesc(List<Business> businessList, String sort){
        Collections.sort(businessList, new Comparator<Business>() {
            @Override
            public int compare(Business o1, Business o2) {
                if(o1 instanceof Business && o2 instanceof Business){
                    Business e1 = (Business) o1;
                    Business e2 = (Business) o2;
                    return e1.getWait().compareTo(e2.getWait());
                }
                throw new ClassCastException("不能转换为Business类型");
            }
        });
        return businessList;
    }

    // 按照距离排序
    public static List<Business> sortBusinessDistance(List<Business> businessList, String sort){
        Collections.sort(businessList, new Comparator<Business>() {
            @Override
            public int compare(Business o1, Business o2) {
                if(o1 instanceof Business && o2 instanceof Business){
                    Business e1 = (Business) o1;
                    Business e2 = (Business) o2;
                    return e1.getDistance().compareTo(e2.getDistance());
                }
                throw new ClassCastException("不能转换为Business类型");
            }
        });
        return businessList;
    }

    // 按照等级排序
    public static List<Business> sortBusinessLevel(List<Business> businessList, String sort) {
        Collections.sort(businessList, new Comparator<Business>() {
            @Override
            public int compare(Business o1, Business o2) {
                if (o1 instanceof Business && o2 instanceof Business) {
                    Business e1 = (Business) o1;
                    Business e2 = (Business) o2;
                    return e1.getLevel().compareTo(e2.getLevel());
                }
                throw new ClassCastException("不能转换为Business类型");
            }
        });
        return businessList;
    }

}
