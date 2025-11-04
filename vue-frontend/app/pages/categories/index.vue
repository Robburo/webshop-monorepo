<script setup lang="ts">
import CategoryGrid from "~/components/CategoryGrid.vue";

definePageMeta({
  title: "Kategorier",
  description: "Viser alle produktkategorier",
});
</script>

<template>
  <div>
    <CategoryGrid />
  </div>
</template>
