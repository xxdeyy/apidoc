package com.f2boy.dao;

import com.f2boy.domain.entity.Api;
import com.f2boy.domain.entity.ApiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApiMapper {
    int countByExample(ApiExample example);

    int deleteByExample(ApiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Api record);

    int insertSelective(Api record);

    List<Api> selectByExample(ApiExample example);

    Api selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Api record, @Param("example") ApiExample example);

    int updateByExample(@Param("record") Api record, @Param("example") ApiExample example);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);
}