package com.beardream.config;

import com.beardream.dao.MethodMapper;
import com.beardream.dao.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * Created by soft01 on 2017/5/7.
 */
@Component
public class PermissionScanner implements CommandLineRunner {

    @Autowired
    private ModuleMapper moduleMap;

    @Autowired
    private MethodMapper methodMapper;

    @Override
    public void run(String... args) throws Exception {
        //扫描所有controller，注册权限模块和方法
//        new AddPermission().scanner(moduleMap, methodMapper);
    }

}
