<script setup lang="ts">
import { useRouter } from "#app";
import type { ProductResponseDto } from "~/types/product";

const props = defineProps<{
  product: ProductResponseDto;
}>();

const router = useRouter();

function openProduct() {
  router.push(`/products/${props.product.id}`);
}
</script>

<template>
  <div
    @click="openProduct"
    class="flex flex-col h-full p-6 transition bg-gray-800 rounded-lg shadow cursor-pointer hover:bg-gray-700 hover:shadow-lg"
  >
    <div class="flex flex-col justify-between flex-grow">
      <div>
        <h3 class="mb-2 text-lg font-semibold">{{ product.name }}</h3>
        <p class="mb-4 text-sm text-gray-400 select-none line-clamp-2">
          {{ product.description }}
        </p>
      </div>
      <p class="mt-auto text-lg font-bold text-center select-none">
        {{ product.price }} kr
      </p>
    </div>
  </div>
</template>
