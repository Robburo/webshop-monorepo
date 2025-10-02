"use client";
import { useState, useEffect, useRef } from "react";
import { registerUser } from "@/services/userApi";
import { useRouter } from "next/navigation";
import PopupModal from "@/modals/PopupModal";

export default function RegisterForm() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showModal, setShowModal] = useState(false);
  const router = useRouter();
  const nameInputRef = useRef<HTMLInputElement>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await registerUser({ username, email, password });
      setShowModal(true);
      router.push("/login");
    } catch (err) {
      console.error("Kunne ikke registrere bruker:", err);
      alert("Feil ved registrering av ny bruker");
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
        <p>RegisterForm.tsx</p>
        <h2 className="text-xl font-bold mb-4">Registrer ny bruker</h2>
        <input
          ref={nameInputRef}
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
      <PopupModal
        open={showModal}
        onClose={handleCloseModal}
        title="Registrering vellykket"
        message={`Velkommen, ${username}!`}
        autoClose={5000} // lukker automatisk etter 5s
      />
    </>
  );
}
