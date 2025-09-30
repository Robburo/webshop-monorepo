// Standard base-URL for backend-API.
// Bruk NEXT_PUBLIC_API_URL i .env.local hvis du kjører i ulike miljøer.
const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";

/**
 * apiFetch - felles wrapper rundt fetch som alle API-kall bruker.
 *
 * @param endpoint    - f.eks. "/products" eller "/cart/add"
 * @param options     - fetch-config (method, headers, body, osv.)
 * @param requireAuth - hvis true, legges JWT i Authorization-header
 * @returns           - typed svar (T) eller kaster feil hvis API feiler
 */
export async function apiFetch<T>(
  endpoint: string,
  options: RequestInit = {},
  requireAuth: boolean = false
): Promise<T> {
  // Bruk Record<string, string> for å unngå TS-feil ved setting av headers
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...(options.headers as Record<string, string>),
  };

  // Legg til JWT i Authorization hvis krevd
  if (requireAuth) {
    const token = localStorage.getItem("jwt");
    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    } else {
      throw new Error("Missing authentication token");
    }
  }

  // Utfør API-kallet
  const res = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });

  // Hvis responsen ikke er OK, kast en feil
  if (!res.ok) {
    let errorMessage = `API error: ${res.status}`;
    try {
      // Backend sender ofte en JSON med "message"
      const errorData = await res.json();
      if (errorData.message) {
        errorMessage = errorData.message;
      }
    } catch {
      // Ignorer hvis body ikke er JSON
    }
    throw new Error(errorMessage);
  }

  // Hvis API returnerer "204 No Content", returner undefined
  if (res.status === 204) {
    return undefined as T;
  }

  // Ellers parse JSON og returner typed data
  return res.json() as Promise<T>;
}
