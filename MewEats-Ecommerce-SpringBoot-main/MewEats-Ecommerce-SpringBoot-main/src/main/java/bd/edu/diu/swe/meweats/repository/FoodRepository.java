package bd.edu.diu.swe.meweats.repository;

import bd.edu.diu.swe.meweats.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {
    Food getFoodById(long id);
}
