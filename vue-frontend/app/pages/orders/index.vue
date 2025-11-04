<script setup lang="ts">
import OrderList from "~/components/OrderList.vue";

definePageMeta({
  title: "Mine Ordrer",
  description: "Oversikt over alle ordrer for innlogget bruker",
});
</script>

<template>
  <main class="p-6">
    <OrderList />
  </main>
</template>
