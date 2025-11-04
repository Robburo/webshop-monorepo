"use client";

import { useRouter, usePathname } from "next/navigation";

export default function BackButton() {
  const router = useRouter();
  const pathname = usePathname();

  if (pathname === "/") return null;
  
  return (
    <button
      onClick={() => router.back()}
      className="fixed top-20 left-4 z-50 px-3 py-1 text-sm text-gray-100 bg-gray-700 rounded hover:bg-gray-600"
    >
      ← Tilbake
    </button>
  );
}
