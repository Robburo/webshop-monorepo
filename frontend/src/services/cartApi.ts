import { apiFetch } from "./apiClient";

// Typedefs som matcher backend DTO-er
export interface CartItemResponseDto {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  userId: number;
}

export interface CartItemCreateDto {
  productId: number;
  quantity: number;
}

/**
 * Hent alle cart items for innlogget bruker
 */
export async function getCartItems(): Promise<CartItemResponseDto[]> {
  return apiFetch<CartItemResponseDto[]>("/cart", {}, true);
}

/**
 * Legg et produkt til handlekurven
 */
export async function addToCart(
  dto: CartItemCreateDto
): Promise<CartItemResponseDto> {
  return apiFetch<CartItemResponseDto>(
    "/cart/add",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    true
  );
}

/**
 * Oppdater antall på et cart item
 */
export async function updateCartItem(
  itemId: number,
  quantity: number
): Promise<CartItemResponseDto> {
  return apiFetch<CartItemResponseDto>(
    `/cart/${itemId}?quantity=${quantity}`,
    { method: "PUT" },
    true
  );
}

/**
 * Fjern et cart item fra handlekurven
 */
export async function removeCartItem(itemId: number): Promise<void> {
  return apiFetch<void>(`/cart/${itemId}`, { method: "DELETE" }, true);
}

/**
 * Tøm hele handlekurven
 */
export async function clearCart(): Promise<void> {
  return apiFetch<void>("/cart/clear", { method: "DELETE" }, true);
}
