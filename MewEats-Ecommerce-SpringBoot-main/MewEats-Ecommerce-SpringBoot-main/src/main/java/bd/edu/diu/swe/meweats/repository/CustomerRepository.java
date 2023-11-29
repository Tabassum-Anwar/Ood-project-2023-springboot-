package bd.edu.diu.swe.meweats.repository;

import bd.edu.diu.swe.meweats.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer getCustomerByUsername(String username);
}
