import { CartItemResponseDto } from "@/services/cartApi";

interface Props {
  item: CartItemResponseDto;
  onUpdateQuantity: (itemId: number, newQuantity: number) => void;
  onRemove: (itemId: number) => void;
}

export default function CartItem({ item, onUpdateQuantity, onRemove }: Props) {
  return (
    <li className="flex items-start justify-between p-4 bg-gray-800 rounded shadow">
      <div className="w-auto max-w-xs px-5 break-words">
        <h3 className="font-semibold">{item.productName}</h3>
        <p className="text-sm text-gray-400">Antall: {item.quantity}</p>
      </div>
      <div className="flex space-x-2">
        <button
          onClick={() => onUpdateQuantity(item.id, item.quantity - 1)}
          className="px-2 py-1 bg-gray-600 rounded"
        >
          -
        </button>
        <button
          onClick={() => onUpdateQuantity(item.id, item.quantity + 1)}
          className="px-2 py-1 bg-gray-600 rounded"
        >
          +
        </button>
        <button
          onClick={() => onRemove(item.id)}
          className="px-2 py-1 text-white bg-red-600 rounded"
        >
          Fjern
        </button>
      </div>
    </li>
  );
}
