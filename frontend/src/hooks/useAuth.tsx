"use client";
import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import { getCurrentUser, UserResponseDto } from "@/services/userApi";

interface AuthContextType {
  user: UserResponseDto | null;
  loading: boolean;
  isAdmin: boolean;
  login: (token: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserResponseDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [isAdmin, setIsAdmin] = useState(false);

  // kjøres ved oppstart: sjekk om token finnes
  useEffect(() => {
    async function fetchUser() {
      const token = localStorage.getItem("jwt");
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
        localStorage.removeItem("jwt");
        setUser(null);
        setIsAdmin(false);
      } finally {
        setLoading(false);
      }
    }
    fetchUser();
  }, []);

  // kalles fra LoginForm etter vellykket innlogging
  async function login(token: string) {
    localStorage.setItem("jwt", token);
    try {
      const u = await getCurrentUser();
      setUser(u);
      setIsAdmin(u.role === "ROLE_ADMIN");
    } catch (err) {
      console.error("Kunne ikke hente bruker etter innlogging:", err);
      setUser(null);
      setIsAdmin(false);
    }
  }

  function logout() {
    localStorage.removeItem("jwt");
    setUser(null);
    setIsAdmin(false);
  }

  return (
    <AuthContext.Provider value={{ user, loading, isAdmin, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
