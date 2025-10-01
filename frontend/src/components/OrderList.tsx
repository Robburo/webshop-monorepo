"use client";
import { useEffect, useState } from "react";
import { getOrdersForUser, OrderDto } from "@/services/orderApi";
import OrderCard from "./OrderCard";

export default function OrderList() {
  const [orders, setOrders] = useState<OrderDto[]>([]);

  useEffect(() => {
    getOrdersForUser()
      .then(setOrders)
      .catch((err) => console.error("Kunne ikke hente ordrer:", err));
  }, []);

  if (orders.length === 0) {
    return <p>Du har ingen ordrer enda.</p>;
  }

  return (
    <section className="my-12">
      <p>Orderlist.tsx</p>
      <h2 className="text-2xl font-bold mb-6">Mine ordre</h2>
      <div className="space-y-4">
        {orders.map((order) => (
          <OrderCard key={order.id} order={order} />
        ))}
      </div>
    </section>
  );
}
