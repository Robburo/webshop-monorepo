<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useRoute } from "#app";
import { getAllProducts } from "~/services/productService";
import type { ProductResponseDto } from "~/types/product";
import ProductCard from "~/components/ProductCard.vue";
import SearchBar from "~/components/SearchBar.vue";

const products = ref<ProductResponseDto[]>([]);
const query = ref("");
const route = useRoute();

// get ?category param from URL
const categoryId = computed(() => route.query.category);

async function loadProducts() {
  try {
    const allProducts = await getAllProducts();
    if (categoryId.value) {
      products.value = allProducts.filter(
        (p) => p.categoryId === Number(categoryId.value)
      );
    } else {
      products.value = allProducts;
    }
  } catch (err) {
    console.error("Kunne ikke hente produkter fra backend:", err);
  }
}

onMounted(loadProducts);
watch(categoryId, loadProducts); // reload when query param changes

const filteredProducts = computed(() =>
  products.value.filter((p) =>
    p.name.toLowerCase().includes(query.value.toLowerCase())
  )
);
</script>

<template>
  <section class="my-12">
    <h2 class="text-2xl font-bold mb-2 flex justify-center">
      {{ categoryId ? "Produkter i valgt kategori" : "Alle produkter" }}
    </h2>

    <div class="p-6 flex justify-center">
      <SearchBar
        :value="query"
        @update:value="query = $event"
        placeholder="Søk etter produkter..."
      />
    </div>

    <div
      class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6 min-h-[200px]"
    >
      <template v-if="filteredProducts.length > 0">
        <ProductCard v-for="p in filteredProducts" :key="p.id" :product="p" />
      </template>

      <p v-else class="col-span-full text-center">Ingen resultater..</p>
    </div>
  </section>
</template>
