package com.javaexpress.feignsclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaexpress.dto.UserDto;


@FeignClient(name="USER-SERVICE",path="/api/users") 
public interface UserFeignClient {

	@GetMapping("/{userId}")
	public UserDto findById(@PathVariable Long userId);
}

