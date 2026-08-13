package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.exceptions.CantPayException;
import dev.lumah.orders_ms.exceptions.InactiveProductException;
import dev.lumah.orders_ms.exceptions.InactiveUserException;
import dev.lumah.orders_ms.exceptions.InsufficientStockException;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.OrderStatus;
import dev.lumah.orders_ms.model.Product;
import dev.lumah.orders_ms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	User validateUser(String id) {
		User user = userService.findUser(id);
		if (!Boolean.TRUE.equals(user.getActive())) {
			throw new InactiveUserException();
		}
		return user;
	}

	Product validateProduct(String id, Integer quantity) {
		Product product = productService.findProduct(id);
		if (!Boolean.TRUE.equals(product.getActive())) {
			throw new InactiveProductException();
		}
		if (quantity > product.getStock()) {
			throw new InsufficientStockException();
		}
		return product;
	}

	Order validateOrder(String id) {
		Order order = orderService.findOrder(id);
		if(order.getStatus() != OrderStatus.PAYMENT_PENDING){
			throw new CantPayException();
		}
		return order;
	}
}
