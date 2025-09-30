import { apiFetch } from "./apiClient";

// Typedefs fra backend DTO-er
export interface UserRequestDto {
  username: string;
  email: string;
  password: string;
  role?: string; // optional, settes ofte av backend
}

export interface UserResponseDto {
  id: number;
  username: string;
  email: string;
  role: string;
}

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
