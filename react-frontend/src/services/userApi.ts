import { apiFetch } from "./apiClient";
import { UserRequestDto, UserResponseDto } from "@/types/User";

/**
 * Registrer en ny bruker (åpent endepunkt)
 */
export async function registerUser(
  dto: UserRequestDto
): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(
    "/users/register",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    false // åpent endepunkt
  );
}

/**
 * Hent innlogget bruker (krever JWT)
 */
export async function getCurrentUser(): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>("/users/me", {}, true);
}

/**
 * Hent alle brukere (typisk ADMIN)
 */
export async function getAllUsers(): Promise<UserResponseDto[]> {
  return apiFetch<UserResponseDto[]>("/users", {}, true);
}

/**
 * Hent bruker på ID (typisk ADMIN)
 */
export async function getUserById(id: number): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(`/users/${id}`, {}, true);
}
