package com.rcompany.tablecreater.service;

import com.rcompany.tablecreater.dtos.auth.RegisterDto;
import com.rcompany.tablecreater.dtos.auth.UserLoggedDto;
import com.rcompany.tablecreater.models.User;

public interface UserService {
    void register(RegisterDto registerDto);
    User findByLoggedUser(String email);
    User findUserById(Long userId);
    User findUserByEmail(String email);
    UserLoggedDto getLoggedUserInfo(String email);


}