<script setup lang="ts">
import type { ProductResponseDto } from "~/types/product";
import { useAuthStore } from "~/stores/auth";

const props = defineProps<{
  product: ProductResponseDto;
  onAddToCart: () => void;
}>();

const auth = useAuthStore();
</script>

<template>
  <div class="grid grid-cols-1 gap-8 md:grid-cols-2">
    <!-- Venstre side: bilde -->
    <div class="flex items-center justify-center bg-gray-700 rounded-lg">
      <!-- TODO: Legg til produktbilde her -->
    </div>

    <!-- Høyre side: info -->
    <div class="flex flex-col justify-between">
      <div>
        <h1 class="mb-4 text-3xl font-bold">{{ product.name }}</h1>
        <p class="mb-4 text-gray-400">{{ product.description }}</p>
        <p class="text-xl font-semibold">Pris: {{ product.price }} kr</p>
        <p class="mb-6 text-sm text-gray-300">
          Antall igjen:
          <span :class="product.stock > 0 ? 'text-green-400' : 'text-red-500'">
            {{ product.stock > 0 ? product.stock : "Utsolgt" }}
          </span>
        </p>
      </div>

      <button
        v-if="auth.user"
        @click="onAddToCart"
        :disabled="product.stock <= 0"
        class="px-4 py-2 text-white bg-blue-600 rounded hover:bg-blue-700 disabled:bg-gray-500"
      >
        {{ product.stock > 0 ? "Legg i handlekurv" : "Utsolgt" }}
      </button>
    </div>
  </div>
</template>
