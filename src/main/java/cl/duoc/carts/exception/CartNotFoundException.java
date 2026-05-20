/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long cartId) {
        String msg = "Cart with id '" + cartId + "' not found in the DB.";
        log.error(msg);
        super(msg);
    }
}
