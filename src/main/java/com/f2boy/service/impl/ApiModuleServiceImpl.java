package com.f2boy.service.impl;

import com.f2boy.dao.ApiModuleMapper;
import com.f2boy.domain.entity.ApiModule;
import com.f2boy.domain.entity.ApiModuleExample;
import com.f2boy.service.ApiModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApiModuleServiceImpl implements ApiModuleService {

    @Autowired
    private ApiModuleMapper apiModuleMapper;

    /**
     * 获取所有的模块（按照排序号从小到大）
     */
    @Override
    public List<ApiModule> allModule() {
        ApiModuleExample example = new ApiModuleExample();

        example.setOrderByClause("sort_no asc, update_time desc");
        return apiModuleMapper.selectByExample(example);

    }

    /**
     * 根据id查询ApiModule
     */
    @Override
    public ApiModule find(int id) {
        return apiModuleMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存接口
     *
     * @param apiModule
     */
    @Override
    public void save(ApiModule apiModule) {
        if (apiModule == null) {
            return;
        }

        if (apiModule.getSortNo() == null || apiModule.getSortNo() <= 0) {
            apiModule.setSortNo(calculateMaxSortNo() + 1);
        }

        Date now = new Date();
        apiModule.setUpdateTime(now);
        if (apiModule.getId() == null) {
            apiModule.setCreateTime(now);
            apiModuleMapper.insertSelective(apiModule);
        } else {
            apiModuleMapper.updateByPrimaryKeySelective(apiModule);
        }

        // 重新更新排序号
        refreshSortNo();
    }

    /**
     * 删除接口
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        apiModuleMapper.deleteByPrimaryKey(id);
        refreshSortNo();
    }

    /**
     * 计算最大的排序号
     */
    @Override
    public int calculateMaxSortNo() {
        List<ApiModule> allModule = allModule();
        if (allModule.isEmpty()) {
            return 0;
        } else {
            return allModule.get(allModule.size() - 1).getSortNo();
        }
    }

    private void refreshSortNo() {
        List<ApiModule> allModule = allModule();
        for (int i = 0; i < allModule.size(); ++i) {
            ApiModule ele = allModule.get(i);
            int expectSortNo = i + 1;
            if (expectSortNo != ele.getSortNo()) {
                ele.setSortNo(expectSortNo);
                apiModuleMapper.updateByPrimaryKeySelective(ele);
            }
        }
    }
}
