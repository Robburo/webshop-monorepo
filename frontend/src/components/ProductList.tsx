"use client";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { getAllProducts, ProductResponseDto } from "@/services/productApi";
import ProductCard from "./ProductCard";

export default function ProductList() {
  const [products, setProducts] = useState<ProductResponseDto[]>([]);
  const searchParams = useSearchParams();

  // hent kategori fra query-param
  const categoryId = searchParams.get("category");

  useEffect(() => {
    getAllProducts()
      .then((allProducts) => {
        if (categoryId) {
          // filtrer basert på kategoriId
          const filtered = allProducts.filter(
            (p) => p.categoryId === Number(categoryId)
          );
          setProducts(filtered);
        } else {
          setProducts(allProducts);
        }
      })
      .catch((err) =>
        console.error("Kunne ikke hente produkter fra backend:", err)
      );
  }, [categoryId]);

  if (products.length === 0) {
    return (
      <p>
        {categoryId
          ? "Ingen produkter i denne kategorien."
          : "Ingen produkter tilgjengelig."}
      </p>
    );
  }

  return (
    <section className="my-12">
      <p>ProductList.tsx</p>
      <h2 className="text-2xl font-bold mb-6">
        {categoryId ? "Produkter i valgt kategori" : "Alle produkter"}
      </h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {products.map((p) => (
          <ProductCard key={p.id} product={p} />
        ))}
      </div>
    </section>
  );
}
