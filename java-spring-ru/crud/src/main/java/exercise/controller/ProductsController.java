package exercise.controller;

import java.util.List;
import java.util.Optional;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.dto.CategoryDTO;
import exercise.mapper.CategoryMapper;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.repository.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    // BEGIN

    @GetMapping(path = "")
    public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO create(@Valid @RequestBody ProductCreateDTO productData) {
        var product = productMapper.map(productData);
       // product.setAuthor(userUtils.getCurrentUser());
        productRepository.save(product);
        var productDTO = productMapper.map(product);
        return productDTO;
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO show(@PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var productDTO = productMapper.map(product);
        return productDTO;
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO update(@RequestBody @Valid ProductUpdateDTO productData, @PathVariable Long id) {
        Optional<Category> Cat = categoryRepository.findById(productData.getCategoryId().get());
           var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        productMapper.update(productData, product);
        product.setCategory(Cat.get());
        productRepository.save(product);
        var productDTO = productMapper.map(product);
        return productDTO;
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
      void destroy(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    // END
}
