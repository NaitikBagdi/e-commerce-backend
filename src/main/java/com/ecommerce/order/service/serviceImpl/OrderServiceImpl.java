package com.ecommerce.order.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.cart.entity.Cart;
import com.ecommerce.cart.repository.CartRepository;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.order.dto.request.CreateOrderRequestDto;
import com.ecommerce.order.dto.response.OrderItemResponseDto;
import com.ecommerce.order.dto.response.OrderResponseDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductVariant;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final CartRepository cartRepository;

	private final ProductRepository productRepository;

	private final UserRepository userRepository;

	@Override
	@Transactional
	public OrderResponseDto placeOrder(CreateOrderRequestDto requestDto, String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<Cart> cartItems = cartRepository.findByUser(user);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}
		List<OrderItem> orderItems = new ArrayList<>();
		double total = 0;
		Order order = Order.builder().user(user)
				.address(requestDto.getAddress())
				.mobileNo(requestDto.getMobileNo())
				.paymentMethod(requestDto.getPaymentMethod())
				.orderDate(LocalDateTime.now())
				.status(OrderStatus.PENDING)
				.orderNumber(UUID.randomUUID().toString()).build();
		for (Cart cart : cartItems) {
			Product product = cart.getProduct();
			ProductVariant variant = cart.getProductVariant();
			double currentPrice;
			int stock;
			if (variant != null) {
				currentPrice = variant.getPrice();
				stock = variant.getStock();
			} else {
				currentPrice = product.getPrice();
				stock = product.getStock();
			}
			
			if (stock < cart.getQuantity()) {
				throw new RuntimeException(product.getName() + " out of stocks ");
			}
			
			OrderItem orderItem = OrderItem.builder()
					.order(order)
					.product(product)
					.productVariant(variant)
					.quantity(cart.getQuantity())
					.price(currentPrice)
					.subtotal(currentPrice*cart.getQuantity())
					.build();

			total += orderItem.getSubtotal(); 
			orderItems.add(orderItem);

			if(variant!=null){
				variant.setStock(variant.getStock() - cart.getQuantity());
			} else {
				product.setStock(product.getStock() - cart.getQuantity());
			}
			order.setTotalAmount(total);
			order.setOrderItems(orderItems);
			orderRepository.save(order);
			cartRepository.deleteAll(cartItems);
		}
		return OrderResponseDto.builder().id(order.getId())
				.orderNumber(order.getOrderNumber())
				.totalAmount(total)
				.paymentMethod(order.getPaymentMethod().name())
				.orderStatus(order.getStatus().name())
				.build();
	}

	@Override
	public List<OrderResponseDto> getMyOrders(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return orderRepository.findByUser(user).stream().map(this::mapToDto).toList();
	}

//	@Override
//	public OrderResponseDto getOrderById(Long orderId, String email) {
//
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//		Order order = orderRepository.findById(orderId)
//				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
//
//		if (!order.getUser().getId().equals(user.getId())) {
//
//			throw new RuntimeException("Unauthorized");
//		}
//
//		return mapToDto(order);
//	}
//
//	@Override
//	@Transactional
//	public void cancelOrder(Long orderId, String email) {
//
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//		Order order = orderRepository.findById(orderId)
//				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
//
//		if (!order.getUser().getId().equals(user.getId())) {
//
//			throw new RuntimeException("Unauthorized");
//		}
//
//		if (order.getStatus() == OrderStatus.CANCELLED) {
//
//			throw new RuntimeException("Order already cancelled");
//		}
//
//		for (OrderItem item : order.getOrderItems()) {
//
//			Product product = item.getProduct();
//
//			product.setStock(product.getStock() + item.getQuantity());
//
//			productRepository.save(product);
//		}
//
//		order.setStatus(OrderStatus.CANCELLED);
//
//		orderRepository.save(order);
//	}
//
	private OrderResponseDto mapToDto(Order order) {
		List<OrderItemResponseDto> items = order.getOrderItems().stream()
				.map(item -> OrderItemResponseDto.builder().productName(item.getProduct().getName())
						.imageUrl("http://localhost:8080/ecommerce/" + item.getProduct().getImages().get(0).getImageUrl())
						.quantity(item.getQuantity()).price(item.getPrice())
						.subtotal(item.getPrice() * item.getQuantity()).build()).toList();
		return OrderResponseDto.builder().id(order.getId())
				.orderNumber(order.getOrderNumber())
				.totalAmount(order.getTotalAmount())
				.paymentMethod(order.getPaymentMethod().name())
				.paymentStatus(order.getPaymentStatus() == null ? null :  order.getPaymentStatus().name())
				.orderStatus(order.getStatus().name()).orderDate(order.getOrderDate()).items(items).build();
	}

//	@Override
//	@Transactional
//	public void updateOrderStatus(Long orderId, OrderStatus status) {
//		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
//		order.setStatus(status);
//		orderRepository.save(order);
//	}
	
//	@Transactional
//	public void updateQuantity(Long cartId, Integer quantity, String email) {
//	    Cart cart = cartRepository.findById(cartId) .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
//	    int availableStock = (cart.getProductVariant() != null) ? cart.getProductVariant().getStock() : cart.getProduct().getStock();
//	    if (quantity > availableStock) {
//	        throw new RuntimeException("Only " + availableStock + " items available");
//	    }
//	    cart.setQuantity(quantity);
//	    cartRepository.save(cart);
//	}
	
}
