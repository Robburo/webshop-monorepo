import { apiFetch } from "./apiClient";

// Typedefs for Admin-data
export interface UserResponseDto {
  id: number;
  username: string;
  email: string;
  role: string;
}

export interface CartItemResponseDto {
  id: number;
  productId: number;
  productName: string;
  quantity: number;
  userId: number;
}

/**
 * Hent alle brukere (ADMIN-only)
 */
export async function getAllUsers(): Promise<UserResponseDto[]> {
  return apiFetch<UserResponseDto[]>("/admin/users", {}, true);
}

/**
 * Hent en spesifikk bruker (ADMIN-only)
 */
export async function getUserById(id: number): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(`/admin/users/${id}`, {}, true);
}

/**
 * Slett en bruker (ADMIN-only)
 */
export async function deleteUser(id: number): Promise<void> {
  return apiFetch<void>(`/admin/users/${id}`, { method: "DELETE" }, true);
}

/**
 * Hent alle cart items for alle brukere (ADMIN-only)
 */
export async function getAllCartItems(): Promise<CartItemResponseDto[]> {
  return apiFetch<CartItemResponseDto[]>("/admin/cart_items", {}, true);
}
