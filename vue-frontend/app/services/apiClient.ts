export async function apiFetch<T>(
  endpoint: string,
  options: RequestInit = {},
  requireAuth = false
): Promise<T> {
  const config = useRuntimeConfig();
  const baseUrl = config.public.apiBase || "http://localhost:8080/api";

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...(options.headers as Record<string, string>),
  };

  if (requireAuth) {
    const token = localStorage.getItem("jwt");
    if (!token) throw new Error("Missing authentication token");
    headers["Authorization"] = `Bearer ${token}`;
  }

  // Normalize method to allowed literal types for $fetch
  const method = (options.method?.toUpperCase?.() || "GET") as
    | "GET"
    | "POST"
    | "PUT"
    | "PATCH"
    | "DELETE"
    | "HEAD"
    | "OPTIONS"
    | "TRACE"
    | undefined;

  try {
    const data = await $fetch<T>(`${baseUrl}${endpoint}`, {
      method,
      headers,
      body: options.body,
    });
    return data;
  } catch (err: any) {
    const msg =
      err?.response?._data?.message ||
      `API error: ${err.status || "unknown status"}`;
    throw new Error(msg);
  }
}
