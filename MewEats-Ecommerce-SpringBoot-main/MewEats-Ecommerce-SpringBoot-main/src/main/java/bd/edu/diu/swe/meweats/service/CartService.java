package bd.edu.diu.swe.meweats.service;

import bd.edu.diu.swe.meweats.model.Cart;
import bd.edu.diu.swe.meweats.model.Food;
import bd.edu.diu.swe.meweats.model.Customer;
import bd.edu.diu.swe.meweats.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> listAll(String username) {
        return (List<Cart>) cartRepository.findCartsByCustomerUsername(username);
    }

    public Cart get(long id) {
        return cartRepository.findCartById(id);
    }

    public void addToCart(Food food, Customer customer) {

        Cart cart = null;

        cart = cartRepository.findCartByFoodIdAndCustomerId(food.getId(), customer.getId());

        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setFood(food);
            cart.setQuantity(1);
        } else {
            cart.setQuantity(cart.getQuantity()+1);
        }

        cartRepository.save(cart);
    }

    public void updateCart(long cartId, int quantity) {
        Cart cart = getById(cartId);
        cart.setQuantity(quantity);

        cartRepository.save(cart);
    }

    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }

    Cart getById(long id) {
        return cartRepository.findCartById(id);
    }
}
