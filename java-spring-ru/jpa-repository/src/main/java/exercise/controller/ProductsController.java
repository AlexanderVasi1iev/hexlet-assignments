package exercise.controller;

import org.hibernate.annotations.EmbeddableInstantiator;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import org.springframework.data.domain.Sort;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> index  (@RequestParam(name ="min",defaultValue = "0", required = false) String min, @RequestParam(name ="max",defaultValue = "200",required = false) String max) {  //(@RequestParam String min, String max) {
        if (!min.equals("0") && !max.equals("200"))
        {
            return productRepository.findByPriceBetweenOrderByPriceAsc(Integer.parseInt(min), Integer.parseInt(max));
        }
        if (min.equals("0") && !max.equals("200"))
        {
            return productRepository.findByPriceBetweenOrderByPriceAsc(Integer.parseInt(min), Integer.parseInt(max));
        }
        if (!min.equals("0") && max.equals("200"))
        {
            return productRepository.findByPriceBetweenOrderByPriceAsc(Integer.parseInt(min), Integer.parseInt(max));
        }
else
       return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
     //   if (min.isPresent() & max.isEmpty() ) { return productRepository.findByPriceBetween(min,1000);}
    }


    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
