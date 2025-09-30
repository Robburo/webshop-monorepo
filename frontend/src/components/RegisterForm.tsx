"use client";
import { useState } from "react";
import { registerUser } from "@/services/userApi";

export default function RegisterForm() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await registerUser({ username, email, password });
      alert("Bruker registrert!");
    } catch (err) {
      console.error("Kunne ikke registrere bruker:", err);
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="max-w-md mx-auto p-6 bg-gray-800 rounded shadow"
    >
      <h2 className="text-xl font-bold mb-4">Registrer ny bruker</h2>
      <input
        type="text"
        placeholder="Brukernavn"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="block w-full mb-3 p-2 rounded bg-gray-700"
      />
      <input
        type="email"
        placeholder="E-post"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
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
        Registrer
      </button>
    </form>
  );
}
