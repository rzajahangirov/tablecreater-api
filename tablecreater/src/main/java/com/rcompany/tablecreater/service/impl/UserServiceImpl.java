package com.rcompany.tablecreater.service.impl;

import com.rcompany.tablecreater.dtos.auth.RegisterDto;
import com.rcompany.tablecreater.dtos.auth.UserLoggedDto;
import com.rcompany.tablecreater.models.User;
import com.rcompany.tablecreater.repository.UserRepository;
import com.rcompany.tablecreater.service.RoleService;
import com.rcompany.tablecreater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        User user = modelMapper.map(registerDto, User.class);
        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User findByLoggedUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserLoggedDto getLoggedUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        UserLoggedDto userLoggedDto = modelMapper.map(user, UserLoggedDto.class);
        String role = "user";
        userLoggedDto.setActive(true);
        userLoggedDto.setRoleName(role);
        return userLoggedDto;
    }
}
