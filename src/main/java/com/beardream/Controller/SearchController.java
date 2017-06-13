package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by soft01 on 2017/6/13.
 */
@RestController
@RequestMapping("/api/mobile/search")
public class SearchController {

    @GetMapping
    public Result search(@RequestParam String key){
        // 1、先模糊搜索商家，将结果装到一个集合中

        // 2、再模糊搜索菜品，将结果装到一个集合中

        // 3、最后将两个集合装到map中返回给前端
        return ResultUtil.success("");
    }
}
