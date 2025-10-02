"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/hooks/useAuth";
import PopupModal from "@/modals/PopupModal";

export default function Navbar() {
  const { user, isAdmin, logout } = useAuth();
  const router = useRouter();
  const [showModal, setShowModal] = useState(false);

  function handleCloseModal() {
    setShowModal(false);
    router.push("/"); // redirect home
  }

  async function handleLogout(e: React.FormEvent) {
    e.preventDefault();
    logout(); // oppdater context + hent user
    setShowModal(true);
  }

  return (
    <>
      <nav className="flex justify-between p-4 text-white bg-gray-800">
        <div className="flex gap-4">
          <p>Navbar.tsx</p>
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
              <Link href="/login">Logg inn</Link>
              <Link href="/register">Registrer</Link>
            </>
          )}
        </div>
      </nav>
      <PopupModal
        open={showModal}
        onClose={handleCloseModal}
        title="Utlogging vellykket"
        message={`Sees snart!`}
        autoClose={5000} // lukker automatisk etter 5s
      />
    </>
  );
}
