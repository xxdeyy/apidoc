package com.f2boy.service.impl;

import com.f2boy.dao.AdminMapper;
import com.f2boy.domain.entity.Admin;
import com.f2boy.domain.entity.AdminExample;
import com.f2boy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xy on 15/8/17.
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin get(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public Admin get(String username) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<Admin> list = adminMapper.selectByExample(example);

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

}
