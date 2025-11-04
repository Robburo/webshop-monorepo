import type { ProductRequestDto, ProductResponseDto } from "~/types/product";
import { apiFetch } from "./apiClient";

/** Hent alle produkter */
export async function getAllProducts(): Promise<ProductResponseDto[]> {
  return apiFetch<ProductResponseDto[]>("/products");
}

/** Hent et produkt med ID */
export async function getProductById(id: number): Promise<ProductResponseDto> {
  return apiFetch<ProductResponseDto>(`/products/${id}`);
}

/** Opprett nytt produkt (ADMIN only) */
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

/** Oppdater eksisterende produkt (ADMIN only) */
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

/** Slett et produkt (ADMIN only) */
export async function deleteProduct(id: number): Promise<void> {
  return apiFetch<void>(`/products/${id}`, { method: "DELETE" }, true);
}