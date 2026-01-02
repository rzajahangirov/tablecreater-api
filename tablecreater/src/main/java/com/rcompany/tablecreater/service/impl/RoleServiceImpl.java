package com.rcompany.tablecreater.service.impl;


import com.rcompany.tablecreater.models.Role;
import com.rcompany.tablecreater.repository.RoleRepository;
import com.rcompany.tablecreater.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        Role role =roleRepository.findByName(roleName).orElseThrow();
        return role;
    }
}