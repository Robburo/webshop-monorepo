import type { UserRequestDto, UserResponseDto } from "@/types/user";
import { apiFetch } from "./apiClient";

/** Registrer ny bruker (åpent endepunkt) */
export async function registerUser(
  dto: UserRequestDto
): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(
    "/users/register",
    {
      method: "POST",
      body: JSON.stringify(dto),
    },
    false // ikke auth
  );
}

/** Hent innlogget bruker (krever JWT) */
export async function getCurrentUser(): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>("/users/me", {}, true);
}

/** Hent alle brukere (ADMIN) */
export async function getAllUsers(): Promise<UserResponseDto[]> {
  return apiFetch<UserResponseDto[]>("/users", {}, true);
}

/** Hent bruker på ID (ADMIN) */
export async function getUserById(id: number): Promise<UserResponseDto> {
  return apiFetch<UserResponseDto>(`/users/${id}`, {}, true);
}
