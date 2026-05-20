/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.controller;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartUpdateRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Carts", description = "Provides carts CRUD operations.")
public class CartController {
    private final CartService service;

    @GetMapping("/{id}")
    @Operation(summary = "Find cart by ID", description = "Retrieves a specific cart using its unique identifier.")
    public ResponseEntity<CartResponse> findCart(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "List all carts", description = "Retrieves a full list of all recorded carts in the system.")
    public ResponseEntity<List<CartResponse>> findAllCarts() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(summary = "Create a new cart", description = "Persists a new cart record into the database.")
    public ResponseEntity<CartResponse> saveCart(@Valid @RequestBody CartCreationRequest req) {
        return ResponseEntity.ok(service.saveCart(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Replace an existing cart", description = "Replaces an existing cart record matching the id.")
    public ResponseEntity<CartResponse> replaceCart(@PathVariable Long id, @Valid @RequestBody CartUpdateRequest req) {
        return ResponseEntity.ok(service.replaceCart(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing cart", description = "Delete a cart matching id record from the database.")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        service.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
