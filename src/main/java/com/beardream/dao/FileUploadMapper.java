package com.beardream.dao;

import com.beardream.model.FileUpload;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileUploadMapper {

    FileUpload findBySelective(FileUpload fileUpload);

    int deleteByPrimaryKey(Integer fileId);

    int insert(FileUpload record);

    int insertSelective(FileUpload record);

    FileUpload selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(FileUpload record);

    int updateByPrimaryKey(FileUpload record);
}