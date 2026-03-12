package com.javaexpress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.models.Favourite;
import com.javaexpress.service.FavouriteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/favourites")
@Slf4j
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    // Add product to the wishlist
    @PostMapping("/user/{userId}")
    public ResponseEntity<Favourite> addProductToWishlist(
            @PathVariable Long userId, 
            @RequestParam Integer productId) {
        log.info("FavouriteController addProductToWishlist");
        Favourite favourite = favouriteService.addProductToWishlist(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(favourite);
    }

    // Remove product from the wishlist
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(
            @PathVariable Long userId, 
            @PathVariable Integer productId) {
    	log.info("FavouriteController removeProductFromWishlist");
        favouriteService.removeProductFromWishlist(userId, productId);
        return ResponseEntity.noContent().build();
    }

    // Get all products in the user's wishlist
    @GetMapping("/{userId}")
    public ResponseEntity<List<Favourite>> getAllWishlistItems(@PathVariable Long userId) {
    	log.info("FavouriteController getAllWishlistItems");
        List<Favourite> favourites = favouriteService.getAllWishlistItems(userId);
        return ResponseEntity.ok(favourites);
    }

    // Clear all products in the user's wishlist
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearWishlist(@PathVariable Long userId) {
    	log.info("FavouriteController clearWishlist");
        favouriteService.clearWishlist(userId);
        return ResponseEntity.noContent().build();
    }

   
}

