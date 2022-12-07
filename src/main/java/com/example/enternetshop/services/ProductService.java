package com.example.enternetshop.services;


import com.example.enternetshop.dto.ProductDto;
import com.example.enternetshop.entity.Product;
import com.example.enternetshop.exceptions.ResourceNotFoundException;
import com.example.enternetshop.repositories.ProductsRepository;
import com.example.enternetshop.repositories.specifications.ProductsSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepository productsRepository;

    public Page<Product> findAll(
            Integer minPrice,
            Integer maxPrice,
            String partTitle,
            String categoryName,
            Integer page) {

        Specification<Product> spec = Specification.where(null);

        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryName != null) {
            if (!categoryName.equals("")) {
                spec = spec.and(ProductsSpecifications.categoryEqual(categoryName));
            }
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 8));

    }

    public Optional<Product> findByID (Long id) {
        return productsRepository.findById(id);
    }

    public Product save (Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update (ProductDto productDto) {
        Long id = productDto.getId();
        Product product = findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить. Продукт с id = " + id + " не найден."));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    }


    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

}

