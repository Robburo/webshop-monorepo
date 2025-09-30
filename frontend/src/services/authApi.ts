import { apiFetch } from "./apiClient";

interface TokenResponse {
  token: string;
}

/**
 * Logg inn med username/password og hent JWT-token
 */
export async function login(username: string, password: string): Promise<string> {
  const basicAuth = btoa(`${username}:${password}`);

  // kall apiClient og forvent et objekt { token: string }
  const data = await apiFetch<TokenResponse>(
    "/auth/token",
    {
      method: "POST",
      headers: {
        Authorization: `Basic ${basicAuth}`,
      },
    },
    false // ingen JWT, kun Basic Auth
  );

  return data.token;
}
