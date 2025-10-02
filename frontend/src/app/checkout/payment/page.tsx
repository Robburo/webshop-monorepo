"use client";

import { useSearchParams, useRouter } from "next/navigation";
import { useState } from "react";
import { updateOrderStatus } from "@/services/orderApi";

export default function PaymentPage() {
  const params = useSearchParams();
  const router = useRouter();
  const orderId = params.get("orderId");

  const [form, setForm] = useState({ number: "", name: "", cvc: "" });

  function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  const isValid =
    form.number.length === 16 &&
    /^\d+$/.test(form.number) &&
    form.cvc.length === 3 &&
    /^\d+$/.test(form.cvc) &&
    form.name.trim().length > 0;

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!orderId) return;

    try {
      await updateOrderStatus(Number(orderId), "PAID");
      router.push(`/checkout/confirmation?orderId=${orderId}`);
    } catch (err) {
      console.error("Kunne ikke oppdatere betaling:", err);
    }
  }

  return (
    <div className="max-w-md mx-auto mt-10">
      <h2 className="mb-6 text-2xl font-bold">Betaling</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          name="number"
          value={form.number}
          onChange={handleChange}
          placeholder="Kortnummer (16 siffer)"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="name"
          value={form.name}
          onChange={handleChange}
          placeholder="Navn på kortet"
          className="w-full p-2 rounded text-white"
        />
        <input
          name="cvc"
          value={form.cvc}
          onChange={handleChange}
          placeholder="CVC (3 siffer)"
          className="w-full p-2 rounded text-white"
        />

        <button
          type="submit"
          disabled={!isValid}
          className={`px-4 py-2 rounded ${
            isValid
              ? "bg-blue-600 text-white hover:bg-blue-700"
              : "bg-gray-400 text-gray-200 cursor-not-allowed"
          }`}
        >
          Fullfør betaling
        </button>
      </form>
    </div>
  );
}
