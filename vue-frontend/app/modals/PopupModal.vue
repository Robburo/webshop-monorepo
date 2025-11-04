<script setup lang="ts">
import { ref, watch, nextTick } from "vue";

const props = defineProps<{
  open: boolean;
  onClose: () => void;
  title: string;
  message: string;
  autoClose?: number;
}>();

const okButtonRef = ref<HTMLButtonElement | null>(null);

// Auto-close logic
watch(
  () => props.open,
  (isOpen) => {
    if (isOpen && props.autoClose) {
      const timeout = setTimeout(() => props.onClose(), props.autoClose);
      return () => clearTimeout(timeout);
    }
  }
);

// Focus OK button when modal opens
watch(
  () => props.open,
  async (isOpen) => {
    if (isOpen) {
      await nextTick();
      okButtonRef.value?.focus();
    }
  }
);
</script>

<template>
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div
        class="bg-gray-800 p-6 rounded shadow-lg max-w-sm w-full text-center"
      >
        <h2 class="text-xl font-bold mb-4">{{ title }}</h2>
        <p class="mb-4">{{ message }}</p>
        <button
          ref="okButtonRef"
          @click="onClose"
          class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          OK
        </button>
      </div>
    </div>
  </Teleport>
</template>
