/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.controller;

import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Carts", description = "Provides carts CRUD operations.")
public class CartController {
    private final CartService service;

    @GetMapping
    @Operation(summary = "List all carts", description = "Retrieves a full list of all recorded carts in the system.")
    public ResponseEntity<List<CartResponse>> findAllCarts() {
        return ResponseEntity.ok(service.findAll());
    }
}
