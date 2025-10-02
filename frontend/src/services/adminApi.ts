import { apiFetch } from "./apiClient";

// --- Typedefs ---
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

export interface OrderResponseDto {
  id: number;
  createdAt: string;
  status: string;
  items: { id: number; productName: string; quantity: number; price: number }[];
}

export interface ProductRequestDto {
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId?: number;
}

export interface ProductResponseDto extends ProductRequestDto {
  id: number;
}

// --- Eksisterende metoder beholdes ---
// getAllUsers, getUserById, deleteUser, getAllCartItems

// --- Nye metoder ---

/** Oppdater en bruker (ADMIN-only) */
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

/** Hent alle ordre (ADMIN-only) */
export async function getAllOrders(): Promise<OrderResponseDto[]> {
  return apiFetch<OrderResponseDto[]>("/admin/orders", {}, true);
}

/** Oppdater ordrestatus (ADMIN-only) */
export async function updateOrderStatus(
  id: number,
  status: string
): Promise<OrderResponseDto> {
  return apiFetch<OrderResponseDto>(
    `/admin/orders/${id}/status?status=${status}`,
    {
      method: "PUT",
    },
    true
  );
}

/** Opprett nytt produkt (ADMIN-only) */
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

/** Oppdater produkt (ADMIN-only) */
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

/** Slett produkt (ADMIN-only) */
export async function deleteProduct(id: number): Promise<void> {
  return apiFetch<void>(`/admin/products/${id}`, { method: "DELETE" }, true);
}

/** Oppdater lagerbeholdning (ADMIN-only) */
export async function updateProductStock(
  id: number,
  stock: number
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    `/admin/products/${id}/stock?stock=${stock}`,
    {
      method: "PATCH",
    },
    true
  );
}

/** Hent salgsstatistikk (ADMIN-only) */
/*
export async function getSalesStatistics(from: string, to: string): Promise<any> {
  return apiFetch<any>(`/admin/statistics/sales?from=${from}&to=${to}`, {}, true);
}
*/

/** Hent topp-produkter (ADMIN-only) */
/*
export async function getTopProducts(): Promise<any> {
  return apiFetch<any>("/admin/statistics/top-products", {}, true);
}
*/