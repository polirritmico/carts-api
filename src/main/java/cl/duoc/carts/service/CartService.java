/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.service;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.exception.CartNotFoundException;
import cl.duoc.carts.exception.CustomerCartNotFoundException;
import cl.duoc.carts.mapper.DtoModelMapper;
import cl.duoc.carts.repository.CartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepo;

    private final DtoModelMapper mapper;

    public List<CartResponse> findAll() {
        return cartRepo.findAll().stream().map(mapper::toCartResponse).toList();
    }

    private void logRequest(String msg) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(msg + " by user " + auth.getName());
    }

    public CartResponse findById(Long id) {
        logRequest("Starting findById with id: " + id);
        return cartRepo.findById(id).map(mapper::toCartResponse).orElseThrow(() -> new CartNotFoundException(id));
    }

    public CartResponse findByCustomer(Long customerId) {
        logRequest("Starting findByCustomer with customer id: " + customerId);
        return mapper.toCartResponse(
                cartRepo.findByCustomer(customerId).orElseThrow(() -> new CustomerCartNotFoundException(customerId)));
    }

    public CartResponse createCart(CartCreationRequest req) {
        logRequest("Starting createCart with customer id: " + req.getCustomerId());
        return mapper.toCartResponse(cartRepo.save(mapper.cartFromCreationRequest(req)));
    }
}
