<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getAllCategories } from "~/services/categoryService";
import type { CategoryResponseDto } from "~/types/category";

const categories = ref<CategoryResponseDto[]>([]);

onMounted(async () => {
  try {
    categories.value = await getAllCategories();
  } catch (err) {
    console.error("Kunne ikke hente kategorier fra backend:", err);
  }
});
</script>

<template>
  <section class="my-12">
    <h2 class="text-2xl font-bold mb-6">Kategorier</h2>

    <template v-if="categories.length === 0">
      <p>Ingen kategorier tilgjengelig.</p>
    </template>

    <template v-else>
      <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <NuxtLink
          v-for="category in categories"
          :key="category.id"
          :to="`/products?category=${category.id}`"
          class="block p-6 bg-gray-800 rounded-lg shadow hover:bg-gray-700 transition"
        >
          <h3 class="text-lg font-semibold text-white">
            {{ category.name }}
          </h3>
        </NuxtLink>
      </div>
    </template>
  </section>
</template>
