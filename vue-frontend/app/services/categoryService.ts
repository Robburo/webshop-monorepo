import type { CategoryCreateDto, CategoryResponseDto } from "~/types/category";
import { apiFetch } from "./apiClient";

/** Hent alle kategorier */
export async function getAllCategories(): Promise<CategoryResponseDto[]> {
  return apiFetch<CategoryResponseDto[]>("/categories");
}

/** Hent kategori med ID */
export async function getCategoryById(
  id: number
): Promise<CategoryResponseDto> {
  return apiFetch<CategoryResponseDto>(`/categories/${id}`);
}

/** Opprett ny kategori (ADMIN only) */
export async function createCategory(
  dto: CategoryCreateDto
): Promise<CategoryResponseDto> {
  return apiFetch<CategoryResponseDto>(
    "/categories",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    true
  );
}

/** Slett kategori (ADMIN only) */
export async function deleteCategory(id: number): Promise<void> {
  return apiFetch<void>(`/categories/${id}`, { method: "DELETE" }, true);
}
