package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.RoleMapper;
import com.beardream.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by soft01 on 2017/5/9.
 */
@Component
@Service
public class RoleService {

    @Autowired
    private RoleMapper mRoleMapper;

    public List<Role> findRole(Role role){
        List<Role> roles = mRoleMapper.findBySelective(role);
        return roles;
    }

    public int addRole(Role role){
        int result;
        List<Role> exit = mRoleMapper.findBySelective(role);
        if (exit.size() > 0)
            return -2;
        role.setAddTime(new Date());
        result = mRoleMapper.insertSelective(role);
        return result;
    }

    public int deleteRole(Role role){
        int result;
        result = mRoleMapper.deleteByPrimaryKey(role.getRoleId());
        return result;
    }

    public int updateRole(Role role){
        role.setAddTime(new Date());
        int result = mRoleMapper.updateByPrimaryKeySelective(role);
        return result;
    }
}
