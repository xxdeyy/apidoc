package com.f2boy.service;

import com.f2boy.domain.entity.Admin;

import java.util.List;

/**
 * Created by xy on 15/8/17.
 */
public interface AdminService {

    Admin get(int id);

    Admin get(String username);

    List<Admin> getAll();
}
