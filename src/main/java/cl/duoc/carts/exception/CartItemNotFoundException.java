/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long itemId, Long cartId) {
        String msg = "Cart item with id '" + itemId + "' in cart '" + cartId + "' not found in the DB.";
        log.error(msg);
        super(msg);
    }
}
