package com.Cobra.EvoCommerce.Mapper.Cart;

import com.Cobra.EvoCommerce.DTO.Cart.CartDto;
import com.Cobra.EvoCommerce.Model.Cart.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart entity);

    List<CartDto> toDtoList(List<Cart> entities);

    Cart toEntity(CartDto dto);
}



