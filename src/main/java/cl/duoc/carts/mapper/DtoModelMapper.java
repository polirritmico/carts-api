/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.mapper;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.model.Cart;
import cl.duoc.carts.model.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoModelMapper {

    public CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .customer(cart.getCustomer())
                .status(cart.getStatus().name())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public Cart cartFromCreationRequest(CartCreationRequest req) {
        return Cart.builder()
                .customer(req.getCustomerId())
                .status(CartStatus.ACTIVE)
                .build();
    }
}
