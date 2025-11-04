<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute } from "#app";
import { getOrderById } from "~/services/orderService";
import type { OrderDto } from "~/types/order";

const route = useRoute();
const orderId = computed(() => route.query.orderId);
const order = ref<OrderDto | null>(null);

async function loadOrder() {
  if (!orderId.value) return;
  try {
    order.value = await getOrderById(Number(orderId.value));
  } catch (err) {
    console.error("Kunne ikke hente ordre:", err);
  }
}

onMounted(loadOrder);

const total = computed(() =>
  order.value
    ? order.value.items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      )
    : 0
);
</script>

<template>
  <div v-if="!orderId" class="text-center mt-10">Ingen ordre funnet.</div>

  <div v-else-if="!order" class="text-center mt-10">
    Laster ordreinformasjon...
  </div>

  <div v-else class="max-w-2xl mx-auto mt-10">
    <h2 class="mb-6 text-2xl font-bold text-center">Takk for bestillingen!</h2>

    <!-- Ordreinfo -->
    <div class="mb-6 bg-gray-800 p-4 rounded">
      <h3 class="text-lg font-semibold mb-2">Ordre #{{ order.id }}</h3>
      <p>Status: {{ order.status }}</p>
      <p>Dato: {{ new Date(order.createdAt).toLocaleString("no-NO") }}</p>
    </div>

    <!-- Leveringsinformasjon -->
    <div class="mb-6 bg-gray-800 p-4 rounded">
      <h3 class="text-lg font-semibold mb-2">Leveringsinformasjon</h3>
      <p>{{ order.recipientName }}</p>
      <p>{{ order.street }}</p>
      <p>{{ order.postalCode }} {{ order.city }}</p>
      <p>{{ order.country }}</p>
    </div>

    <!-- Varer -->
    <div class="bg-gray-800 p-4 rounded">
      <h3 class="text-lg font-semibold mb-2">Bestilte varer</h3>
      <ul class="space-y-2">
        <li
          v-for="item in order.items"
          :key="item.id"
          class="flex justify-between border-b border-gray-700 pb-1"
        >
          <span> {{ item.productName }} ({{ item.quantity }} stk) </span>
          <span>{{ (item.price * item.quantity).toFixed(2) }} kr</span>
        </li>
      </ul>
      <div class="mt-4 font-bold text-right">
        Total: {{ total.toFixed(2) }} kr
      </div>
    </div>

    <!-- Tilbake -->
    <div class="mt-6 text-center">
      <NuxtLink
        to="/orders"
        class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
      >
        Se mine ordre
      </NuxtLink>
    </div>
  </div>
</template>
