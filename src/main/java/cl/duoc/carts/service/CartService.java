/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.carts.service;

import cl.duoc.carts.dto.request.CartCreationRequest;
import cl.duoc.carts.dto.request.CartItemCreationRequest;
import cl.duoc.carts.dto.request.CartItemUpdateRequest;
import cl.duoc.carts.dto.request.CartStatusUpdateRequest;
import cl.duoc.carts.dto.response.CartItemResponse;
import cl.duoc.carts.dto.response.CartResponse;
import cl.duoc.carts.dto.response.NonDetailsCartResponse;
import cl.duoc.carts.exception.CartItemNotFoundException;
import cl.duoc.carts.exception.CartNotFoundException;
import cl.duoc.carts.exception.CustomerCartNotFoundException;
import cl.duoc.carts.mapper.DtoModelMapper;
import cl.duoc.carts.model.Cart;
import cl.duoc.carts.model.CartItem;
import cl.duoc.carts.repository.CartItemRepository;
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
    private final CartItemRepository itemRepo;

    private final DtoModelMapper mapper;

    private void logRequest(String msg) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(msg + " by user " + auth.getName());
    }

    public List<NonDetailsCartResponse> findAll() {
        return cartRepo.findAll().stream().map(mapper::toNonDetailsCartResponse).toList();
    }

    public List<CartItemResponse> findAllItems() {
        return itemRepo.findAll().stream().map(mapper::toCartItemResponse).toList();
    }

    public CartResponse findById(Long id) {
        logRequest("Starting findById with id: " + id);
        return cartRepo.findById(id)
                .map(cart -> {
                    List<CartItem> items = itemRepo.findByCartId(cart.getId());
                    return mapper.toCartResponse(cart, items);
                })
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    public CartResponse findByCustomer(Long customerId) {
        logRequest("Starting findByCustomer with customer id: " + customerId);
        return cartRepo.findByCustomer(customerId)
                .map(cart -> {
                    List<CartItem> items = itemRepo.findByCartId(cart.getId());
                    return mapper.toCartResponse(cart, items);
                })
                .orElseThrow(() -> new CustomerCartNotFoundException(customerId));
    }

    public NonDetailsCartResponse createCart(CartCreationRequest req) {
        logRequest("Starting createCart with customer id: " + req.getCustomerId());
        cartRepo.findByCustomer(req.getCustomerId()).ifPresent(cartRepo::delete);
        return mapper.toNonDetailsCartResponse(cartRepo.save(mapper.cartFromCreationRequest(req)));
    }

    @Transactional
    public NonDetailsCartResponse updateCartStatus(Long cartId, CartStatusUpdateRequest req) {
        logRequest("Starting updateCartStatus with cart id: " + cartId);
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setStatus(req.getStatus());
        return mapper.toNonDetailsCartResponse(cart);
    }

    @Transactional
    public CartResponse addItem(Long cartId, CartItemCreationRequest req) {
        logRequest("Starting addItem with cart id: " + cartId);
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        cart.getItems().add(mapper.cartItemFromCreationRequest(cart, req));
        cart.setUpdatedAt(LocalDateTime.now());
        return mapper.toCartResponse(cartRepo.saveAndFlush(cart), cart.getItems());
    }

    @Transactional
    public CartResponse updateItem(Long cartId, Long itemId, CartItemUpdateRequest req) {
        logRequest("Starting updateItem with cart id: " + cartId);
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        CartItem item = itemRepo.findByIdAndCartId(itemId, cartId)
                .orElseThrow(() -> new CartItemNotFoundException(itemId, cartId));

        cart.setUpdatedAt(LocalDateTime.now());
        item.setProduct(req.getProductId());
        item.setQuantity(req.getQuantity());
        item.setPrice(req.getPrice());

        return mapper.toCartResponse(cart);
    }

    @Transactional
    public void deleteCart(Long id) {
        logRequest("Starting deleteCart with cart id: " + id);
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        cartRepo.delete(cart);
    }

    @Transactional
    public void deleteAllCartItems(Long id) {
        logRequest("Starting deleteAllCartItems with cart id: " + id);
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public CartResponse deleteCartItem(Long cartId, Long itemId) {
        logRequest("Starting deleteCartItems with cart id: " + cartId + " and item id: " + itemId);
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        if (!cart.getItems().removeIf(item -> item.getId().equals(itemId))) {
            throw new CartItemNotFoundException(itemId, cartId);
        }
        return mapper.toCartResponse(cart);
    }
}
