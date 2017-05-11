package com.beardream.Controller;

import
        com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.RoleMapper;
import com.beardream.ioc.Log;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Result;
import com.beardream.model.Role;
import com.beardream.model.User;
import com.beardream.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 权限控制器
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色服务",description = "提供RESTful风格API的角色的增删改查服务")
@PermissionModule(text = "角色管理")
public class RoleController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService mRoleService;

    /*
        Put更新数据的请求只能是参数形式，不能写在body中
     */
    @ApiOperation("获取单个角色")
    @GetMapping
    @Log
    @PermissionMethod(text = "查看角色")
    public Result get(Role role, BindingResult bindingResult){
        System.out.println(role.getRoleId());
        return ResultUtil.success(mRoleService.findRole(role));
    }

    /*
        Put更新数据的请求只能是post的body形式，不能写在param参数中
     */
    @ApiOperation("添加角色")
    @PostMapping
    @Log
    public Result add(Role role){
        int result;
        result = mRoleService.addRole(role);
        if (result > 0)
            return ResultUtil.success("添加成功");
        else
            return ResultUtil.error(-1,"该角色已存在");
    }

    /*
    Put更新数据的请求只能是参数形式，不能写在body中
     */
    @ApiOperation("删除角色")
    @DeleteMapping
    @Log
    public Result delete(Role role){
        int result;
        result = mRoleService.deleteRole(role);
        if (result > 0)
            return ResultUtil.success("删除成功");
        else
            return ResultUtil.success("删除失败");
    }


    /*
        Put更新数据的请求只能是参数形式，不能写在body中
     */
    @ApiOperation("更新角色")
    @PutMapping
    @Log
    public Result update(Role role){
        int result;
        result = mRoleService.updateRole(role);
        if (result > 0)
            return ResultUtil.success("更新成功");
        else
            return ResultUtil.error(-1,"更新失败");
    }

    //需要分页
    // 需要两个参数： 当前所在页pageSize 需要几条数据limit
    @ApiOperation("分页获取角色")
    @GetMapping("/getpage")
    @Log
    public Result getPage(Role role, @RequestParam(value = "pageNum", required = false)  int pageNum, @RequestParam(value = "pageSize", required = false)  int pageSize, BindingResult bindingResult){
//        System.out.println(role.getRoleId());
        System.out.println(pageNum);
        System.out.println(pageSize);
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }

        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Role> roles =roleMapper.findBySelective(new Role());
        PageInfo page = new PageInfo(roles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list",roles);
        return ResultUtil.success(map);
    }
}
