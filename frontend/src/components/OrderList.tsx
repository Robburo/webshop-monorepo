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
      <h2 className="flex justify-center mb-6 text-2xl font-bold">Mine ordre</h2>
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {orders.map((order) => (
          <OrderCard key={order.id} order={order} />
        ))}
      </div>
    </section>
  );
}
