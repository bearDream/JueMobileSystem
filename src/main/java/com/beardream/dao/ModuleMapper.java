package com.beardream.dao;

import com.beardream.model.Module;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ModuleMapper {
    int deleteByPrimaryKey(Integer moduleId);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer moduleId);

    List<Module> findBySelective();

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    int truncate();

}