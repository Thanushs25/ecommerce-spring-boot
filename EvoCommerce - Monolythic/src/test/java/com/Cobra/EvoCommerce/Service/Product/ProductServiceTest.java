package com.Cobra.EvoCommerce.Service.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.DTO.Product.UpdateProductDTO;
import com.Cobra.EvoCommerce.Facade.ProductMapperFacade;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductManager productManager;

    @Mock
    private ProductMapperFacade productMapperFacade;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private CreateProductDTO createProductDTO;
    private UpdateProductDTO updateProductDTO;
    private Product productToSave;
    private Product savedProduct;
    private ProductRepDTO expectedProductRepDTO;
    private Long productId;
    private Long adminId;
    private String categoryName;
    private String subcategoryName;
    private List<Product> productList;
    private List<ProductRepDTO> productRepDTOList;


    @BeforeEach
    void setUp() {
        // Initialize common test data
        createProductDTO = new CreateProductDTO();
        updateProductDTO = new UpdateProductDTO();
        productToSave = new Product();
        savedProduct = new Product();
        expectedProductRepDTO = new ProductRepDTO();
        productId = 1L;
        adminId = 100L;
        categoryName = "Electronics";
        subcategoryName = "Mobiles";
        productList = Arrays.asList(new Product(), new Product());
        productRepDTOList = Arrays.asList(new ProductRepDTO(), new ProductRepDTO());
    }

    @Test
    void add_product() {
        //When
        when(productMapperFacade.mapToCreateProductEntity(createProductDTO)).thenReturn(productToSave);
        when(productRepository.save(productToSave)).thenReturn(savedProduct);
        when(productMapperFacade.mapToProductRepresentation(savedProduct)).thenReturn(expectedProductRepDTO);

        //Act
        ProductRepDTO actualDTO = productService.add_product(createProductDTO);

        //Assert
        assertEquals(expectedProductRepDTO, actualDTO);
        verify(productMapperFacade, times(1)).mapToCreateProductEntity(createProductDTO);
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapperFacade, times(1)).mapToProductRepresentation(savedProduct);
    }

    @Test
    void update_product() {
        //When
        when(productMapperFacade.mapToUpdateProductEntity(updateProductDTO,productId,adminId)).thenReturn(productToSave);
        when(productRepository.save(productToSave)).thenReturn(savedProduct);
        when(productMapperFacade.mapToProductRepresentation(savedProduct)).thenReturn(expectedProductRepDTO);

        //Act
        ProductRepDTO actualDto = productService.update_product(updateProductDTO,productId,adminId);

        //Assert
        assertEquals(expectedProductRepDTO, actualDto);
        verify(productMapperFacade,times(1)).mapToUpdateProductEntity(updateProductDTO,productId,adminId);
        verify(productRepository,times(1)).save(productToSave);
        verify(productMapperFacade, times(1)).mapToProductRepresentation(savedProduct);
    }

    @Test
    void delete_product() {
        //Given
        boolean expected_result = true;

        //When
        when(productManager.delete_product(productId,adminId)).thenReturn(expected_result);

        //Act
        boolean actualResult = productService.delete_product(productId,adminId);

        //Assert
        assertEquals(expected_result,actualResult);
        verify(productManager,times(1)).delete_product(productId,adminId);

    }

    @Test
    void view_products() {
        //When
        when(productManager.get_products()).thenReturn(productList);
        when(productMapperFacade.mapToProductRepresentation(productList.getFirst())).thenReturn(productRepDTOList.getFirst());
        when(productMapperFacade.mapToProductRepresentation(productList.getLast())).thenReturn(productRepDTOList.getLast());

        //Act
        List<ProductRepDTO> actualRepDTOS = productService.view_products();

        //Assert
        assertEquals(productRepDTOList.size(),actualRepDTOS.size());
        assertEquals(productRepDTOList.get(0), actualRepDTOS.get(0));
        assertEquals(productRepDTOList.get(1), actualRepDTOS.get(1));

        verify(productManager,times(1)).get_products();
        verify(productMapperFacade,times(2)).mapToProductRepresentation(any(Product.class));


    }

    @Test
    void view_products_by_category() {
        //When
        when(productRepository.findByCategory(categoryName)).thenReturn(productList);
        when(productMapperFacade.mapToProductRepresentation(productList.getFirst())).thenReturn(productRepDTOList.getFirst());
        when(productMapperFacade.mapToProductRepresentation(productList.getLast())).thenReturn(productRepDTOList.getLast());

        //Act
        List<ProductRepDTO> productRepDTOS = productService.view_products_by_category(categoryName);

        //Assert
        assertEquals(productRepDTOList.size(),productRepDTOS.size());
        verify(productRepository,times(1)).findByCategory(categoryName);
        verify(productMapperFacade,times(2)).mapToProductRepresentation(any(Product.class));
    }

    @Test
    void view_products_by_category_subcategory() {
        //When
        when(productRepository.findByCategoryAndSubCategory(categoryName,subcategoryName)).thenReturn(productList);
        when(productMapperFacade.mapToProductRepresentation(productList.getFirst())).thenReturn(productRepDTOList.getFirst());
        when(productMapperFacade.mapToProductRepresentation(productList.getLast())).thenReturn(productRepDTOList.getLast());

        //Act
        List<ProductRepDTO> productRepDTOS = productService.view_products_by_category_subcategory(categoryName,subcategoryName);

        //Assert
        assertEquals(productRepDTOList.size(),productRepDTOS.size());
        verify(productRepository,times(1)).findByCategoryAndSubCategory(categoryName,subcategoryName);
        verify(productMapperFacade,times(2)).mapToProductRepresentation(any(Product.class));


    }

    @Test
    void getProductById() {
        //When
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(savedProduct));
        lenient().when(productMapperFacade.mapToProductRepresentation(savedProduct)).thenReturn(expectedProductRepDTO);

        //Act
        ProductRepDTO productRepDTO = productService.getProductById(productId);


        //Assert
        assertEquals(expectedProductRepDTO,productRepDTO);
        verify(productRepository,times(1)).findById(productId);

        verify(productMapperFacade,times(1)).mapToProductRepresentation(savedProduct);



    }
}