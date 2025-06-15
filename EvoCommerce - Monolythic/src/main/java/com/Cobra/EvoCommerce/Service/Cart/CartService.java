package com.Cobra.EvoCommerce.Service.Cart;

import com.Cobra.EvoCommerce.DTO.Cart.CartDto;
import com.Cobra.EvoCommerce.Exception.CartNotFoundException;
import com.Cobra.EvoCommerce.Exception.CustomValidationException;
import com.Cobra.EvoCommerce.Exception.ProductOutOfStockException;
import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Model.Cart.Cart;
import com.Cobra.EvoCommerce.Model.Cart.CartItem;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addToCart(Long productId, Long variantId, Long userId, int quantity) {
        // System.out.println("iehfhefhfiwhfhewh "+userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        ProductVariant variant = null;
        if (variantId != null) {
            variant = product.getProductVariants().stream()
                    .filter(v -> v.getProductVariantId().equals(variantId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("ProductVariant", "id", variantId));
            if (variant.getStock() < quantity) {
                throw new ProductOutOfStockException(
                        "Only " + variant.getStock() + " items of this variant are in stock.");
            }
        }

        Cart shoppingCart = Optional.ofNullable(cartRepo.findByUser_userId(user.getUserId()))
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId) &&
                        (variantId == null ? item.getProductVariant() == null
                                : (item.getProductVariant() != null
                                        && item.getProductVariant().getProductVariantId().equals(variantId))))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;

            if (variantId != null && variant.getStock() < newQuantity) {
                throw new ProductOutOfStockException(
                        "Only " + variant.getStock() + " items of this variant are in stock.");
            }
            double newPrice = variant.getPrice() * newQuantity;
            cartItem.setPriceAtAddition(newPrice);
            cartItem.setQuantity(newQuantity);
            cartItemsRepo.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setProductVariant(variant);
            newCartItem.setImageUrl(product.getImageUrl());
            newCartItem.setQuantity(quantity);
            newCartItem.setIndPrice(variant.getPrice());
            newCartItem.setCart(shoppingCart);
            assert variant != null;
            newCartItem.setPriceAtAddition(variant.getPrice() * quantity);
            cartItemsRepo.save(newCartItem);
            shoppingCart.getCartItems().add(newCartItem);
        }
    }

    private Cart getShoppingCart(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return Optional.ofNullable(cartRepo.findByUser_userId(user.getUserId()))
                .orElseThrow(() -> new CartNotFoundException("Cart", "userId", userId));
    }

    public List<CartDto> displayCartItems(Long userId) {
        Cart shoppingCart = getShoppingCart(userId);

        List<CartDto> cartDtos = new ArrayList<>();

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            CartDto cartDto = new CartDto();
            cartDto.setId(cartItem.getCartItemId());
            cartDto.setUserId(shoppingCart.getUser().getUserId());
            cartDto.setProductName(cartItem.getProduct().getName());
            cartDto.setProductId(cartItem.getProduct().getProductId());
            cartDto.setVariantId(cartItem.getProductVariant().getProductVariantId());
            cartDto.setImageUrl(cartItem.getProduct().getImageUrl());
            cartDto.setQuantity(cartItem.getQuantity());
            cartDto.setIndPrice(cartItem.getIndPrice());
            cartDto.setAmount(cartItem.getPriceAtAddition());
            cartDto.setCartId(cartItem.getCart().getCartId());
            cartDtos.add(cartDto);
        }

        return cartDtos;
    }

    @Transactional
    public CartItem updateCartItemQuantity(Long userId, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemsRepo.findById(cartItemId)
                .orElseThrow(() -> new CartNotFoundException("Cart Item", "id", cartItemId));

        Cart shoppingCart = cartItem.getCart();
        if (!shoppingCart.getUser().getUserId().equals(userId)) {
            throw new CartNotFoundException("This Cart not belong to this user");
        }

        Product product = cartItem.getProduct();
        ProductVariant variant = cartItem.getProductVariant();

        if (variant != null) {
            if (variant.getStock() < quantity) {
                throw new ProductOutOfStockException(
                        "Only " + variant.getStock() + " items of this variant are in stock.");
            }
        }

        cartItem.setQuantity(quantity);
        cartItem.setPriceAtAddition(quantity * variant.getPrice());
        return cartItemsRepo.save(cartItem);
    }

    @Transactional
    public void removeCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemsRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item", "id", cartItemId));
        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new CartNotFoundException("This Cart not belong to this user");
        }
        cartItemsRepo.delete(cartItem);
    }

    @Transactional
    public void clearShoppingCart(Long userId) {
        cartRepo.deleteAllByUser_userId(userId);
    }

}