package bd.edu.diu.swe.meweats.controller;

import bd.edu.diu.swe.meweats.model.Customer;
import bd.edu.diu.swe.meweats.repository.CustomerRepository;
import bd.edu.diu.swe.meweats.service.CustomerDetailsServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    private CustomerDetailsServiceImplement userDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal, Model model) {
        if (principal != null) return "redirect:/";
        model.addAttribute("title", "Mew Eats Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("title", "Mew Eats Food - Registration");
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customer") Customer customer,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes attributes) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("customer", customer);
                return "register";
            }

            Customer customerNew = userDetailsService.getByUserEmail(customer.getUsername());

            if (customerNew != null) {
                model.addAttribute("error", "Email is already registered");
                model.addAttribute("customer", customer);
                return "register";
            }

            if (customer.getName().trim() == null) {
                model.addAttribute("error", "Name can not be empty");
                model.addAttribute("customer", customer);
                return "register";
            }

            if (customer.getPassword().length() >= 5 && customer.getPassword().length() <= 20) {
                customer.setPassword(passwordEncoder.encode(customer.getPassword()));
                customerRepository.save(customer);
                attributes.addFlashAttribute("success", customer.getName() + " is successfully registered.");

                return "redirect:/login";
            } else {
                model.addAttribute("error", "Password should have 5-20 characters");
                model.addAttribute("customer", customer);
            }
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Server have ran some problems");
            model.addAttribute("customer", customer);
        }
        return "register";
    }
}
