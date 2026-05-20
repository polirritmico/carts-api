/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequest {
    @NotNull(message = "El carro debe estar asociada a un cliente")
    @Positive(message = "La id del usuario no puede ser negativa")
    private Long customerId;

    @PastOrPresent(message = "La fecha de borrado no puede ser en el futuro")
    private LocalDateTime deletedAt;
}
