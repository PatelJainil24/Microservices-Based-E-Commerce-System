package com.javaexpress.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaexpress.models.Favourite;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    // Find all favourites by userId
    List<Favourite> findByUserId(Long userId);

    // Find a favourite by userId and productId
    Optional<Favourite> findByUserIdAndProductId(Long userId, Integer productId);
}

