package com.javaexpress.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.CartItemDTO;
import com.javaexpress.dto.OrderItemResponseDTO;
import com.javaexpress.dto.OrderResponseDTO;
import com.javaexpress.dto.PlaceOrderRequestDTO;
import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.ProductStockUpdateDTO;
import com.javaexpress.dto.UserDto;
import com.javaexpress.feignsclients.CartServiceClient;
import com.javaexpress.feignsclients.ProductServiceClient;
import com.javaexpress.feignsclients.UserFeignClient;
import com.javaexpress.model.Order;
import com.javaexpress.model.OrderItem;
import com.javaexpress.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartServiceClient cartClient;

    @Autowired
    private ProductServiceClient productClient;

    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private UserFeignClient userFeignClient;
    
    @Autowired
    private ProductIntegrationService productIntegrationService;

    @Override
    public OrderResponseDTO placeOrder(PlaceOrderRequestDTO request) {
    	validateUser(request.getUserId());
    	
        List<CartItemDTO> cartItems = fetchCartItems(request.getUserId());
        validateCartItems(cartItems);

        BigDecimal total = calculateTotalPrice(cartItems);
        List<OrderItem> orderItems = buildOrderItems(cartItems);
        //List<ProductStockUpdateDTO> stockUpdates = prepareStockUpdates(cartItems);

        Order order = createOrder(request.getUserId(), total, orderItems);
        Order savedOrder = orderRepo.save(order);

        //updateProductStock(stockUpdates);
        //clearUserCart(request.getUserId());

        return mapToOrderResponse(savedOrder);
    }

    private UserDto validateUser(Long userId) {
    	log.info("User Id {}",userId);
        try {
            return userFeignClient.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    
    private List<CartItemDTO> fetchCartItems(Long userId) {
        return cartClient.getCart(userId);
    }

    private void validateCartItems(List<CartItemDTO> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
    }

    private BigDecimal calculateTotalPrice(List<CartItemDTO> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            BigDecimal price = productClient.getProductImage(item.getProductId()).getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    private List<OrderItem> buildOrderItems(List<CartItemDTO> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDTO item : cartItems) {
            BigDecimal price = productClient.getProductImage(item.getProductId()).getPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(price);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private List<ProductStockUpdateDTO> prepareStockUpdates(List<CartItemDTO> cartItems) {
        return cartItems.stream()
                .map(item -> new ProductStockUpdateDTO(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    private Order createOrder(Long userId, BigDecimal total, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setStatus("PLACED");

        for (OrderItem item : orderItems) {
            item.setOrder(order); // Bi-directional mapping
        }

        order.setItems(orderItems);
        return order;
    }

    private void updateProductStock(List<ProductStockUpdateDTO> stockUpdates) {
        productClient.updateStock(stockUpdates);
    }

    private void clearUserCart(Long userId) {
        cartClient.clearCart(userId);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        return orderRepo.findById(orderId)
                .map(this::mapToOrderResponse)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId).stream()
                .map(this::mapToOrderResponse)
                .sorted(Comparator.comparing(OrderResponseDTO::getOrderId).reversed())
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToOrderResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        // Copy simple properties
        dto.setOrderId(order.getId());
        BeanUtils.copyProperties(order, dto, "items"); // Exclude items to map manually

        // Manually map the items list
        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {
            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
            ProductResponseDTO product = productIntegrationService.fetchProductDetails(item.getProductId());
            itemDto.setProduct(product);
            BeanUtils.copyProperties(item, itemDto);
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }


    @Override
    public void updateOrderStatus(Long orderId, String status) {
        // Step 1: Fetch the order by ID
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Step 2: Validate if the status is allowed
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        // Step 3: Update the order status
        order.setStatus(status);
        orderRepo.save(order);  // Save the updated order

        // Step 4: Handle business logic based on the new status
        if (status.equals("SHIPPED")) {
            // Notify shipping-service or perform actions related to shipping
           
        }
        // Additional business logic for other statuses can be added here (e.g., "DELIVERED", "CANCELLED")
    }

    private boolean isValidStatus(String status) {
        // Define valid statuses
        List<String> validStatuses = Arrays.asList("PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED","PLACED");
        return validStatuses.contains(status);
    }
    
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll(); // or a custom query
        return orders.stream()
        				.map(this::mapToOrderResponse)
                     .collect(Collectors.toList());
    }
    
}

