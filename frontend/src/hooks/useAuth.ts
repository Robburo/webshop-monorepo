"use client";
import { useEffect, useState } from "react";
import { getCurrentUser, UserResponseDto } from "@/services/userApi";

interface AuthState {
  user: UserResponseDto | null;
  loading: boolean;
  isAdmin: boolean;
  logout: () => void;
}

export function useAuth(): AuthState {
  const [user, setUser] = useState<UserResponseDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    async function fetchUser() {
      const token = localStorage.getItem("token");
      if (!token) {
        setLoading(false);
        return;
      }
      try {
        const u = await getCurrentUser();
        setUser(u);
        setIsAdmin(u.role === "ROLE_ADMIN");
      } catch (err) {
        console.error("Kunne ikke hente innlogget bruker:", err);
        localStorage.removeItem("token"); // ugyldig token
        setUser(null);
        setIsAdmin(false);
      } finally {
        setLoading(false);
      }
    }
    fetchUser();
  }, []);

  function logout() {
    localStorage.removeItem("token");
    setUser(null);
    setIsAdmin(false);
  }

  return { user, loading, isAdmin, logout };
}
