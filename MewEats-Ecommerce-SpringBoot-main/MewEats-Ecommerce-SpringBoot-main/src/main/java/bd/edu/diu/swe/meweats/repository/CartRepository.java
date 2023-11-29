package bd.edu.diu.swe.meweats.repository;

import bd.edu.diu.swe.meweats.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findCartById(long id);
    List<Cart> findCartsByCustomerUsername(String username);
    Cart findCartByFoodIdAndCustomerId(long foodId, long userId);
}
