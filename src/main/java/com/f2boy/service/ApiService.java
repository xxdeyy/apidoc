package com.f2boy.service;

import com.f2boy.domain.entity.Api;

import java.util.List;

public interface ApiService {

    /**
     * 获取所有的接口
     */
    List<Api> allApi();

    /**
     * 获取模块下的接口列表
     *
     * @param moduleId 模块id
     */
    List<Api> findByModule(int moduleId);

    /**
     * 获取接口详情
     *
     * @param id 接口id
     */
    Api find(int id);

    /**
     * 保存接口
     */
    void save(Api api);

    /**
     * 删除接口
     */
    void delete(int id);

    /**
     * 计算某模块中接口最大的排序号
     */
    int calculateMaxSortNo(int moduleId);
}
