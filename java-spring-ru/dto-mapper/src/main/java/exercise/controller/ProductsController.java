package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;


        @Autowired
        private ProductMapper productMapper;

        @PostMapping("")
        @ResponseStatus(HttpStatus.CREATED)
        public ProductDTO create(@RequestBody ProductCreateDTO productData) {
            // Преобразование в сущность
            var product = productMapper.map(productData);
            productRepository.save(product);
            // Преобразование в DTO
            var productDTO = productMapper.map(product);
            return productDTO;
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ProductDTO update(@RequestBody ProductUpdateDTO productData, @PathVariable Long id) {
            var product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
            productMapper.update(productData, product);
            productRepository.save(product);
            var productDTO = productMapper.map(product);
            return productDTO;
        }

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ProductDTO show(@PathVariable Long id) {
            var product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
            // Преобразование в DTO
            var productDTO = productMapper.map(product);
            return productDTO;
        }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
       public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .toList();
        }
    private ProductDTO toDTO(Product product) {
        var dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getName());
        dto.setPrice(product.getCost());
        dto.setVendorCode(product.getBarcode());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    // END
}
