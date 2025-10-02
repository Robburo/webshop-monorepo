"use client";
import { useState, useRef, useEffect } from "react";
import { useRouter } from "next/navigation";
import { login as loginApi } from "@/services/authApi";
import { useAuth } from "@/hooks/useAuth";
import PopupModal from "@/modals/PopupModal";

export default function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const router = useRouter();
  const { login } = useAuth();
  const nameInputRef = useRef<HTMLInputElement>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      const token = await loginApi(username, password);
      await login(token); // oppdater context + hent user
      setError("");
      setShowModal(true);
    } catch (err) {
      console.error("Innlogging feilet:", err);
      setError("Ugyldig brukernavn eller passord");
    }
  }

  function handleCloseModal() {
    setShowModal(false);
    router.push("/"); // redirect home
  }

  useEffect(() => {
    if (nameInputRef.current) {
      nameInputRef.current.focus();
    }
  });

  return (
    <>
      <form
        onSubmit={handleSubmit}
        className="max-w-md mx-auto p-6 bg-gray-800 rounded shadow"
      >
        <p>LoginForm.tsx</p>
        <h2 className="text-xl font-bold mb-4">Logg inn</h2>
        {error && <p className="mb-2 text-red-500">{error}</p>}
        <input
          ref={nameInputRef}
          type="text"
          placeholder="Brukernavn"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="block w-full mb-3 p-2 rounded bg-gray-700"
        />
        <input
          type="password"
          placeholder="Passord"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="block w-full mb-3 p-2 rounded bg-gray-700"
        />
        <button
          type="submit"
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Logg inn
        </button>
      </form>

      <PopupModal
        open={showModal}
        onClose={handleCloseModal}
        title="Innlogging vellykket"
        message={`Velkommen tilbake, ${username}!`}
        autoClose={5000} // lukker automatisk etter 5s
      />
    </>
  );
}
