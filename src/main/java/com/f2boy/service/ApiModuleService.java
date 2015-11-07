package com.f2boy.service;

import com.f2boy.domain.entity.ApiModule;

import java.util.List;

public interface ApiModuleService {

    /**
     * 获取所有的模块
     */
    List<ApiModule> allModule();

    /**
     * 根据id查询ApiModule
     */
    ApiModule find(int id);

    /**
     * 保存接口模块
     */
    void save(ApiModule apiModule);

    /**
     * 删除接口模块
     */
    void delete(int id);

    /**
     * 计算最大的排序号
     */
    int calculateMaxSortNo();
}
