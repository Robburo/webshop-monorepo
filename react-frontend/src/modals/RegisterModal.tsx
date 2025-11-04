"use client";
import { useState, useRef, useEffect } from "react";
import { registerUser } from "@/services/userApi";
import { useRouter } from "next/navigation";

interface RegisterModalProps {
  open: boolean;
  onClose: () => void;
}

export default function RegisterModal({ open, onClose }: RegisterModalProps) {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const router = useRouter();
  const nameInputRef = useRef<HTMLInputElement>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (!username || !email || !password) {
      setError("Alle felt må fylles inn");
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Ugyldig e-postadresse");
      return;
    }

    try {
      await registerUser({ username, email, password });
      setError("");
      setUsername("");
      setEmail("");
      setPassword("");
      onClose();
      router.push("/");
    } catch (err) {
      console.error("Kunne ikke registrere bruker:", err);
      setError("Feil ved registrering av ny bruker");
    }
  }

  useEffect(() => {
    if (open && nameInputRef.current) {
      nameInputRef.current.focus();
    }
  }, [open]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="w-full max-w-sm p-6 text-center bg-gray-800 rounded shadow-lg">
        <h2 className="mb-4 text-xl font-bold">Registrer ny bruker</h2>
        {error && <p className="mb-2 text-red-500">{error}</p>}
        <form onSubmit={handleSubmit}>
          <input
            ref={nameInputRef}
            type="text"
            placeholder="Brukernavn"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="block w-full p-2 mb-3 bg-gray-700 rounded"
            required
          />
          <input
            type="email"
            placeholder="E-post"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="block w-full p-2 mb-3 bg-gray-700 rounded"
            required
          />
          <input
            type="password"
            placeholder="Passord"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="block w-full p-2 mb-3 bg-gray-700 rounded"
            required
          />
          <div className="flex justify-center gap-4">
            <button
              type="submit"
              className="px-4 py-2 text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              Registrer
            </button>
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-white bg-gray-600 rounded hover:bg-gray-700"
            >
              Avbryt
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
