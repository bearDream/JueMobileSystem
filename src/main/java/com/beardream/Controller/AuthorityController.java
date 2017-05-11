package com.beardream.Controller;

import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.MethodMapper;
import com.beardream.dao.ModuleMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.ioc.Log;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by soft01 on 2017/5/7.
 */
@RestController
@RequestMapping("/authority")
@Api(value = "权限服务",description = "提供RESTful风格API的权限的增删改查服务")
@PermissionModule(text = "权限管理")
public class AuthorityController {

    @Autowired
    private ModuleMapper mModuleMapper;

    @Autowired
    private MethodMapper mMethodMapper;

    @Autowired
    private RoleMapper mRoleMapper;

    /*
        查看所有权限，一个module对应多个method
        先查询所有的module  再从method中遍历所有module_id响应的method
     */
    @ApiOperation("获取所有权限")
    @GetMapping
    @Log
    @PermissionMethod(text = "查看所有权限")
    public Result get(){
        //权限树的list
        List<AuthorityTree> authorityTrees = new ArrayList<>();
        List<Module> modules = mModuleMapper.findBySelective();
        for (Module modules1 : modules){
            //根据modules1的module_id查找相应的method
            AuthorityTree authorityTree = new AuthorityTree();
            authorityTree.setModuleName(modules1.getModuleName());
            authorityTree.setControllerKey(modules1.getControllerkey());
//            System.out.println("模块名："+modules1.getModuleName());
            Method method = new Method();
            method.setModuleId(modules1.getModuleId());
            List<Method> methodsList = new ArrayList<>();
            List<Method> methods = mMethodMapper.selectBySelective(method);
            for (Method method1 : methods){
//                System.out.println("方法名："+method1.getMethodName());
                methodsList.add(method1);
            }
            authorityTree.setMethod(methodsList);
            authorityTrees.add(authorityTree);
        }
        return ResultUtil.success(authorityTrees);
    }

    /*
        Post更新数据的请求只能是参数形式，不能写在body中
        参数信息应该按照如下格式：
    {"code":"1","msg":"权限设置","data":[{
      "controllerKey": "/authority",
      "moduleName": "权限管理",
      "method": [
        {
          "methodId": 1,
          "methodName": "查看所有权限",
          "methodContent": null,
          "moduleId": 1,
          "actionkey": "/authority/get"
        },
        {
          "methodId": 2,
          "methodName": "设置权限",
          "methodContent": null,
          "moduleId": 1,
          "actionkey": "/authority/update"
        }
      ]
    }]
    }]}
     */
    @ApiOperation("设置权限 格式为{\"code\":\"1\",\"msg\":\"1\",\"data\":[{}]}")
    @PostMapping
    @Log
    @PermissionMethod(text = "设置权限")
    public Result post(@RequestBody Result result){

        StringBuilder promission = new StringBuilder();
        System.out.println(result.getCode());
        Gson gson = new Gson();
        result.setData(gson.toJson(result.getData()));
        System.out.println(result.getData());

        Map<String, Class> beanMap = new HashMap<String, Class>();
        beanMap.put("method", Method.class);

        JSONArray jsonArray = JSONArray.fromObject(result.getData());

        List<AuthorityTree> authoritylist = (List) JSONArray.toList(jsonArray, AuthorityTree.class, beanMap);
        for (AuthorityTree authorityTree : authoritylist){
            System.out.println(authorityTree.getControllerKey());
            for (int i = 0 ; i < authorityTree.getMethod().size() ; i++){
                promission.append(authorityTree.getMethod().get(i).getActionkey()).append(",");
            }
        }
        //去除最后一个逗号
        promission = new StringBuilder(promission.substring(0,promission.length()-1));
        System.out.println(promission.toString());
        if (TextUtil.isBlank(result.getMsg())){
            return ResultUtil.error(-1, "roleId不能为空","更新失败");
        }
        Role role = new Role();
        role.setPromission(promission.toString());
        role.setRoleId(Integer.parseInt(result.getMsg()));
        if(mRoleMapper.updateByPrimaryKeySelective(role) != 0){
            return ResultUtil.success("更新成功");
        }else {
            return ResultUtil.error(-1,"更新失败");
        }
    }

}
