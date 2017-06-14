package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.model.Result;
import com.beardream.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by soft01 on 2017/6/13.
 */
@RestController
@RequestMapping("/api/mobile/search")
public class SearchController {

    @Autowired
    private SearchService mSearchService;

    @GetMapping
    public Result search(@RequestParam String key){

        Map result = mSearchService.search(key);
        return ResultUtil.success(result);
    }
}
