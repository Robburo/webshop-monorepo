import type { UserResponseDto } from "~/types/user";
import type { CartItemResponseDto } from "~/types/cart";
import type { OrderResponseDto } from "~/types/order";
import type { ProductRequestDto } from "~/types/product";
import { apiFetch } from "./apiClient";

export interface ProductResponseDto extends ProductRequestDto {
  id: number;
}

/** Hent alle brukere (ADMIN) */
export async function getAllUsers(): Promise<UserResponseDto[]> {
  return apiFetch<UserResponseDto[]>("/admin/users", { method: "GET" }, true);
}

/** Hent spesifikk bruker (ADMIN) */
export async function getUserById(id: number): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(
    `/admin/users/${id}`,
    { method: "GET" },
    true
  );
}

/** Oppdater bruker (ADMIN) */
export async function updateUser(
  id: number,
  data: UserResponseDto
): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(
    `/admin/users/${id}`,
    {
      method: "PUT",
      body: JSON.stringify(data),
    },
    true
  );
}

/** Slett bruker (ADMIN) */
export async function deleteUser(id: number): Promise<void> {
  return apiFetch<void>(`/admin/users/${id}`, { method: "DELETE" }, true);
}

/** Hent alle handlekurv-items for alle brukere (ADMIN) */
export async function getAllCartItems(): Promise<CartItemResponseDto[]> {
  return apiFetch<CartItemResponseDto[]>(
    "/admin/cart_items",
    { method: "GET" },
    true
  );
}

/** Hent alle ordrer (ADMIN) */
export async function getAllOrders(): Promise<OrderResponseDto[]> {
  return apiFetch<OrderResponseDto[]>("/admin/orders", {}, true);
}

/** Oppdater ordrestatus (ADMIN) */
export async function updateOrderStatus(
  id: number,
  status: string
): Promise<OrderResponseDto> {
  return apiFetch<OrderResponseDto>(
    `/admin/orders/${id}/status?status=${encodeURIComponent(status)}`,
    { method: "PUT" },
    true
  );
}

/** Opprett nytt produkt (ADMIN) */
export async function createProduct(
  data: ProductRequestDto
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    "/admin/products",
    {
      method: "POST",
      body: JSON.stringify(data),
    },
    true
  );
}

/** Oppdater produkt (ADMIN) */
export async function updateProduct(
  id: number,
  data: ProductRequestDto
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    `/admin/products/${id}`,
    {
      method: "PUT",
      body: JSON.stringify(data),
    },
    true
  );
}

/** Slett produkt (ADMIN) */
export async function deleteProduct(id: number): Promise<void> {
  return apiFetch<void>(`/admin/products/${id}`, { method: "DELETE" }, true);
}

/** Oppdater lagerbeholdning (ADMIN) */
export async function updateProductStock(
  id: number,
  stock: number
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    `/admin/products/${id}/stock?stock=${stock}`,
    { method: "PATCH" },
    true
  );
}

/** (valgfritt) Hent salgsstatistikk (ADMIN) */
// export async function getSalesStatistics(from: string, to: string): Promise<any> {
//   return apiFetch<any>(`/admin/statistics/sales?from=${from}&to=${to}`, {}, true)
// }

/** (valgfritt) Hent topp-produkter (ADMIN) */
// export async function getTopProducts(): Promise<any> {
//   return apiFetch<any>('/admin/statistics/top-products', {}, true)
// }
