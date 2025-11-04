<script setup lang="ts">
import type { CartItemResponseDto } from "~/types/cart";

// Props definition
interface Props {
  item: CartItemResponseDto;
}

// Emits definition
const emit = defineEmits<{
  (e: "update-quantity", itemId: number, newQuantity: number): void;
  (e: "remove", itemId: number): void;
}>();

const props = defineProps<Props>();

// Computed values
const total = computed(() =>
  (props.item.price * props.item.quantity).toFixed(2)
);
const price = computed(() => props.item.price.toFixed(2));

function decreaseQuantity() {
  emit("update-quantity", props.item.id, props.item.quantity - 1);
}

function increaseQuantity() {
  emit("update-quantity", props.item.id, props.item.quantity + 1);
}

function removeItem() {
  emit("remove", props.item.id);
}
</script>

<template>
  <li class="flex items-start justify-between p-4 bg-gray-800 rounded shadow">
    <div class="w-auto max-w-xs px-5 break-words">
      <h3 class="font-semibold">{{ item.productName }}</h3>
      <p class="text-sm text-gray-400">Pris: {{ price }} kr</p>
      <p class="text-sm text-gray-400">Antall: {{ item.quantity }}</p>
      <p class="text-sm text-gray-400">Totalpris: {{ total }} kr</p>
    </div>

    <div class="flex space-x-2">
      <button @click="decreaseQuantity" class="px-2 py-1 bg-gray-600 rounded">
        -
      </button>
      <button @click="increaseQuantity" class="px-2 py-1 bg-gray-600 rounded">
        +
      </button>
      <button
        @click="removeItem"
        class="px-2 py-1 text-white bg-red-600 rounded"
      >
        Fjern
      </button>
    </div>
  </li>
</template>
