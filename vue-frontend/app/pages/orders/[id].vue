<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "#app";
import { getOrderById } from "~/services/orderService";
import type { OrderDto } from "~/types/order";

const route = useRoute();
const router = useRouter();

const order = ref<OrderDto | null>(null);
const id = computed(() => route.params.id);

async function loadOrder() {
  if (!id.value) return;
  try {
    order.value = await getOrderById(Number(id.value));
  } catch (err) {
    console.error("Kunne ikke hente ordre:", err);
  }
}

onMounted(loadOrder);
watch(id, loadOrder);

const total = computed(() =>
  order.value
    ? order.value.items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      )
    : 0
);

function goBack() {
  router.push("/orders");
}
</script>

<template>
  <div v-if="order" class="max-w-2xl mx-auto mt-10">
    <h2 class="mb-6 text-2xl font-bold text-center">Ordre #{{ order.id }}</h2>

    <!-- Status -->
    <div class="mb-6 bg-gray-800 p-4 rounded">
      <h3 class="text-lg font-semibold mb-2">Status</h3>
      <p>{{ order.status }}</p>
      <p>Dato: {{ new Date(order.createdAt).toLocaleString("no-NO") }}</p>
    </div>

    <!-- Leveringsinfo -->
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
      <button
        @click="goBack"
        class="px-4 py-2 bg-gray-700 text-white rounded hover:bg-gray-600"
      >
        Tilbake til mine ordre
      </button>
    </div>
  </div>

  <p v-else class="mt-10 text-center">Laster ordre…</p>
</template>
