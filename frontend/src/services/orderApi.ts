import { apiFetch } from "./apiClient";

// Typedefs fra backend DTO-er
export interface OrderItemDto {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface OrderDto {
  id: number;
  userId: number;
  createdAt: string; // ISO datetime
  status: string;

  recipientName: string;
  street: string;
  postalCode: string;
  city: string;
  country: string;

  items: OrderItemDto[];
}

/**
 * Opprett en ny ordre basert på handlekurven (checkout)
 */
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

/**
 * Hent alle ordrer for innlogget bruker
 */
export async function getOrdersForUser(): Promise<OrderDto[]> {
  return apiFetch<OrderDto[]>("/orders", {}, true);
}

/**
 * Hent en spesifikk ordre
 */
export async function getOrderById(id: number): Promise<OrderDto> {
  return apiFetch<OrderDto>(`/orders/${id}`, {}, true);
}

/**
 * Oppdater status på en ordre (ADMIN eller spesifikke roller)
 */
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

/**
 * Hent alle order items for en ordre
 */
export async function getItemsByOrder(
  orderId: number
): Promise<OrderItemDto[]> {
  return apiFetch<OrderItemDto[]>(`/order-items/order/${orderId}`, {}, true);
}

/**
 * Hent et enkelt order item
 */
export async function getOrderItemById(id: number): Promise<OrderItemDto> {
  return apiFetch<OrderItemDto>(`/order-items/${id}`, {}, true);
}
