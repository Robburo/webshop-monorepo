"use client";
import Link from "next/link";
import { useAuth } from "@/hooks/useAuth";

export default function Navbar() {
  const { user, isAdmin, logout } = useAuth();

  return (
    <nav className="bg-gray-900 text-white p-4 flex justify-between">
      <div className="flex gap-4">
        <Link href="/">Hjem</Link>
        <Link href="/products">Produkter</Link>
        <Link href="/categories">Kategorier</Link>

        {user && (
          <>
            <Link href="/cart">Handlekurv</Link>
            <Link href="/orders">Ordre</Link>
            {isAdmin && <Link href="/admin/users">Admin</Link>}
          </>
        )}
      </div>

      <div className="flex gap-4">
        {user ? (
          <>
            <Link
              href="/profile"
              className="hover:underline text-blue-400"
            >
              Hei, {user.username}
            </Link>
            <button
              onClick={logout}
              className="text-red-400 hover:text-red-600"
            >
              Logg ut
            </button>
          </>
        ) : (
          <>
            <Link href="/login">Logg inn</Link>
            <Link href="/register">Registrer</Link>
          </>
        )}
      </div>
    </nav>
  );
}
