package com.rcompany.tablecreater.security;

import com.rcompany.tablecreater.models.User;
import com.rcompany.tablecreater.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        System.out.println(username);
        if (user != null){
            org.springframework.security.core.userdetails.User loginUser = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    user.getRoles().stream().map(m-> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList())
            );
            return loginUser;
        }else{
            throw  new UsernameNotFoundException("User not found");
        }
    }
}