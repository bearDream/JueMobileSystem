package com.beardream.config;

import com.beardream.dao.NumberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by soft01 on 2017/6/6.
 */
@Component
public class InValidTakeNumConfiguration {

    private static Logger logger = LoggerFactory.getLogger(InValidTakeNumConfiguration.class);

    @Autowired
    private NumberMapper mNumberMapper;

    /*
        每天凌晨一点自动清理昨天取的号但没有设置为过期的号
        每天凌晨00：15触发
        @Scheduled(cron = "0/2 * * * * *") 两秒钟触发一次
     */
    @Scheduled(cron = "0 15 0 ? * *")
    public void InvalidTakeNum() {
        // 直接更新所有已经过期的所有号码`
        int result = mNumberMapper.updateExpiredBySelective(new Date());

        logger.info("取号更新条数：{}", result);
    }
}
