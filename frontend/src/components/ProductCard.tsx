"use client";
import { useRouter } from "next/navigation";
import { ProductResponseDto } from "@/services/productApi";

interface Props {
  product: ProductResponseDto;
}

export default function ProductCard({ product }: Props) {
  const router = useRouter();

  const openProduct = () => {
    router.push(`/products/${product.id}`);
  };

  return (
    <div
      className="flex flex-col h-full p-6 transition bg-gray-800 rounded-lg shadow cursor-pointer hover:bg-gray-700 hover:shadow-lg"
      onClick={openProduct}
    >
      <div className="flex flex-col justify-between flex-grow">
        <div>
          <h3 className="mb-2 text-lg font-semibold">{product.name}</h3>
          <p className="mb-4 text-sm text-gray-400 select-none line-clamp-2">
            {product.description}
          </p>
        </div>
        <p className="mt-auto text-lg font-bold text-center select-none">
          {product.price} kr
        </p>
      </div>
    </div>
  );
}
