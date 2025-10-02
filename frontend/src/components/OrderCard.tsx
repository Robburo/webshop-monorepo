import { OrderDto, updateOrderStatus } from "@/services/orderApi";
import { useRouter } from "next/navigation";
import toast from "react-hot-toast";

interface Props {
  order: OrderDto;
  onStatusChange?: (id: number, status: string) => void;
}

export default function OrderCard({ order, onStatusChange }: Props) {
  const router = useRouter();

  const statusColors: Record<string, string> = {
    PAID: "bg-green-600",
    PENDING: "bg-yellow-600",
    SHIPPED: "bg-blue-600",
    CANCELLED: "bg-red-600",
  };

  const total = order.items.reduce((sum, i) => sum + i.price * i.quantity, 0);

  async function handleUpdate(status: string) {
    try {
      await updateOrderStatus(order.id, status);

      toast.success(
        status === "PAID" ? "Betaling fullført" : "Ordre kansellert"
      );

      if (onStatusChange) {
        onStatusChange(order.id, status);
      } else {
        router.refresh();
      }
    } catch (err) {
      console.error("Kunne ikke oppdatere ordre:", err);
      toast.error("Feil ved oppdatering av ordre");
    }
  }

  return (
    <div className="flex flex-col p-6 transition bg-gray-800 shadow-md rounded-xl hover:shadow-lg">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <h3 className="text-lg font-semibold">Ordre #{order.id}</h3>
        <span
          className={`text-xs px-3 py-1 rounded-full text-white ${
            statusColors[order.status] || "bg-gray-600"
          }`}
        >
          {order.status}
        </span>
      </div>

      {/* Dato */}
      <p className="mb-4 text-sm text-gray-400">
        Dato: {new Date(order.createdAt).toLocaleDateString("no-NO")}
      </p>

      {/* Items */}
      <ul className="space-y-2">
        {order.items.map((item) => (
          <li
            key={item.id}
            className="flex justify-between pb-2 border-b border-gray-700 last:border-b-0"
          >
            <span className="text-gray-200">
              {item.productName}{" "}
              <span className="p-1 text-gray-400">({item.quantity} stk)</span>
            </span>
            <span className="font-semibold">
              {(item.price * item.quantity).toFixed(2)} kr
            </span>
          </li>
        ))}
      </ul>

      {/* Total */}
      <div className="pt-4 mt-auto border-t border-gray-700">
        <p className="text-sm text-gray-300">
          Total:{" "}
          <span className="text-lg font-bold text-white">
            {total.toFixed(2)} kr
          </span>
        </p>
      </div>

      {/* Handlinger for PENDING */}
      {order.status === "PENDING" && (
        <div className="flex justify-end gap-2 mt-4">
          <button
            onClick={() => router.push(`/checkout/payment?orderId=${order.id}`)}
            className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Betal
          </button>
          <button
            onClick={() => handleUpdate("CANCELLED")}
            className="px-3 py-1 bg-red-600 text-white rounded hover:bg-red-700"
          >
            Kanseller
          </button>
        </div>
      )}

      {/* Se detaljer-knapp */}
      <div className="mt-4">
        <button
          onClick={() => router.push(`/orders/${order.id}`)}
          className="w-full px-3 py-2 bg-gray-700 text-white rounded hover:bg-gray-600"
        >
          Se detaljer
        </button>
      </div>
    </div>
  );
}
