/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartItemCreationRequest;
import cl.duoc.carts.dto.request.CartStatusUpdateRequest;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.dto.response.NonDetailsCartResponse;
import cl.duoc.carts.exception.CartNotFoundException;
import cl.duoc.carts.mapper.DtoModelMapper;
import cl.duoc.carts.model.Cart;
import cl.duoc.carts.model.CartItem;
import cl.duoc.carts.model.CartStatus;
import cl.duoc.carts.repository.CartItemRepository;
import cl.duoc.carts.repository.CartRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepo;

    @Mock
    private CartItemRepository itemRepo;

    @Mock
    private DtoModelMapper mapper;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("usuario@test.cl", null, List.of()));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private Cart crearCart() {
        return Cart.builder()
                .id(1L)
                .customer(10L)
                .status(CartStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private CartItem crearItem(Cart cart) {
        return CartItem.builder()
                .id(1L)
                .cart(cart)
                .product(100L)
                .quantity(2)
                .price(5000)
                .build();
    }

    @Test
    void createCart_debeCrearCarroYEliminarCarroAnteriorDelCliente() {
        // GIVEN.
        CartCreationRequest request = new CartCreationRequest(10L);
        Cart existingCart = crearCart();
        Cart newCart = Cart.builder().customer(10L).status(CartStatus.ACTIVE).build();
        Cart savedCart = crearCart();
        NonDetailsCartResponse response = NonDetailsCartResponse.builder()
                .id(1L)
                .customer(10L)
                .status("ACTIVE")
                .build();

        when(cartRepo.findByCustomer(10L)).thenReturn(Optional.of(existingCart));
        when(mapper.cartFromCreationRequest(request)).thenReturn(newCart);
        when(cartRepo.save(newCart)).thenReturn(savedCart);
        when(mapper.toNonDetailsCartResponse(savedCart)).thenReturn(response);

        // WHEN.
        NonDetailsCartResponse result = cartService.createCart(request);

        // THEN.
        assertThat(result).isNotNull();
        assertThat(result.getCustomer()).isEqualTo(10L);
        assertThat(result.getStatus()).isEqualTo("ACTIVE");
        verify(cartRepo).delete(existingCart);
        verify(cartRepo).save(newCart);
    }

    @Test
    void findById_debeRetornarCarroConItemsCuandoExiste() {
        // GIVEN.
        Cart cart = crearCart();
        CartItem item = crearItem(cart);
        CartResponse response = CartResponse.builder()
                .id(1L)
                .customer(10L)
                .status("ACTIVE")
                .items(List.of())
                .build();

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(itemRepo.findByCartId(1L)).thenReturn(List.of(item));
        when(mapper.toCartResponse(cart, List.of(item))).thenReturn(response);

        // WHEN.
        CartResponse result = cartService.findById(1L);

        // THEN.
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomer()).isEqualTo(10L);
        verify(itemRepo).findByCartId(1L);
    }

    @Test
    void findById_debeLanzarExcepcionCuandoCarroNoExiste() {
        // GIVEN.
        when(cartRepo.findById(99L)).thenReturn(Optional.empty());

        // WHEN + THEN.
        assertThatThrownBy(() -> cartService.findById(99L)).isInstanceOf(CartNotFoundException.class);
        verify(itemRepo, never()).findByCartId(any());
    }

    @Test
    void updateCartStatus_debeActualizarEstadoCuandoExiste() {
        // GIVEN.
        Cart cart = crearCart();
        CartStatusUpdateRequest request = new CartStatusUpdateRequest(CartStatus.CHECKED_OUT);
        NonDetailsCartResponse response = NonDetailsCartResponse.builder()
                .id(1L)
                .customer(10L)
                .status("CHECKED_OUT")
                .build();

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(mapper.toNonDetailsCartResponse(cart)).thenReturn(response);

        // WHEN.
        NonDetailsCartResponse result = cartService.updateCartStatus(1L, request);

        // THEN.
        assertThat(result.getStatus()).isEqualTo("CHECKED_OUT");
        assertThat(cart.getStatus()).isEqualTo(CartStatus.CHECKED_OUT);
        assertThat(cart.getUpdatedAt()).isNotNull();
    }

    @Test
    void addItem_debeAgregarItemAlCarroCuandoExiste() {
        // GIVEN.
        Cart cart = crearCart();
        CartItemCreationRequest request = new CartItemCreationRequest(100L, 2, 5000);
        CartItem item = crearItem(cart);
        CartResponse response = CartResponse.builder()
                .id(1L)
                .customer(10L)
                .status("ACTIVE")
                .items(List.of())
                .build();

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(mapper.cartItemFromCreationRequest(cart, request)).thenReturn(item);
        when(cartRepo.saveAndFlush(cart)).thenReturn(cart);
        when(mapper.toCartResponse(cart, cart.getItems())).thenReturn(response);

        CartResponse result = cartService.addItem(1L, request);

        assertThat(result).isNotNull();
        assertThat(cart.getItems()).containsExactly(item);
        assertThat(cart.getUpdatedAt()).isNotNull();
        verify(cartRepo).saveAndFlush(cart);
    }
}
