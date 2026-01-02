package com.rcompany.tablecreater.service;


import com.rcompany.tablecreater.models.Role;

public interface RoleService {
    Role getRoleByName(String roleName);
}