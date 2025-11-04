<script setup lang="ts">
import { useRouter } from "#app";
import { useToast } from "vue-toastification";
import { updateOrderStatus } from "~/services/orderService";
import type { OrderDto } from "~/types/order";

const props = defineProps<{
  order: OrderDto;
  onStatusChange?: (id: number, status: string) => void;
}>();

const router = useRouter();
const toast = useToast();

const statusColors: Record<string, string> = {
  PAID: "bg-green-600",
  PENDING: "bg-yellow-600",
  SHIPPED: "bg-blue-600",
  CANCELLED: "bg-red-600",
};

const total = computed(() =>
  props.order.items.reduce((sum, i) => sum + i.price * i.quantity, 0)
);

async function handleUpdate(status: string) {
  try {
    await updateOrderStatus(props.order.id, status);

    toast.success(status === "PAID" ? "Betaling fullført" : "Ordre kansellert");

    if (props.onStatusChange) {
      props.onStatusChange(props.order.id, status);
    } else {
      router.go(0); // same as router.refresh() in Next
    }
  } catch (err) {
    console.error("Kunne ikke oppdatere ordre:", err);
    toast.error("Feil ved oppdatering av ordre");
  }
}

function goToPayment() {
  router.push(`/checkout/payment?orderId=${props.order.id}`);
}

function goToDetails() {
  router.push(`/orders/${props.order.id}`);
}
</script>

<template>
  <div
    class="flex flex-col p-6 transition bg-gray-800 shadow-md rounded-xl hover:shadow-lg"
  >
    <!-- Header -->
    <div class="flex items-center justify-between mb-2">
      <h3 class="text-lg font-semibold">Ordre #{{ order.id }}</h3>
      <span
        class="text-xs px-3 py-1 rounded-full text-white"
        :class="statusColors[order.status] || 'bg-gray-600'"
      >
        {{ order.status }}
      </span>
    </div>

    <!-- Dato -->
    <p class="mb-4 text-sm text-gray-400">
      Dato: {{ new Date(order.createdAt).toLocaleDateString("no-NO") }}
    </p>

    <!-- Items -->
    <ul class="space-y-2">
      <li
        v-for="item in order.items"
        :key="item.id"
        class="flex justify-between pb-2 border-b border-gray-700 last:border-b-0"
      >
        <span class="text-gray-200">
          {{ item.productName }}
          <span class="p-1 text-gray-400">({{ item.quantity }} stk)</span>
        </span>
        <span class="font-semibold">
          {{ (item.price * item.quantity).toFixed(2) }} kr
        </span>
      </li>
    </ul>

    <!-- Total -->
    <div class="pt-4 mt-auto border-t border-gray-700">
      <p class="text-sm text-gray-300">
        Total:
        <span class="text-lg font-bold text-white">
          {{ total.toFixed(2) }} kr
        </span>
      </p>
    </div>

    <!-- Handlinger for PENDING -->
    <div v-if="order.status === 'PENDING'" class="flex justify-end gap-2 mt-4">
      <button
        @click="goToPayment"
        class="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Betal
      </button>
      <button
        @click="handleUpdate('CANCELLED')"
        class="px-3 py-1 bg-red-600 text-white rounded hover:bg-red-700"
      >
        Kanseller
      </button>
    </div>

    <!-- Se detaljer -->
    <div class="mt-4">
      <button
        @click="goToDetails"
        class="w-full px-3 py-2 bg-gray-700 text-white rounded hover:bg-gray-600"
      >
        Se detaljer
      </button>
    </div>
  </div>
</template>
