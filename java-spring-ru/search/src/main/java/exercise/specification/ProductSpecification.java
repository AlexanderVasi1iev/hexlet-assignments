package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component // Для возможности автоматической инъекции
public class ProductSpecification {
    // Генерация спецификации на основе параметров внутри DTO
    // Для удобства каждый фильтр вынесен в свой метод
    public Specification<Product> build(ProductParamsDTO params) {
        return  withtitleCont(params.getTitleCont())
                .and(withcategoryId(params.getCategoryId()))
                .and(withPriceGt(params.getPriceGt()))
                        .and(withPriceLt(params.getPriceLt()))
                                .and(withRatingGt(params.getRatingGt()));
    }

    private Specification<Product> withtitleCont(String title) {
        return (root, query, cb) -> title == null ? cb.conjunction() : cb.like(root.get("title"),title);
    }

    private Specification<Product> withcategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }

    private Specification<Product> withPriceGt(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("price"), price);
    }
    private Specification<Product> withPriceLt(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.lessThan(root.get("price"), price);
    }

    private Specification<Product> withRatingGt(Double rating) {
        return (root, query, cb) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
    }
    // Остальные методы
}
// END
