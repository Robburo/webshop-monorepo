"use client";

import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { getOrderById, OrderDto } from "@/services/orderApi";

export default function ConfirmationPage() {
  const params = useSearchParams();
  const orderId = params.get("orderId");
  const [order, setOrder] = useState<OrderDto | null>(null);

  useEffect(() => {
    if (orderId) {
      getOrderById(Number(orderId))
        .then(setOrder)
        .catch((err) => console.error("Kunne ikke hente ordre:", err));
    }
  }, [orderId]);

  if (!orderId) {
    return <p className="text-center mt-10">Ingen ordre funnet.</p>;
  }

  if (!order) {
    return <p className="text-center mt-10">Laster ordreinformasjon...</p>;
  }

  const total = order.items.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

  return (
    <div className="max-w-2xl mx-auto mt-10">
      <h2 className="mb-6 text-2xl font-bold text-center">
        Takk for bestillingen!
      </h2>

      <div className="mb-6 bg-gray-800 p-4 rounded">
        <h3 className="text-lg font-semibold mb-2">Ordre #{order.id}</h3>
        <p>Status: {order.status}</p>
        <p>Dato: {new Date(order.createdAt).toLocaleString("no-NO")}</p>
      </div>

      <div className="mb-6 bg-gray-800 p-4 rounded">
        <h3 className="text-lg font-semibold mb-2">Leveringsinformasjon</h3>
        <p>{order.recipientName}</p>
        <p>{order.street}</p>
        <p>
          {order.postalCode} {order.city}
        </p>
        <p>{order.country}</p>
      </div>

      <div className="bg-gray-800 p-4 rounded">
        <h3 className="text-lg font-semibold mb-2">Bestilte varer</h3>
        <ul className="space-y-2">
          {order.items.map((item) => (
            <li
              key={item.id}
              className="flex justify-between border-b border-gray-700 pb-1"
            >
              <span>
                {item.productName} ({item.quantity} stk)
              </span>
              <span>{(item.price * item.quantity).toFixed(2)} kr</span>
            </li>
          ))}
        </ul>
        <div className="mt-4 font-bold text-right">
          Total: {total.toFixed(2)} kr
        </div>
      </div>

      <div className="mt-6 text-center">
        <a
          href="/orders"
          className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
        >
          Se mine ordre
        </a>
      </div>
    </div>
  );
}
