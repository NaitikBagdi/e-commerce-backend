package com.ecommerce.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.cart.entity.Cart;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductVariant;
import com.ecommerce.user.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long>  {

	List<Cart> findByUser(User user);

	Optional<Cart> findByUserAndProduct(User user, Product product);

	Optional<Cart> findByUserAndProductAndProductVariant(User user, Product product, ProductVariant productVariant);

	void deleteByUser(User user);
}
