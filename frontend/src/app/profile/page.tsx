"use client";
import { useEffect, useState } from "react";
import Link from "next/link";
import { getCurrentUser, UserResponseDto } from "@/services/userApi";

export default function Profile() {
  const [user, setUser] = useState<UserResponseDto | null>(null);

  useEffect(() => {
    getCurrentUser()
      .then(setUser)
      .catch((err) => console.error("Kunne ikke hente bruker:", err));
  }, []);

  if (!user) {
    return <p className="mt-10 text-center">Laster profil...</p>;
  }

  return (
    <section className="max-w-md p-8 mx-auto my-12 bg-gray-800 shadow-lg rounded-2xl">
      <h1 className="mb-8 text-3xl font-bold text-center text-white">
        Min Profil
      </h1>

      {/* Brukerinfo */}
      <div className="mb-10 space-y-4 text-gray-200">
        <div className="flex justify-between">
          <span className="font-semibold">Brukernavn:</span>
          <span>{user.username}</span>
        </div>
        <div className="flex justify-between">
          <span className="font-semibold">E-post:</span>
          <span>{user.email}</span>
        </div>
      </div>

      {/* Handling-knapper */}
      <div className="flex justify-center space-x-4">
        <Link href="/orders">
          <button className="px-5 py-2 font-medium text-white transition bg-blue-600 rounded-lg hover:bg-blue-700">
            Mine Ordrer
          </button>
        </Link>

        <button className="px-5 py-2 font-medium text-white transition bg-gray-600 rounded-lg hover:bg-gray-700">
          Rediger Profil
        </button>
      </div>
    </section>
  );
}
