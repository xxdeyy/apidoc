package com.f2boy.service.impl;

import com.f2boy.dao.ApiMapper;
import com.f2boy.domain.entity.Api;
import com.f2boy.domain.entity.ApiExample;
import com.f2boy.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    ApiMapper apiMapper;

    /**
     * 获取所有的接口
     */
    @Override
    public List<Api> allApi() {
        ApiExample example = new ApiExample();

        example.setOrderByClause("module_id asc, sort_no asc");
        return apiMapper.selectByExample(example);
    }

    /**
     * 获取模块下的接口列表
     *
     * @param moduleId 模块id
     */
    @Override
    public List<Api> findByModule(int moduleId) {
        ApiExample example = new ApiExample();
        ApiExample.Criteria criteria = example.createCriteria();
        criteria.andModuleIdEqualTo(moduleId);

        example.setOrderByClause("sort_no asc");
        return apiMapper.selectByExample(example);
    }

    /**
     * 获取接口详情
     *
     * @param id 接口id
     */
    @Override
    public Api find(int id) {
        return apiMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存接口
     *
     * @param api
     */
    @Override
    public void save(Api api) {
        if (api == null) {
            return;
        }

        if (api.getModuleId() == null) {
            api.setModuleId(0);
        }

        if (api.getSortNo() == null || api.getSortNo() <= 0) {
            api.setSortNo(calculateMaxSortNo(api.getModuleId()) + 1);
        }

        if (api.getId() == null) {
            apiMapper.insertSelective(api);
        } else {
            // 注意这里没有Selective
            apiMapper.updateByPrimaryKey(api);
        }

        // 重新更新排序号
        refreshSortNo(api.getModuleId());
    }

    /**
     * 删除接口
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        Api api = find(id);
        if (api == null) {
            return;
        }

        apiMapper.deleteByPrimaryKey(id);
        refreshSortNo(api.getModuleId());
    }

    /**
     * 计算最大的排序号
     */
    @Override
    public int calculateMaxSortNo(int moduleId) {

        if (moduleId <= 0) {
            return 0;
        }

        List<Api> apis = findByModule(moduleId);

        if (apis.isEmpty()) {
            return 0;
        } else {
            return apis.get(apis.size() - 1).getSortNo();
        }
    }

    private void refreshSortNo(int moduleId) {
        List<Api> apis = findByModule(moduleId);
        for (int i = 0; i < apis.size(); ++i) {
            Api ele = apis.get(i);
            int expectSortNo = i + 1;
            if (expectSortNo != ele.getSortNo()) {
                ele.setSortNo(expectSortNo);
                apiMapper.updateByPrimaryKeySelective(ele);
            }
        }
    }
}
