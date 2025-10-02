"use client";
import { useState, useRef, useEffect } from "react";
import { login as loginApi } from "@/services/authApi";
import { useAuth } from "@/hooks/useAuth";
import toast from "react-hot-toast";

interface LoginModalProps {
  open: boolean;
  onClose: () => void;
}

export default function LoginModal({ open, onClose }: LoginModalProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();
  const nameInputRef = useRef<HTMLInputElement>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      const token = await loginApi(username, password);
      await login(token);
      setError("");
      setUsername("");
      setPassword("");
      toast.success("Velkommen!");
      onClose();
    } catch (err) {
      console.error("Innlogging feilet:", err);
      setError("Ugyldig brukernavn eller passord");
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
        <h2 className="mb-4 text-xl font-bold">Logg inn</h2>
        {error && <p className="mb-2 text-red-500">{error}</p>}
        <form onSubmit={handleSubmit}>
          <input
            ref={nameInputRef}
            type="text"
            placeholder="Brukernavn"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="block w-full p-2 mb-3 bg-gray-700 rounded"
          />
          <input
            type="password"
            placeholder="Passord"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="block w-full p-2 mb-3 bg-gray-700 rounded"
          />
          <div className="flex justify-center gap-4">
            <button
              type="submit"
              className="px-4 py-2 text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              Logg inn
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
