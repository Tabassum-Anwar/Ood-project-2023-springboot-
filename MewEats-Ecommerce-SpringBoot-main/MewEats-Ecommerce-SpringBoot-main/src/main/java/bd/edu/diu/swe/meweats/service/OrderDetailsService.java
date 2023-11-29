package bd.edu.diu.swe.meweats.service;

import bd.edu.diu.swe.meweats.model.Cart;
import bd.edu.diu.swe.meweats.model.Orders;
import bd.edu.diu.swe.meweats.model.OrderDetails;
import bd.edu.diu.swe.meweats.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CartService cartService;

    public void save(OrderDetails orderDetails) {
        orderDetailsRepository.save(orderDetails);
    }

    public void saveBulk(List<Cart> carts, Orders order) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrders(order);
        for (Cart cart : carts) {
            orderDetails.setPrice(cart.getFood().getPrice());
            orderDetails.setFood(cart.getFood());
            orderDetails.setQuantity(cart.getQuantity());

            cartService.deleteCart(cart);
            save(orderDetails);
        }
    }
}
