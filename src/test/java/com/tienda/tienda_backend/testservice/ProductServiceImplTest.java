package com.tienda.tienda_backend.testservice;

import com.tienda.tienda_backend.entity.Product;
import com.tienda.tienda_backend.repository.ProductRepository;
import com.tienda.tienda_backend.service.ProductServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_returnsAllFromRepository() {
        Product p1 = new Product();
        p1.setId(1L);
        Product p2 = new Product();
        p2.setId(2L);

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2).containsExactly(p1, p2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void createProduct_savesAndReturnsProduct() {
        Product product = new Product();
        product.setName("Torta Prueba");
        product.setPrice(10000.0);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertThat(result.getName()).isEqualTo("Torta Prueba");
        assertThat(result.getPrice()).isEqualTo(10000.0);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductById_whenExists_returnsProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Torta");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Torta");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_whenNotFound_throwsException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Producto no encontrado");

        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void updateProduct_updatesFieldsAndSaves() {
        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Viejo");
        existing.setPrice(1000.0);
        existing.setDescription("Desc vieja");

        Product updated = new Product();
        updated.setName("Nuevo");
        updated.setPrice(2000.0);
        updated.setDescription("Desc nueva");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product result = productService.updateProduct(1L, updated);

        assertThat(result.getName()).isEqualTo("Nuevo");
        assertThat(result.getPrice()).isEqualTo(2000.0);
        assertThat(result.getDescription()).isEqualTo("Desc nueva");
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    void deleteProduct_callsRepositoryDeleteById() {
        Long id = 1L;

        productService.deleteProduct(id);

        verify(productRepository, times(1)).deleteById(id);
    }
}
