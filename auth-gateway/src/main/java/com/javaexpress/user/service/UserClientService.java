package com.javaexpress.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaexpress.user.dto.UserDto;

@FeignClient(name = "USER-SERVICE", contextId = "userClientService", path = "/api/users")
public interface UserClientService {

	@PostMapping
	public ResponseEntity<UserDto> save(@RequestBody UserDto userDto);

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findById(@PathVariable Integer userId);

}
