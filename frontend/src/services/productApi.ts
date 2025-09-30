import { apiFetch } from "./apiClient";

// Typedefs fra backend DTO-er
export interface ProductResponseDto {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
  categoryName: string;
}

export interface ProductRequestDto {
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
}

/**
 * Hent alle produkter
 */
export async function getAllProducts(): Promise<ProductResponseDto[]> {
  return apiFetch<ProductResponseDto[]>("/products");
}

/**
 * Hent et produkt med id
 */
export async function getProductById(id: number): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(`/products/${id}`);
}

/**
 * Opprett et nytt produkt (ADMIN only)
 */
export async function createProduct(
  dto: ProductRequestDto
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    "/products",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    true
  );
}

/**
 * Oppdater et produkt (ADMIN only)
 */
export async function updateProduct(
  id: number,
  dto: ProductRequestDto
): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(
    `/products/${id}`,
    {
      method: "PUT",
      body: JSON.stringify(dto),
    },
    true
  );
}

/**
 * Slett et produkt (ADMIN only)
 */
export async function deleteProduct(id: number): Promise<void> {
  return apiFetch<void>(`/products/${id}`, { method: "DELETE" }, true);
}
