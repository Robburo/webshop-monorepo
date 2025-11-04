import { apiFetch } from "./apiClient";
import type { OrderDto, OrderItemDto } from "~/types/order";

/** Opprett ny ordre (checkout) */
export async function checkout(data: {
  recipientName: string;
  street: string;
  postalCode: string;
  city: string;
  country: string;
}): Promise<OrderDto> {
  return apiFetch<OrderDto>(
    "/orders/checkout",
    {
      method: "POST",
      body: JSON.stringify(data),
      headers: { "Content-Type": "application/json" },
    },
    true
  );
}

/** Hent alle ordrer for innlogget bruker */
export async function getOrdersForUser(): Promise<OrderDto[]> {
  return apiFetch<OrderDto[]>("/orders", {}, true);
}

/** Hent spesifikk ordre */
export async function getOrderById(id: number): Promise<OrderDto> {
  return apiFetch<OrderDto>(`/orders/${id}`, {}, true);
}

/** Oppdater status (ADMIN eller autoriserte roller) */
export async function updateOrderStatus(
  id: number,
  status: string
): Promise<OrderDto> {
  return apiFetch<OrderDto>(
    `/orders/${id}/status?status=${encodeURIComponent(status)}`,
    { method: "PUT" },
    true
  );
}

/** Hent alle order items for en ordre */
export async function getItemsByOrder(
  orderId: number
): Promise<OrderItemDto[]> {
  return apiFetch<OrderItemDto[]>(`/order-items/order/${orderId}`, {}, true);
}

/** Hent ett order item */
export async function getOrderItemById(id: number): Promise<OrderItemDto> {
  return apiFetch<OrderItemDto>(`/order-items/${id}`, {}, true);
}
