<script setup lang="ts">
import Cart from "~/components/Cart.vue";

definePageMeta({
  title: "Handlekurv",
  description: "Viser handlekurven til innlogget bruker",
});
</script>

<template>
  <main class="p-6">
    <Cart />
  </main>
</template>
