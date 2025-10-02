"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { getOrderById, OrderDto } from "@/services/orderApi";

export default function OrderDetailsPage() {
  const params = useParams();
  const router = useRouter();
  const { id } = params; // fra /orders/[id]
  const [order, setOrder] = useState<OrderDto | null>(null);

  useEffect(() => {
    if (id) {
      getOrderById(Number(id))
        .then(setOrder)
        .catch((err) => console.error("Kunne ikke hente ordre:", err));
    }
  }, [id]);

  if (!order) {
    return <p className="mt-10 text-center">Laster ordre...</p>;
  }

  const total = order.items.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

  return (
    <div className="max-w-2xl mx-auto mt-10">
      <h2 className="mb-6 text-2xl font-bold text-center">Ordre #{order.id}</h2>

      <div className="mb-6 bg-gray-800 p-4 rounded">
        <h3 className="text-lg font-semibold mb-2">Status</h3>
        <p>{order.status}</p>
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

      {/* Tilbake-knapp */}
      <div className="mt-6 text-center">
        <button
          onClick={() => router.push("/orders")}
          className="px-4 py-2 bg-gray-700 text-white rounded hover:bg-gray-600"
        >
          Tilbake til mine ordre
        </button>
      </div>
    </div>
  );
}
