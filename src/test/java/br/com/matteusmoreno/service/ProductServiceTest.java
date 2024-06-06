package br.com.matteusmoreno.service;

import br.com.matteusmoreno.domain.Product;
import br.com.matteusmoreno.domain.Supplier;
import br.com.matteusmoreno.repository.ProductRepository;
import br.com.matteusmoreno.request.CreateProductRequest;
import br.com.matteusmoreno.request.UpdateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private Long id;
    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        id = 1L;
        product = new Product(id, "Product", "Product description", new BigDecimal("100"), new BigDecimal("200"),
                "Nike", 10, LocalDateTime.now(), null, null, true);
    }

    @Test
    @DisplayName("Should create a product and save it to the repository")
    void shouldCreateProductAndSaveToRepository() {

        CreateProductRequest request = new CreateProductRequest("PRODUCT", "Product description",
                new BigDecimal("100"), new BigDecimal("200"), "Nike", 10);

        Product result = productService.createProduct(request);
        result.setId(id);

        verify(productRepository, times(1)).save(result);

        assertEquals(1L, result.getId());
        assertEquals(request.name(), result.getName());
        assertEquals(request.description(), result.getDescription());
        assertEquals(request.purchasePrice(), result.getPurchasePrice());
        assertEquals(request.salePrice(), result.getSalePrice());
        assertEquals(request.manufacturer(), result.getManufacturer());
        assertEquals(request.quantity(), result.getQuantity());
        assertNotNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should return product details by ID")
    void shouldReturnProductDetailsById() {

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = Optional.ofNullable(productService.productDetails(id));

        verify(productRepository, times(1)).findById(id);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when product ID not found")
    void shouldThrowNoSuchElementExceptionWhenProductIdNotFound() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.productDetails(id);
        });

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should update product and save it to the repository")
    void shouldUpdateProductAndSaveToRepository() {

        UpdateProductRequest request = new UpdateProductRequest(id, "NEW NAME", "New description",
                new BigDecimal("300.00"), new BigDecimal("400.00"), "Puma", 100);

        when(productRepository.findById(request.id())).thenReturn(Optional.ofNullable(product));

        Product result = productService.updateProduct(request);

        verify(productRepository, times(1)).findById(request.id());
        verify(productRepository, times(1)).save(result);

        assertEquals(request.id(), result.getId());
        assertEquals(request.name(), result.getName());
        assertEquals(request.description(), result.getDescription());
        assertEquals(request.purchasePrice(), result.getPurchasePrice());
        assertEquals(request.salePrice(), result.getSalePrice());
        assertEquals(request.manufacturer(), result.getManufacturer());
        assertEquals(request.quantity(), result.getQuantity());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when updating a product with invalid ID")
    void shouldThrowProductNotFoundExceptionWhenUpdatingProductWithInvalidId() {
        UpdateProductRequest request = new UpdateProductRequest(id, "New name", "New description",
                new BigDecimal("300.00"), new BigDecimal("400.00"), "Puma", 100);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.updateProduct(request);
        });

        verify(productRepository, times(1)).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Should disable a product and mark them as deleted")
    void shouldDisableProductAndMarkAsDeleted() {

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.disableProduct(id);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
        assertFalse(product.getActive());
        assertNotNull(product.getDeletedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when disabling a product that does not exist")
    void shouldThrowNoSuchElementExceptionWhenDisablingNonExistentProduct() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.disableProduct(id);
        });

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).save(any(Product.class));
    }


    @Test
    @DisplayName("Should enable a product and mark them as updated")
    void shouldEnableProductAndMarkAsUpdated() {

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.enableProduct(id);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(result);

        assertTrue(result.getActive());
        assertNull(result.getDeletedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when enabling a product that does not exist")
    void shouldThrowNoSuchElementExceptionWhenEnablingNonExistentProduct() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productService.enableProduct(id);
        });

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).save(any(Product.class));
    }
}