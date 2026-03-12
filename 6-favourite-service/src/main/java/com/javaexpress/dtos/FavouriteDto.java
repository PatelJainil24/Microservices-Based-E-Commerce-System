package com.javaexpress.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FavouriteDto {

	private Integer userId;
	private Integer productId;
	
	@JsonFormat(pattern = "dd-MM-yyyy_HH:mm:ss")
	private LocalDateTime likedDate;
}
