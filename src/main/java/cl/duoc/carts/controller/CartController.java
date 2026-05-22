/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.controller;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/{cartId}")
    @Operation(summary = "Find cart by ID", description = "Retrieves a specific cart using its unique identifier.")
    public ResponseEntity<CartResponse> findCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(service.findById(cartId));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(
            summary = "Find cart by the customer ID",
            description = "Retrieves a specific cart using its customer unique identifier.")
    public ResponseEntity<CartResponse> findCustomerCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.findByCustomer(customerId));
    }

    @PostMapping
    @Operation(
            summary = "Create a new cart",
            description = "Creates a new cart record, replacing any existing customer cart.")
    @ApiResponse(responseCode = "201", description = "Cart created successfully")
    public ResponseEntity<CartResponse> createCart(@Valid @RequestBody CartCreationRequest req) {
        CartResponse res = service.createCart(req);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{cartId}")
                        .buildAndExpand(res.getId())
                        .toUri())
                .body(res);
    }
}
