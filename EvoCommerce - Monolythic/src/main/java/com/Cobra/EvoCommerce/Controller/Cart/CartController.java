package com.Cobra.EvoCommerce.Controller.Cart;

import com.Cobra.EvoCommerce.DTO.Cart.CartDto;
import com.Cobra.EvoCommerce.Model.Cart.Cart;
import com.Cobra.EvoCommerce.Model.Cart.CartItem;
import com.Cobra.EvoCommerce.Service.Cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/carts")
public class CartController {

    @Autowired
    private ICartService shoppingICartService;

    @PostMapping("addCart/products/{productId}/variant/{variantId}/users/{userId}")
    public void addToCart(@PathVariable Long productId, @PathVariable Long variantId, @PathVariable Long userId, @RequestParam int quantity)
    {
//        System.out.println("iehfhefhfiwhfhewh "+userId);
        shoppingICartService.addToCart(productId,variantId,userId,quantity);
    }

    @GetMapping("/cartItems/{userId}")
    public List<CartDto> getCartItems(@PathVariable Long userId)
    {
        return shoppingICartService.displayCartItems(userId);
    }

//    @GetMapping("/cartItems")
//    public List<Cart> displayCart()
//    {
//        return shoppingICartService.displayCart();
//    }


    @PutMapping("/updateCart/user/{userId}/cart/{itemId}")
    public CartItem updateCartItems(@PathVariable Long userId, @PathVariable Long itemId , @RequestParam int quantity)
    {
        return shoppingICartService.updateCartItemQuantity(userId,itemId,quantity);
    }


    @DeleteMapping("/deleteCartItem/user/{userId}/cart/{itemId}")
    public void deleteItem(@PathVariable Long userId,@PathVariable Long itemId)
    {
        shoppingICartService.removeCartItem(userId,itemId);
    }

    @DeleteMapping("/deleteCart/user/{userId}")
    public void deleteCart(@PathVariable Long userId)
    {
        shoppingICartService.clearShoppingCart(userId);
    }


//    @GetMapping("/total_price/{userid}")
//    public double totalPriceByUser(@PathVariable Long userId)
//    {
//         return shoppingICartService.totalPriceByUser(userId);
//    }

}
