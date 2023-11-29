package bd.edu.diu.swe.meweats.controller;

import bd.edu.diu.swe.meweats.model.Food;
import bd.edu.diu.swe.meweats.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        if (principal == null)
            return "redirect:/login";

        model.addAttribute("title", "Mew Eats Food Ecommerce");
        model.addAttribute("food", new Food());
        model.addAttribute("foods", foodService.getAll());
        return "index";
    }
}
