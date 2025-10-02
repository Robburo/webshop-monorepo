package webshop.backend.domains.order.dto;


public record CheckoutOrderDto(
        String recipientName,
        String street,
        String postalCode,
        String city,
        String country
) {}