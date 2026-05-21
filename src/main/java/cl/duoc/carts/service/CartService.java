/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.service;

import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.mapper.DtoModelMapper;
import cl.duoc.carts.repository.CartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
