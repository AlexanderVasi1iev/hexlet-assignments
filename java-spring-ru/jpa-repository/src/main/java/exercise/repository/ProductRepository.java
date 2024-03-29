package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.OptionalInt;

import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceBetweenOrderByPriceAsc(int pricemin, int pricemax);

     // END
}
