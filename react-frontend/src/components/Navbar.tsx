"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/hooks/useAuth";
import LoginModal from "@/modals/LoginModal";
import RegisterModal from "@/modals/RegisterModal";
import toast from "react-hot-toast";

export default function Navbar() {
  const { user, isAdmin, logout } = useAuth();
  const router = useRouter();
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showRegisterModal, setShowRegisterModal] = useState(false);

  async function handleLogout(e: React.FormEvent) {
    e.preventDefault();
    logout();
    router.push("/");
    toast.success("Du ble logget ut!");
  }

  return (
    <>
      <nav className="flex justify-between p-4 text-white bg-gray-800">
        <div className="flex gap-4">
          <Link href="/">Hjem</Link>
          <Link href="/products">Produkter</Link>
          <Link href="/categories">Kategorier</Link>
        </div>

        <div className="flex gap-4">
          {user ? (
            <>
              <Link href="/cart">Handlekurv</Link>
              <Link href="/profile" className="text-blue-400 hover:underline">
                Min side
              </Link>
              {isAdmin && <Link href="/admin">Admin</Link>}
              <button
                onClick={handleLogout}
                className="text-red-400 hover:text-red-600"
              >
                Logg ut
              </button>
            </>
          ) : (
            <>
              <button
                onClick={() => setShowLoginModal(true)}
                className="text-blue-400 hover:underline"
              >
                Logg inn
              </button>
              <button
                onClick={() => setShowRegisterModal(true)}
                className="text-blue-400 hover:underline"
              >
                Registrer
              </button>
            </>
          )}
        </div>
      </nav>

      {/* Login-popup */}
      <LoginModal
        open={showLoginModal}
        onClose={() => setShowLoginModal(false)}
      />
      {/* Register-popup */}
      <RegisterModal
        open={showRegisterModal}
        onClose={() => setShowRegisterModal(false)}
      />
    </>
  );
}
