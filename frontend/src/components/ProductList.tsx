"use client";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { getAllProducts, ProductResponseDto } from "@/services/productApi";
import ProductCard from "./ProductCard";
import SearchBar from "./SearchBar";

export default function ProductList() {
  const [products, setProducts] = useState<ProductResponseDto[]>([]);
  const [query, setQuery] = useState("");
  const searchParams = useSearchParams();

  const categoryId = searchParams.get("category");

  useEffect(() => {
    getAllProducts()
      .then((allProducts) => {
        if (categoryId) {
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

  const filteredProducts = products.filter((p) =>
    p.name.toLowerCase().includes(query.toLowerCase())
  );

  return (
    <section className="my-12">
      <h2 className="text-2xl font-bold mb-2 flex justify-center">
        {categoryId ? "Produkter i valgt kategori" : "Alle produkter"}
      </h2>
      <div className="p-6 flex justify-center">
        <SearchBar
        value={query}
        onChange={setQuery}
        placeholder="Søk etter produkter..."
      />
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6 min-h-[200px]">
        {filteredProducts.length > 0 ? (
          filteredProducts.map((p) => <ProductCard key={p.id} product={p} />)
        ) : (
          <p className="col-span-full text-center">Ingen resultater..</p>
        )}
      </div>
    </section>
  );
}
