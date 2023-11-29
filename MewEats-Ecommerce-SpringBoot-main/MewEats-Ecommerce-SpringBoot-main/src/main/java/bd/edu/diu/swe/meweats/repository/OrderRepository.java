package bd.edu.diu.swe.meweats.repository;

import bd.edu.diu.swe.meweats.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {
}
