import { OrderDto } from "@/services/orderApi";

interface Props {
  order: OrderDto;
}

export default function OrderCard({ order }: Props) {
  const statusColors: Record<string, string> = {
    PAID: "bg-green-600",
    PENDING: "bg-yellow-600",
    SHIPPED: "bg-blue-600",
    CANCELLED: "bg-red-600",
  };

  const total = order.items.reduce(
    (sum, i) => sum + i.price * i.quantity,
    0
  );

  return (
    <div className="flex flex-col p-6 transition bg-gray-800 shadow-md rounded-xl hover:shadow-lg">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <h3 className="text-lg font-semibold">Ordre #{order.id}</h3>
        <span
          className={`text-xs px-3 py-1 rounded-full text-white ${statusColors[order.status] || "bg-gray-600"}`}
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
            <span className="font-semibold">{item.price} kr</span>
          </li>
        ))}
      </ul>

      {/* Total alltid nederst */}
      <div className="pt-4 mt-auto border-t border-gray-700">
        <p className="text-sm text-gray-300">
          Total:{" "}
          <span className="text-lg font-bold text-white">
            {total.toFixed(2)} kr
          </span>
        </p>
      </div>
    </div>
  );
}
