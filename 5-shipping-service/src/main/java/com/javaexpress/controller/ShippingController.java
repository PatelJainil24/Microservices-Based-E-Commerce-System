package com.javaexpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.ShippingRequestDTO;
import com.javaexpress.dto.ShippingResponseDTO;
import com.javaexpress.service.ShippingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shipping")
@Slf4j
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/ship/{orderId}")
    public ShippingResponseDTO shipOrder(@PathVariable Long orderId, @RequestBody ShippingRequestDTO request) {
    	log.info("ShippingController shipOrder");
        return shippingService.shipOrder(orderId, request);
    }

    @GetMapping("/{orderId}")
    public ShippingResponseDTO getShippingDetails(@PathVariable Long orderId) {
    	log.info("ShippingController getShippingDetails");
        return shippingService.getShippingDetails(orderId);
    }

    @PutMapping("/update-status/{orderId}")
    public Boolean updateStatus(@PathVariable Long orderId, @RequestParam String status) {
    	log.info("ShippingController updateStatus");
        shippingService.updateShippingStatus(orderId, status);
        return true;
    }
}

