import { CartItemResponseDto, updateCartItem, removeCartItem } from "@/services/cartApi";

interface Props {
  item: CartItemResponseDto;
}

export default function CartItem({ item }: Props) {
  const handleIncrease = () => {
    updateCartItem(item.id, item.quantity + 1).catch(console.error);
  };

  const handleDecrease = () => {
    if (item.quantity > 1) {
      updateCartItem(item.id, item.quantity - 1).catch(console.error);
    }
  };

  const handleRemove = () => {
    removeCartItem(item.id).catch(console.error);
  };

  return (
    <li className="flex justify-between items-center p-4 bg-gray-800 rounded shadow">
      <p>CartItem.tsx</p>
      <div>
        <h3 className="font-semibold">{item.productName}</h3>
        <p className="text-sm text-gray-400">Antall: {item.quantity}</p>
      </div>
      <div className="space-x-2">
        <button onClick={handleDecrease} className="px-2 py-1 bg-gray-600 rounded">
          -
        </button>
        <button onClick={handleIncrease} className="px-2 py-1 bg-gray-600 rounded">
          +
        </button>
        <button onClick={handleRemove} className="px-2 py-1 bg-red-600 text-white rounded">
          Fjern
        </button>
      </div>
    </li>
  );
}
