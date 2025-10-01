"use client";
import { useEffect, useState } from "react";
import {
  getCartItems,
  clearCart,
  CartItemResponseDto,
} from "@/services/cartApi";
import CartItem from "./CartItem";

export default function Cart() {
  const [cart, setCart] = useState<CartItemResponseDto[]>([]);

  // Hent cart items ved første render
  useEffect(() => {
    loadCart();
  }, []);

  async function loadCart() {
    try {
      const items = await getCartItems();
      setCart(items);
    } catch (err) {
      console.error("Kunne ikke hente handlekurv:", err);
    }
  }

  async function handleClearCart() {
    try {
      await clearCart();
      setCart([]); // oppdater UI lokalt
    } catch (err) {
      console.error("Kunne ikke tømme handlekurven:", err);
    }
  }

  return (
    <div>
      <p>Cart.tsx</p>
      {cart.length === 0 ? (
        <p>Handlekurven er tom</p>
      ) : (
        <>
          <ul className="space-y-4 mb-6">
            {cart.map((item) => (
              <CartItem key={item.id} item={item} />
            ))}
          </ul>
          <button
            onClick={handleClearCart}
            className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
          >
            Tøm handlekurv
          </button>
        </>
      )}
    </div>
  );
}
