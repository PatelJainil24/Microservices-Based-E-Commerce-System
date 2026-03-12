package com.javaexpress.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.clients.ProductServiceFeignClient;
import com.javaexpress.models.Favourite;
import com.javaexpress.repository.FavouriteRepository;

@Service
public class FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;  // Inject ProductService Feign Client


    // Add product to the wishlist (favourite)
    public Favourite addProductToWishlist(Long userId, Integer productId) {
        // Validate if the product exists using the Feign client
        productServiceFeignClient.getProduct(productId);  // This will throw an exception if not found

        // Check if the product is already in the wishlist
        Favourite favourite = favouriteRepository.findByUserIdAndProductId(userId, productId)
                .orElse(new Favourite());

        favourite.setUserId(userId);
        favourite.setProductId(productId);

        // Save the product to the wishlist
        return favouriteRepository.save(favourite);
    }

    // Remove product from the wishlist
    public void removeProductFromWishlist(Long userId, Integer productId) {
        Favourite favourite = favouriteRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist"));

        favouriteRepository.delete(favourite);
    }

    // Get all products in the user's wishlist
    public List<Favourite> getAllWishlistItems(Long userId) {
        return favouriteRepository.findByUserId(userId);
    }

    // Clear all products in the user's wishlist
    public void clearWishlist(Long userId) {
        List<Favourite> favourites = favouriteRepository.findByUserId(userId);
        favouriteRepository.deleteAll(favourites);
    }

    
}

