package com.matheus.commerce.services;

import com.matheus.commerce.domain.Product;
import com.matheus.commerce.dto.product.ProductDto;
import com.matheus.commerce.enums.CategoryEnum;
import com.matheus.commerce.infra.exceptions.ProductNotFoundException;
import com.matheus.commerce.repository.ProductRepository;
import com.matheus.commerce.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setup() {
        productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("Should return all products")
    public void shouldReturnAllProducts() {
        List<Product> products = new ArrayList<>(List.of(new Product(), new Product()));
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> list = productService.findAll();
        Assertions.assertEquals(list.size(), products.size());
        Assertions.assertEquals(list.get(0).getId(), products.get(0).getId());
        Assertions.assertEquals(list.get(1).getId(), products.get(1).getId());
    }

    @Test
    @DisplayName("Should create a product")
    public void shouldCreateProduct() {
        Assertions.assertDoesNotThrow(() -> productService.create(new ProductDto("Casa", "Casa", 23032, "", 4.5, "Caixa", "Casa", Set.of("Black"), Set.of(CategoryEnum.fashion),Set.of(1))));
    }

    @Test
    @DisplayName("Should throw NullPointerException when creating")
    public void shouldThrowNullPointerExceptionWhenCreating() {
        Assertions.assertThrows(NullPointerException.class, () -> productService.create(null));
    }

    @Test
    @DisplayName("Should return all when delete product")
    public void shouldReturnAllWhenDelete() {
        List<Product> products = new ArrayList<>(List.of(new Product(), new Product()));
        Mockito.when(productRepository.findAll()).thenReturn(products);
        productService.delete("1");
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when deleting product")
    public void shouldThrowProductNotFoundExceptionWhenDeletingProduct() {
        Mockito.doThrow(IllegalArgumentException.class).when(productRepository).deleteById(null);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.delete(null));
    }

}
