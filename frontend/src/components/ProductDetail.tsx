// components/ProductDetail.tsx
"use client";

import { ProductResponseDto } from "@/services/productApi";

interface Props {
  product: ProductResponseDto;
  onAddToCart: () => void;
}

export default function ProductDetail({ product, onAddToCart }: Props) {
  return (
    <div className="grid grid-cols-1 gap-8 md:grid-cols-2">
      {/* Venstre side: bilde */}
      <div className="flex items-center justify-center bg-gray-700 rounded-lg">
        {/* TODO Legge til bilde */}
      </div>

      {/* Høyre side: info */}
      <div className="flex flex-col justify-between">
        <div>
          <h1 className="mb-4 text-3xl font-bold">{product.name}</h1>
          <p className="mb-4 text-gray-400">{product.description}</p>
          <p className="text-xl font-semibold">Pris: {product.price} kr</p>
          <p className="mb-6 text-sm text-gray-300">
            Antall igjen:{" "}
            <span className={product.stock > 0 ? "text-green-400" : "text-red-500"}>
              {product.stock > 0 ? product.stock : "Utsolgt"}
            </span>
          </p>
        </div>

        <button
          onClick={onAddToCart}
          className="px-4 py-2 text-white bg-blue-600 rounded hover:bg-blue-700 disabled:bg-gray-500"
          disabled={product.stock <= 0}
        >
          {product.stock > 0 ? "Legg i handlekurv" : "Utsolgt"}
        </button>
      </div>
    </div>
  );
}
