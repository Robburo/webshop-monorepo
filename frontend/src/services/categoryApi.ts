import { apiFetch } from "./apiClient";

// Typedefs fra backend DTO-er
export interface CategoryResponseDto {
  id: number;
  name: string;
}

export interface CategoryCreateDto {
  name: string;
}

/**
 * Hent alle kategorier
 */
export async function getAllCategories(): Promise<CategoryResponseDto[]> {
  return apiFetch<CategoryResponseDto[]>("/categories");
}

/**
 * Hent en kategori med id
 */
export async function getCategoryById(id: number): Promise<CategoryResponseDto> {
  return apiFetch<CategoryResponseDto>(`/categories/${id}`);
}

/**
 * Opprett en ny kategori (ADMIN only)
 */
export async function createCategory(dto: CategoryCreateDto): Promise<CategoryResponseDto> {
  return apiFetch<CategoryResponseDto>(
    "/categories",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    true // krever auth
  );
}

/**
 * Slett en kategori (ADMIN only)
 */
export async function deleteCategory(id: number): Promise<void> {
  return apiFetch<void>(`/categories/${id}`, { method: "DELETE" }, true);
}
