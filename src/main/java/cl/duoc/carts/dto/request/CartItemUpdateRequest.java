/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemUpdateRequest {
    @NotNull(message = "La id del producto es obligatoria")
    @Positive(message = "La id del producto debe ser positiva")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer quantity;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo mayor a 0")
    private Integer price;
}
