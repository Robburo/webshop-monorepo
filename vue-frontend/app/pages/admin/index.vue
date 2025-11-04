<template>
  <section class="p-8 text-center text-gray-100">
    <h1 class="text-3xl font-bold mb-2">{{ title }}</h1>
    <p class="text-gray-400">Page content for {{ title }} will go here.</p>
  </section>
</template>

<script setup lang="ts">
const title =
  useRoute().path === "/" ? "Home" : useRoute().path.replace("/", "");
</script>

<style scoped>
section {
  min-height: 70vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
