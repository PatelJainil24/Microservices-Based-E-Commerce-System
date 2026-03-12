package com.javaexpress.service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.ProductRequestDTO;
import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.ProductStockUpdateDTO;
import com.javaexpress.exception.ProductNotFoundException;
import com.javaexpress.models.Product;
import com.javaexpress.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Override
	public ProductResponseDTO addProduct(ProductRequestDTO request) {
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setStatus("ACTIVE");

		Product saved = productRepo.save(product);
		return mapToDTO(saved);
	}

	@Override
	public ProductResponseDTO getProductById(Long productId) {
		return productRepo.findById(productId).map(product -> mapToDTO(product))
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}

	@Override
	public BigDecimal getPriceById(Long productId) {
		return productRepo.findById(productId).map(Product::getPrice)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		return productRepo.findAll().stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
	}

	@Override
	public void updateStock(List<ProductStockUpdateDTO> updates) {
		for (ProductStockUpdateDTO dto : updates) {
			Product product = productRepo.findById(dto.getProductId())
					.orElseThrow(() -> new ProductNotFoundException("Product not found with ID " + dto.getProductId()));

			if (product.getStock() < dto.getQuantity()) {
				throw new RuntimeException("Insufficient stock for product ID " + dto.getProductId());
			}

			product.setStock(product.getStock() - dto.getQuantity());
			productRepo.save(product);
		}
	}

	private ProductResponseDTO mapToDTO(Product product) {
		ProductResponseDTO dto = new ProductResponseDTO();
		BeanUtils.copyProperties(product, dto);
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		// dto.setImageData(product.getImage());
		if (product.getImage() != null) {
			String base64Image = Base64.getEncoder().encodeToString(product.getImage());
			String imageDataUrl = "data:image/jpeg;base64," + base64Image;
			dto.setImageData(imageDataUrl);
		}
		return dto;
	}

	public boolean isProductExists(Long productId) {
		return productRepo.existsById(productId);
	}

	@Override
	public void addProductImage(ProductRequestDTO request) {
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		BeanUtils.copyProperties(request, product);

		if (request.getImage() != null && !request.getImage().isEmpty()) {
			try {
				product.setImage(request.getImage().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productRepo.save(product);
	}

	@Override
	public void editProductImage(Long productId, ProductRequestDTO request) {
		Optional<Product> optionalProduct = productRepo.findById(productId);

		if (!optionalProduct.isPresent()) {
			throw new RuntimeException("Product with ID " + productId + " not found.");
		}

		Product product = optionalProduct.get();

		// Update fields
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());

		// Update image only if new image is sent
		if (request.getImage() != null && !request.getImage().isEmpty()) {
			try {
				product.setImage(request.getImage().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to update product image", e);
			}
		}

		productRepo.save(product);
	}

	@Override
	public ProductResponseDTO getProductByIdImage(Long productId) {
		Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

		ProductResponseDTO dto = new ProductResponseDTO();
		BeanUtils.copyProperties(product, dto);

		// Convert byte[] to Base64 string
		if (product.getImage() != null) {
			String base64Image = Base64.getEncoder().encodeToString(product.getImage());
			String imageDataUrl = "data:image/jpeg;base64," + base64Image;
			dto.setImageData(imageDataUrl); // assuming you have setImageData() in DTO
		}
		return dto;
	}

	@Override
	public List<ProductResponseDTO> getAllProductsWithImages() {
		List<Product> products = productRepo.findAll();

		return products.stream().filter(a -> a.getStatus().equals("ACTIVE")).map(product -> {
			ProductResponseDTO dto = new ProductResponseDTO();
			BeanUtils.copyProperties(product, dto);

			if (product.getImage() != null) {
				String base64Image = Base64.getEncoder().encodeToString(product.getImage());
				String imageDataUrl = "data:image/jpeg;base64," + base64Image;
				dto.setImageData(imageDataUrl);
			}

			return dto;
		}).collect(Collectors.toList());
	}

	public List<ProductResponseDTO> searchProducts(String keyword) {
		return productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword).stream()
				.filter(a -> a.getStatus().equals("ACTIVE")).map(this::mapToDTO) // method reference for clarity
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductResponseDTO> getAllProductsFilter(Specification<Product> spec) {

		List<Product> filteredProducts = productRepo.findAll(spec);
		// Convert to DTO
		return filteredProducts.stream().map(this::mapToDTO).collect(Collectors.toList());

	}

}
