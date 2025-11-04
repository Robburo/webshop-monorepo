"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { checkout } from "@/services/orderApi";

export default function DeliveryPage() {
  const router = useRouter();
  const [form, setForm] = useState({
    recipientName: "",
    street: "",
    postalCode: "",
    city: "",
    country: "Norge",
  });

  function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  async function handleCheckout(redirectToPayment: boolean) {
    try {
      const order = await checkout({
        recipientName: form.recipientName,
        street: form.street,
        postalCode: form.postalCode,
        city: form.city,
        country: form.country,
      });

      if (redirectToPayment) {
        router.push(`/checkout/payment?orderId=${order.id}`);
      } else {
        router.push("/orders");
      }
    } catch (err) {
      console.error("Kunne ikke opprette ordre:", err);
    }
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    handleCheckout(true); // Gå videre til betaling
  }

  return (
    <div className="max-w-md mx-auto mt-10">
      <h2 className="mb-6 text-2xl font-bold">Leveringsinformasjon</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          name="recipientName"
          value={form.recipientName}
          onChange={handleChange}
          placeholder="Navn"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="street"
          value={form.street}
          onChange={handleChange}
          placeholder="Gateadresse"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="postalCode"
          value={form.postalCode}
          onChange={handleChange}
          placeholder="Postnummer"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="city"
          value={form.city}
          onChange={handleChange}
          placeholder="By"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="country"
          value={form.country}
          onChange={handleChange}
          placeholder="Land"
          className="w-full p-2 rounded text-white"
        />

        <div className="flex justify-between mt-6">
          <button
            type="submit"
            className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
          >
            Fortsett til betaling
          </button>
          <button
            type="button"
            onClick={() => handleCheckout(false)}
            className="px-4 py-2 bg-yellow-600 text-white rounded hover:bg-yellow-700"
          >
            Betal senere
          </button>
        </div>
      </form>
    </div>
  );
}
