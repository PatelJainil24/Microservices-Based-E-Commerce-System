/*
 * package com.javaexpress.service;
 * 
 * import java.util.List; import java.util.concurrent.CompletableFuture; import
 * java.util.stream.Collectors;
 * 
 * import org.springframework.beans.BeanUtils; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.javaexpress.dto.CartItemRequestDTO; import
 * com.javaexpress.dto.CartItemResponseDTO; import com.javaexpress.dto.UserDto;
 * import com.javaexpress.exception.ResourceNotFoundException; import
 * com.javaexpress.feignsclients.ProductServiceClient; import
 * com.javaexpress.feignsclients.UserFeignClient; import
 * com.javaexpress.model.CartItem; import
 * com.javaexpress.repository.CartItemRepository;
 * 
 * import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
 * import jakarta.transaction.Transactional;
 * 
 * //@Service public class CartServiceImpl_completableFuture implements
 * CartService {
 * 
 * @Autowired private CartItemRepository cartItemRepo;
 * 
 * @Autowired private ProductServiceClient productServiceClient; // Feign or
 * RestTemplate
 * 
 * @Autowired private UserFeignClient userFeignClient;
 * 
 * @Override // @CircuitBreaker(name = "productServiceCB", fallbackMethod = //
 * "handleProductServiceDown") public CartItemResponseDTO
 * addToCart(CartItemRequestDTO request) { validateRequest(request);
 * 
 * CompletableFuture<Boolean> productFuture =
 * checkProductAsync(request.getProductId()); CompletableFuture<UserDto>
 * userFuture = fetchUserAsync(request.getUserId());
 * 
 * CompletableFuture.allOf(productFuture, userFuture).join();
 * 
 * if (!productFuture.join()) { throw new
 * RuntimeException("Product does not exist"); }
 * 
 * CartItem item = new CartItem(); item.setUserId(request.getUserId());
 * item.setProductId(request.getProductId());
 * item.setQuantity(request.getQuantity());
 * 
 * return mapToResponseDTO(cartItemRepo.save(item)); }
 * 
 * public CartItemResponseDTO handleProductServiceDown(CartItemRequestDTO
 * request, Throwable t) { CartItemResponseDTO fallbackResponse = new
 * CartItemResponseDTO(); fallbackResponse.
 * setMessage("Product service is currently unavailable. Showing cached or empty data."
 * ); return fallbackResponse; }
 * 
 * @Override public CartItemResponseDTO updateQuantity(CartItemRequestDTO
 * request) { CartItem item =
 * cartItemRepo.findByUserIdAndProductId(request.getUserId(),
 * request.getProductId()) .orElseThrow(() -> new
 * ResourceNotFoundException("Item not in cart"));
 * 
 * item.setQuantity(request.getQuantity()); return
 * mapToResponseDTO(cartItemRepo.save(item)); }
 * 
 * @Override public List<CartItemResponseDTO> getUserCart(Long userId) { return
 * cartItemRepo.findByUserId(userId).stream().map(this::mapToResponseDTO).
 * collect(Collectors.toList()); }
 * 
 * @Override
 * 
 * @Transactional public void removeItem(Long userId, Long productId) {
 * cartItemRepo.deleteByUserIdAndProductId(userId, productId); }
 * 
 * @Override
 * 
 * @Transactional public void clearCart(Long userId) {
 * cartItemRepo.deleteByUserId(userId); }
 * 
 * private CartItemResponseDTO mapToResponseDTO(CartItem item) {
 * CartItemResponseDTO dto = new CartItemResponseDTO();
 * BeanUtils.copyProperties(item, dto); return dto; }
 * 
 * private void validateRequest(CartItemRequestDTO request) { int quantity =
 * request.getQuantity(); if (quantity <= 0 || quantity > 10) { throw new
 * ResourceNotFoundException("Quantity must be between 1 and 10"); } }
 * 
 * @CircuitBreaker(name = "productServiceCB", fallbackMethod =
 * "productFallback") public CompletableFuture<Boolean> checkProductAsync(Long
 * productId) { return CompletableFuture.supplyAsync(() ->
 * productServiceClient.isProductExists(productId)); }
 * 
 * @CircuitBreaker(name = "userServiceCB", fallbackMethod = "userFallback")
 * public CompletableFuture<UserDto> fetchUserAsync(Long userId) { return
 * CompletableFuture.supplyAsync(() ->
 * userFeignClient.findById(userId.intValue())); }
 * 
 * 
 * public CompletableFuture<Boolean> productFallback(Long productId, Throwable
 * t) { System.err.println("Product Service Fallback: " + t.getMessage());
 * return CompletableFuture.completedFuture(false); // default: product doesn't
 * exist }
 * 
 * 
 * public CompletableFuture<UserDto> userFallback(Long userId, Throwable t) {
 * System.err.println("User Service Fallback: " + t.getMessage()); return
 * CompletableFuture.failedFuture( new
 * RuntimeException("User service is unavailable. Cannot fetch user info.", t)
 * ); }
 * 
 * 
 * 
 * }
 */