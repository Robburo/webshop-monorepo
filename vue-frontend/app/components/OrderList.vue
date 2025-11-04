<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getOrdersForUser } from "~/services/orderService";
import type { OrderDto } from "~/types/order";
import OrderCard from "~/components/OrderCard.vue";

const orders = ref<OrderDto[]>([]);

onMounted(async () => {
  try {
    orders.value = await getOrdersForUser();
  } catch (err) {
    console.error("Kunne ikke hente ordrer:", err);
  }
});

function handleStatusChange(id: number, status: string) {
  orders.value = orders.value.map((o) => (o.id === id ? { ...o, status } : o));
}
</script>

<template>
  <section class="my-12">
    <h2 class="flex justify-center mb-6 text-2xl font-bold">Mine ordre</h2>

    <template v-if="orders.length === 0">
      <p>Du har ingen ordrer enda.</p>
    </template>

    <template v-else>
      <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <OrderCard
          v-for="order in orders"
          :key="order.id"
          :order="order"
          :on-status-change="handleStatusChange"
        />
      </div>
    </template>
  </section>
</template>
