<script setup lang="ts">
import { ref, onMounted } from "vue";
import {
  getCartItems,
  clearCart,
  updateCartItem,
  removeCartItem,
} from "~/services/cartService";
import CartItem from "~/components/CartItem.vue";
import type { CartItemResponseDto } from "~/types/cart";
import { useRouter } from "#app";
import { useToast } from "vue-toastification";

const cart = ref<CartItemResponseDto[]>([]);
const timeoutIds = ref<Record<number, ReturnType<typeof setTimeout>>>({});
const router = useRouter();
const toast = useToast();

const cartTotal = computed(() =>
  cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
);
const formattedTotal = computed(() => cartTotal.value.toFixed(2));

onMounted(loadCart);

async function loadCart() {
  try {
    cart.value = await getCartItems();
  } catch (err) {
    console.error("Kunne ikke hente handlekurv:", err);
  }
}

async function handleClearCart() {
  try {
    await clearCart();
    cart.value = [];
    toast.success("Handlekurven ble tømt!");
  } catch (err) {
    console.error("Kunne ikke tømme handlekurven:", err);
  }
}

async function handleCheckoutCart() {
  try {
    await router.push("/checkout/delivery");
  } catch (err) {
    console.error("Kunne ikke gå til checkout", err);
  }
}

// Debounced oppdatering av antall
function handleUpdateQuantity(itemId: number, newQuantity: number) {
  if (newQuantity <= 0) {
    handleRemove(itemId);
    return;
  }

  cart.value = cart.value.map((item) =>
    item.id === itemId ? { ...item, quantity: newQuantity } : item
  );
  toast.success("Handlekurven oppdatert!");

  if (timeoutIds.value[itemId]) {
    clearTimeout(timeoutIds.value[itemId]);
  }

  const id = setTimeout(async () => {
    try {
      const updated = await updateCartItem(itemId, newQuantity);
      cart.value = cart.value.map((item) =>
        item.id === updated.id ? updated : item
      );
    } catch (err) {
      console.error("Kunne ikke oppdatere antall:", err);
    }
  }, 500);

  timeoutIds.value[itemId] = id;
}

async function handleRemove(itemId: number) {
  try {
    await removeCartItem(itemId);
    cart.value = cart.value.filter((item) => item.id !== itemId);
    toast.success("Produkt fjernet!");
  } catch (err) {
    console.error("Kunne ikke fjerne vare:", err);
  }
}
</script>

<template>
  <div class="p-8 border-4 border-gray-800">
    <h2 class="mb-4 text-2xl font-bold">Handlekurv</h2>

    <template v-if="cart.length === 0">
      <p>Handlekurven er tom</p>
    </template>

    <template v-else>
      <ul class="mb-6 space-y-4">
        <CartItem
          v-for="item in cart"
          :key="item.id"
          :item="item"
          @update-quantity="handleUpdateQuantity"
          @remove="handleRemove"
        />
      </ul>

      <div class="mb-4 font-bold text-lg flex justify-center">
        Totalpris: {{ formattedTotal }} kr
      </div>

      <div class="flex justify-between">
        <button
          @click="handleCheckoutCart"
          class="px-4 py-2 text-white bg-green-600 rounded hover:bg-green-700"
        >
          Bestill
        </button>
        <button
          @click="handleClearCart"
          class="px-4 py-2 text-white bg-red-600 rounded hover:bg-red-900"
        >
          Tøm handlekurv
        </button>
      </div>
    </template>
  </div>
</template>
