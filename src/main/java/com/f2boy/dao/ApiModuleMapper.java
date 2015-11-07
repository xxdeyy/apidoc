package com.f2boy.dao;

import com.f2boy.domain.entity.ApiModule;
import com.f2boy.domain.entity.ApiModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApiModuleMapper {
    int countByExample(ApiModuleExample example);

    int deleteByExample(ApiModuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApiModule record);

    int insertSelective(ApiModule record);

    List<ApiModule> selectByExample(ApiModuleExample example);

    ApiModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApiModule record, @Param("example") ApiModuleExample example);

    int updateByExample(@Param("record") ApiModule record, @Param("example") ApiModuleExample example);

    int updateByPrimaryKeySelective(ApiModule record);

    int updateByPrimaryKey(ApiModule record);
}