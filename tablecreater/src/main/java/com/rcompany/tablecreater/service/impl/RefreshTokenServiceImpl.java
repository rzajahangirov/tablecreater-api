package com.rcompany.tablecreater.service.impl;




import com.rcompany.tablecreater.models.RefreshToken;
import com.rcompany.tablecreater.models.User;
import com.rcompany.tablecreater.repository.RefreshTokenRepository;
import com.rcompany.tablecreater.repository.UserRepository;
import com.rcompany.tablecreater.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(username).orElseThrow())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public boolean removeToken(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            if (user != null){
                RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElseThrow();
                refreshTokenRepository.delete(refreshToken);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }


    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}