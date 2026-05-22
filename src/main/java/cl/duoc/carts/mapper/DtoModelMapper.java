/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.mapper;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartItemCreationRequest;
import cl.duoc.carts.dto.response.CartItemResponse;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.dto.response.NonDetailsCartResponse;
import cl.duoc.carts.model.Cart;
import cl.duoc.carts.model.CartItem;
import cl.duoc.carts.model.CartStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoModelMapper {

    public NonDetailsCartResponse toNonDetailsCartResponse(Cart cart) {
        return NonDetailsCartResponse.builder()
                .id(cart.getId())
                .customer(cart.getCustomer())
                .status(cart.getStatus().name())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public CartItemResponse toCartItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

    public CartResponse toCartResponse(Cart cart, List<CartItem> items) {
        return CartResponse.builder()
                .id(cart.getId())
                .customer(cart.getCustomer())
                .status(cart.getStatus().name())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .items(items.stream().map(this::toCartItemResponse).toList())
                .build();
    }

    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .customer(cart.getCustomer())
                .status(cart.getStatus().name())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .items(cart.getItems().stream().map(this::toCartItemResponse).toList())
                .build();
    }

    public Cart cartFromCreationRequest(CartCreationRequest req) {
        return Cart.builder()
                .customer(req.getCustomerId())
                .status(CartStatus.ACTIVE)
                .build();
    }

    public CartItem cartItemFromCreationRequest(Cart cart, CartItemCreationRequest req) {
        return CartItem.builder()
                .cart(cart)
                .product(req.getProductId())
                .quantity(req.getQuantity())
                .price(req.getPrice())
                .build();
    }

    // public Cart saleFromUpdateRequest(CartUpdateRequest req) {
    //     return Cart.builder()
    //             .customerId(req.getCustomerId())
    //             .createdAt(LocalDateTime.now())
    //             .build();
    // }

}
