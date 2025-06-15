package com.Cobra.EvoCommerce.Controller.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.DTO.Product.UpdateProductDTO;
import com.Cobra.EvoCommerce.Service.Product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/manage")
@RequiredArgsConstructor
public class ProductManagingController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRepDTO add_product(@RequestBody CreateProductDTO dto){
        return productService.add_product(dto);
    }

    @PatchMapping("/updateProduct/{productId}/admin/{adminId}")
    public ProductRepDTO updateProduct(@RequestBody UpdateProductDTO updateProductDTO, @PathVariable
    Long productId, @PathVariable Long adminId ){
        return productService.update_product(updateProductDTO,productId,adminId);
    }

    @DeleteMapping("/deleteProduct/{productId}/admin/{adminId}")
    public boolean deleteProduct(@PathVariable Long productId,@PathVariable Long adminId){
        return productService.delete_product(productId,adminId);
    }

    @GetMapping("/allProducts/{adminId}")
    public List<ProductRepDTO> getAllProduct(@PathVariable Long adminId){
        return productService.view_all_product(adminId);
    }


}
