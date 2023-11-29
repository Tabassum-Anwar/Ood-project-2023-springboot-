package bd.edu.diu.swe.meweats.controller;

import bd.edu.diu.swe.meweats.model.Cart;
import bd.edu.diu.swe.meweats.model.Customer;
import bd.edu.diu.swe.meweats.model.Food;
import bd.edu.diu.swe.meweats.service.CartService;
import bd.edu.diu.swe.meweats.service.CustomerDetailsServiceImplement;
import bd.edu.diu.swe.meweats.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CustomerDetailsServiceImplement userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String cart(Model model,
                       Principal principal) {

        if (principal == null) return "redirect:/login";

        List<Cart> carts = cartService.listAll(principal.getName());

        double totalPrice = carts.stream()
                .mapToDouble(cart -> cart.getFood().getPrice() * cart.getQuantity())
                .sum();

        model.addAttribute("carts", carts);
        model.addAttribute("title", "Cart");
        model.addAttribute("food", new Food());
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }


    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Long foodId,
            Principal principal,
            RedirectAttributes attributes){

        if (principal == null) return "redirect:/login";

        Food food = foodService.get(foodId);
        Customer customer = userService.getByUserEmail(principal.getName());
        cartService.addToCart(food, customer);

        attributes.addFlashAttribute("success", "Food added successfully");
        return "redirect:/cart";
    }

    @GetMapping(value = "/delete-cart/{id}")
    public String deleteItemFromCart(Principal principal, @PathVariable String id){
        if(principal == null){
            return "redirect:/login";
        }else{
            cartService.deleteCart(cartService.get(Long.parseLong(id)));
            return "redirect:/cart";
        }
    }

    @PostMapping(value = "/update-cart")
    public String updateItemFromCart(@RequestParam("id") Long cartId,
                                     @RequestParam("quantity") int quantity,
                                     Principal principal){
        if(principal == null){
            return "redirect:/login";
        }else{
            cartService.updateCart(cartId, quantity);
            return "redirect:/cart";
        }
    }
}
