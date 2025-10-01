"use client";
import { useEffect, useState } from "react";
import {
  getAllCategories,
  CategoryResponseDto,
} from "@/services/categoryApi";
import Link from "next/link";

export default function CategoryGrid() {
  const [categories, setCategories] = useState<CategoryResponseDto[]>([]);

  useEffect(() => {
    getAllCategories()
      .then(setCategories)
      .catch((err) =>
        console.error("Kunne ikke hente kategorier fra backend:", err)
      );
  }, []);

  if (categories.length === 0) {
    return <p>Ingen kategorier tilgjengelig.</p>;
  }

  return (
    <section className="my-12">
      <p>CategoryGrid.tsx</p>
      <h2 className="text-2xl font-bold mb-6">Kategorier</h2>
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {categories.map((category) => (
          <Link
            key={category.id}
            href={`/products?category=${category.id}`}
            className="block p-6 bg-gray-800 rounded-lg shadow hover:bg-gray-700 transition"
          >
            <h3 className="text-lg font-semibold text-white">
              {category.name}
            </h3>
          </Link>
        ))}
      </div>
    </section>
  );
}
