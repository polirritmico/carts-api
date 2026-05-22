/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerCartNotFoundException extends RuntimeException {
    public CustomerCartNotFoundException(Long customerId) {
        String msg = "Customer with id '" + customerId + "' do not have a cart in the DB.";
        log.error(msg);
        super(msg);
    }
}
