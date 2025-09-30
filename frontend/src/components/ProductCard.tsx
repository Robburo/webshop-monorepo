import { ProductResponseDto } from "@/services/productApi";
import { addToCart } from "@/services/cartApi";

interface Props {
  product: ProductResponseDto;
}

export default function ProductCard({ product }: Props) {
  const handleAddToCart = () => {
    addToCart({ productId: product.id, quantity: 1 }).catch((err) =>
      console.error("Kunne ikke legge til i handlekurv:", err)
    );
  };

  return (
    <div className="p-6 bg-gray-800 rounded-lg shadow flex flex-col justify-between">
      <div>
        <h3 className="text-lg font-semibold mb-2">{product.name}</h3>
        <p className="text-sm text-gray-400 mb-4">{product.description}</p>
        <p className="text-lg font-bold">{product.price} kr</p>
      </div>
      <button
        onClick={handleAddToCart}
        className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Legg i handlekurv
      </button>
    </div>
  );
}
