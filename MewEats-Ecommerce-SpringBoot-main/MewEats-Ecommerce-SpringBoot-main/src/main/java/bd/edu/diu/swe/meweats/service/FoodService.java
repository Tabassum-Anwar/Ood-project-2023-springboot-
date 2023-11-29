package bd.edu.diu.swe.meweats.service;

import bd.edu.diu.swe.meweats.model.Food;
import bd.edu.diu.swe.meweats.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAll() {
        return (List<Food>) foodRepository.findAll();
    }

    public Food get(long id) {
        return foodRepository.getFoodById(id);
    }

    public void save(Food food) {
        foodRepository.save(food);
    }

    public void delete(long id) {
        foodRepository.delete(get(id));
    }

}
