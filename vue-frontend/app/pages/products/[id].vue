<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useRoute } from "#app";
import { getProductById } from "~/services/productService";
import { addToCart } from "~/services/cartService";
import { useToast } from "vue-toastification";
import ProductDetail from "~/components/ProductDetail.vue";
import type { ProductResponseDto } from "~/types/product";

const route = useRoute();
const toast = useToast();

const product = ref<ProductResponseDto | null>(null);
const id = computed(() => route.params.id);

async function handleAddToCart() {
  if (!id.value) return;
  try {
    await addToCart({ productId: Number(id.value), quantity: 1 });
    toast.success("Lagt til i handlekurv");
  } catch (err) {
    console.error("Kunne ikke legge til i handlekurv:", err);
  }
}

async function loadProduct() {
  if (!id.value) return;
  try {
    product.value = await getProductById(Number(id.value));
  } catch (err) {
    console.error("Kunne ikke hente produkt:", err);
  }
}

onMounted(loadProduct);
watch(id, loadProduct);
</script>

<template>
  <section
    v-if="product"
    class="max-w-screen-lg p-6 mx-auto my-12 bg-gray-800 rounded-lg shadow"
  >
    <ProductDetail :product="product" :on-add-to-cart="handleAddToCart" />
  </section>

  <p v-else class="mt-10 text-center">Laster produkt...</p>
</template>
