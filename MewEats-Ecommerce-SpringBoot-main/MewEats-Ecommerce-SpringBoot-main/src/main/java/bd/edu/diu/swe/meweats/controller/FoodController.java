package bd.edu.diu.swe.meweats.controller;

import bd.edu.diu.swe.meweats.model.Food;
import bd.edu.diu.swe.meweats.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping("/food/new")
    public String foodSave(@ModelAttribute("food") Food food,
                              RedirectAttributes attributes) {
        if (food.getName() == null || food.getPrice() == 0 || food.getImage() == null || food.getCategory() == null) {
            attributes.addFlashAttribute("error", "Value cannot be null");
            return "redirect:/";
        }

        foodService.save(food);
        attributes.addFlashAttribute("success", "Food Successfully Added");
        return "redirect:/";
    }

    @GetMapping("/food/edit/{id}")
    public String foodEdit(@PathVariable String id,
                              Model model, Principal principal) {

        if (principal == null) return "redirect:/login";

        Food food = foodService.get(Long.parseLong(id));
        model.addAttribute("foodEdit", food);
        model.addAttribute("food", new Food());

        return "edit_food";
    }

    @PostMapping("/food/edit/save")
    public String foodEditSave(@ModelAttribute("foodEdit") Food foodEdit,
                                  Principal principal, RedirectAttributes attributes) {
        if (principal == null) return "redirect:/login";

        Food food = foodService.get(foodEdit.getId());

        food.setCategory(foodEdit.getCategory());
        food.setName(foodEdit.getName());
        food.setImage(foodEdit.getImage());
        food.setDescription(foodEdit.getDescription());
        food.setPrice(foodEdit.getPrice());

        foodService.save(food);
        attributes.addFlashAttribute("success", "Food edit successful");
        return "redirect:/";
    }


    @GetMapping("/food/delete/{id}")
    public String foodEdit(@PathVariable String id,
                              Model model, Principal principal,
                              RedirectAttributes attributes) {

        if (principal == null) return "redirect:/login";
        foodService.delete(Long.parseLong(id));
        attributes.addFlashAttribute("success", "Food delete successful");
        return "redirect:/";
    }
}
