/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.mapper;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartUpdateRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.model.Cart;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoModelMapper {
    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .deletedAt(cart.getDeletedAt())
                .build();
    }

    public Cart cartFromCreationRequest(CartCreationRequest req) {
        return Cart.builder()
                .customerId(req.getCustomerId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Cart saleFromUpdateRequest(CartUpdateRequest req) {
        return Cart.builder()
                .customerId(req.getCustomerId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
