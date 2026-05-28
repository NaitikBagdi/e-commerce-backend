package com.ecommerce.cart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.cart.dto.request.AddToCartRequestDto;
import com.ecommerce.cart.dto.response.CartResponseDto;
import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.mapper.CartMapper;
import com.ecommerce.cart.repository.CartRepository;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductVariant;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.repository.ProductVariantRepository;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final ProductVariantRepository productVariantRepository;
	private final UserRepository userRepository;
	private final CartMapper cartMapper;

	@Override
	public void addToCart(String email, AddToCartRequestDto request) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		Double price;
	    ProductVariant variant = null;
		if(!product.getVariants().isEmpty()) {
			variant = productVariantRepository.findById(request.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            price = variant.getPrice();
		} else {
			price = product.getPrice();
		}
		Optional<Cart> isCartExist = cartRepository.findByUserAndProductAndProductVariant(user, product, variant);
		if (isCartExist.isPresent()) {
			Cart cart = isCartExist.get();
			cart.setQuantity(request.getQuantity() + cart.getQuantity());
			cartRepository.save(cart);
			return;
		}
		Cart cart = Cart.builder().user(user).product(product).productVariant(variant).quantity(request.getQuantity()).price(price).build();
		cartRepository.save(cart);
	}

	@Override
	public List<CartResponseDto> getCart(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return cartRepository.findByUser(user) .stream().map(cartMapper::toDto) .toList();
	}

	@Override
	public void removeCartItem(Long cartId, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
		if (!cart.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Unauthorized");
		}
		cartRepository.delete(cart);
	}

	@Override
	public void clearCart(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<Cart> carts = cartRepository.findByUser(user);
		cartRepository.deleteAll(carts);
	}

	@Override
	@Transactional
	public void updateQuantity(Long cartId, Integer quantity, String email) {
	    Cart cart = cartRepository.findById(cartId) .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

	    int availableStock = (cart.getProductVariant() != null) ? cart.getProductVariant().getStock() : cart.getProduct().getStock();
	    if (quantity > availableStock) {
	        throw new RuntimeException("Only " + availableStock + " items available");
	    }
	    cart.setQuantity(quantity);
	    cartRepository.save(cart);
	}

	@Override
	@Transactional
	public void syncGuestCart(String name, @Valid List<AddToCartRequestDto> requests) {
		for (AddToCartRequestDto dto : requests) {
			this.addToCart(name, dto);
		}
		
	}
}