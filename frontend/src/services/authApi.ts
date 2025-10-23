import { apiFetch } from "./apiClient";

interface TokenPairResponse {
  token: string;
  refreshToken: string;
}

/**
 * Logg inn med username/password og hent JWT-token
 */
export async function login(username: string, password: string): Promise<TokenPairResponse> {
  const basicAuth = btoa(`${username}:${password}`);

  const data = await apiFetch<TokenPairResponse>(
    "/auth/token",
    {
      method: "POST",
      headers: {
        Authorization: `Basic ${basicAuth}`,
      },
    },
    false // ingen JWT, kun Basic Auth
  );

  return data;
}