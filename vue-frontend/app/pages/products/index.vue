<script setup lang="ts">
import ProductList from "~/components/ProductList.vue";

definePageMeta({
  title: "Produkter",
  description: "Se alle produkter i Webshoppen",
});
</script>

<template>
  <main>
    <ProductList />
  </main>
</template>
