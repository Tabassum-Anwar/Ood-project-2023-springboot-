package bd.edu.diu.swe.meweats.service;

import bd.edu.diu.swe.meweats.config.MyCustomerDetails;
import bd.edu.diu.swe.meweats.model.Customer;
import bd.edu.diu.swe.meweats.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImplement implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Could not find customer");
        }

        return new MyCustomerDetails(customer);
    }

    public Customer getByUserEmail(String email) {
        return customerRepository.getCustomerByUsername(email);
    }
}
