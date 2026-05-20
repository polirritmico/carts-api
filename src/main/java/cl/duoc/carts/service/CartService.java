/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.service;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartUpdateRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.exception.CartNotFoundException;
import cl.duoc.carts.mapper.DtoModelMapper;
import cl.duoc.carts.model.Cart;
import cl.duoc.carts.repository.CartRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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

    public CartResponse findById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Starting findById with id: " + id + " by user: " + auth.getName());
        return cartRepo.findById(id).map(mapper::toCartResponse).orElseThrow(() -> new CartNotFoundException(id));
    }

    public List<CartResponse> findAll() {
        return cartRepo.findAllActive().stream().map(mapper::toCartResponse).toList();
    }

    public CartResponse saveCart(CartCreationRequest req) {
        Cart newCart = cartRepo.save(mapper.cartFromCreationRequest(req));
        return mapper.toCartResponse(newCart);
    }

    @Transactional
    public CartResponse replaceCart(Long id, CartUpdateRequest req) {
        Cart updatedCart = cartRepo.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        updatedCart.setCustomerId(req.getCustomerId());
        updatedCart.setUpdatedAt(LocalDateTime.now());
        return mapper.toCartResponse(updatedCart);
    }

    @Transactional
    public void deleteCart(Long id) {
        cartRepo.findById(id).orElseThrow(() -> new CartNotFoundException(id)).setDeletedAt(LocalDateTime.now());
    }
}
