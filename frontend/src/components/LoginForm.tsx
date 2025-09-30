"use client";
import { useState } from "react";
import { login } from "@/services/authApi";
import { redirect, RedirectType } from "next/navigation";

export default function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      const token = await login(username, password);
      localStorage.setItem("jwt", token); // lagre JWT lokalt
      setError("");
      redirect('/products', RedirectType.push);
    } catch (err) {
      console.error("Innlogging feilet:", err);
      setError("Ugyldig brukernavn eller passord");
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="max-w-md mx-auto p-6 bg-gray-800 rounded shadow"
    >
      <h2 className="text-xl font-bold mb-4">Logg inn</h2>
      {error && <p className="mb-2 text-red-500">{error}</p>}
      <input
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
  );
}
