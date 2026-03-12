package com.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.UserDto;
import com.javaexpress.feignsclients.UserFeignClient;


@Service
public class UserIntegrationService {

    @Autowired
    private UserFeignClient userFeignClient;

    public UserDto fetchUser(Long userId) {
        UserDto userDto = userFeignClient.findById(userId);
        if (userDto == null) {
            throw new RuntimeException("User Not Found in DB");
        }
        return userDto;
    }

    public UserDto userFallBack(Integer userId, Throwable t) {
        // Log and return default/fallback
        //System.out.println("Fallback triggered: " + t.getMessage());
        System.out.println("Circuit breaker is open. Returning fallback user.");
        throw new RuntimeException("User service is unavailable. Please try again later.");
    }
}
