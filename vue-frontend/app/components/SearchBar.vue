<script setup lang="ts">
const props = defineProps<{
  value: string;
  placeholder?: string;
}>();

const emit = defineEmits<{
  (e: "update:value", value: string): void;
}>();

function handleInput(event: Event) {
  const target = event.target as HTMLInputElement;
  emit("update:value", target.value);
}
</script>

<template>
  <div class="w-full max-w-md">
    <input
      type="text"
      :value="value"
      @input="handleInput"
      :placeholder="placeholder || 'Søk etter produkter...'"
      class="w-full max-w-md px-4 py-2 rounded-lg bg-gray-800 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500"
    />
  </div>
</template>
