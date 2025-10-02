"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { ProductResponseDto, getProductById } from "@/services/productApi";
import { addToCart } from "@/services/cartApi";
import toast from "react-hot-toast";
import ProductDetail from "@/components/ProductDetail";

export default function ProductPage() {
  const { id } = useParams();
  const [product, setProduct] = useState<ProductResponseDto | null>(null);

  const handleAddToCart = () => {
    if (!id) return;
    addToCart({ productId: Number(id), quantity: 1 }).catch((err) =>
      console.error("Kunne ikke legge til i handlekurv:", err)
    );
    toast.success("Lagt til i handlekurv");
  };

  useEffect(() => {
    if (id) {
      getProductById(Number(id))
        .then(setProduct)
        .catch((err) => console.error("Kunne ikke hente produkt:", err));
    }
  }, [id]);

  if (!product) {
    return <p className="mt-10 text-center">Laster produkt...</p>;
  }

  return (
    <section className="max-w-screen-lg p-6 mx-auto my-12 bg-gray-800 rounded-lg shadow">
      <ProductDetail product={product} onAddToCart={handleAddToCart} />
    </section>
  );
}
