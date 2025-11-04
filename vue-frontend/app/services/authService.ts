import type { TokenPairResponse } from "~/types/auth";
import { apiFetch } from "./apiClient";

/** Logg inn med username/password og hent JWT-token */
export async function login(
  username: string,
  password: string
): Promise<TokenPairResponse> {
  const basicAuth = btoa(`${username}:${password}`);

  return apiFetch<TokenPairResponse>(
    "/auth/token",
    {
      method: "POST",
      headers: {
        Authorization: `Basic ${basicAuth}`,
      },
    },
    false // åpent endepunkt, ingen JWT
  );
}
