"use client";
import { useEffect, useState } from "react";
import {
  getCartItems,
  clearCart,
  updateCartItem,
  removeCartItem,
  CartItemResponseDto,
} from "@/services/cartApi";
import CartItem from "./CartItem";
import toast from "react-hot-toast";

export default function Cart() {
  const [cart, setCart] = useState<CartItemResponseDto[]>([]);
  const [timeoutIds, setTimeoutIds] = useState<Record<number, NodeJS.Timeout>>(
    {}
  );

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
      toast.success("Handlekurven ble tømt!");
    } catch (err) {
      console.error("Kunne ikke tømme handlekurven:", err);
    }
  }

  // Debounced oppdatering av antall
  function handleUpdateQuantity(itemId: number, newQuantity: number) {
    console.log("Ny quantity:", newQuantity);
    if (newQuantity <= 0) {
      console.log("Fjerner produkt med id:", itemId);
      handleRemove(itemId);
      return;
    }

    setCart((prev) =>
      prev.map((item) =>
        item.id === itemId ? { ...item, quantity: newQuantity } : item
      )
    );
    toast.success("Handlekurven oppdatert!");

    if (timeoutIds[itemId]) {
      clearTimeout(timeoutIds[itemId]);
    }

    const id = setTimeout(async () => {
      try {
        const updated = await updateCartItem(itemId, newQuantity);
        setCart((prev) =>
          prev.map((item) => (item.id === updated.id ? updated : item))
        );
      } catch (err) {
        console.error("Kunne ikke oppdatere antall:", err);
      }
    }, 500);

    setTimeoutIds((prev) => ({ ...prev, [itemId]: id }));
  }

  async function handleRemove(itemId: number) {
    try {
      await removeCartItem(itemId);
      setCart((prev) => prev.filter((item) => item.id !== itemId));
      toast.success("Produkt fjernet!");
    } catch (err) {
      console.error("Kunne ikke fjerne vare:", err);
    }
  }

  return (
    <div className="p-8 border-4 border-gray-800">
      <h2 className="mb-4 text-2xl font-bold">Handlekurv</h2>
      {cart.length === 0 ? (
        <p>Handlekurven er tom</p>
      ) : (
        <>
          <ul className="mb-6 space-y-4">
            {cart.map((item) => (
              <CartItem
                key={item.id}
                item={item}
                onUpdateQuantity={handleUpdateQuantity}
                onRemove={handleRemove}
              />
            ))}
          </ul>
          <div className="flex justify-between">
            <button className="px-4 py-2 text-white bg-green-600 rounded hover:bg-green-700">
              Bestill
            </button>
            <button
              onClick={handleClearCart}
              className="px-4 py-2 text-white bg-red-600 rounded hover:bg-red-700"
            >
              Tøm handlekurv
            </button>
          </div>
        </>
      )}
    </div>
  );
}
