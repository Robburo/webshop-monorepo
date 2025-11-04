<template>
  <button
    v-if="showButton"
    @click="goBack"
    class="fixed top-20 left-4 z-50 px-3 py-1 text-sm text-gray-100 bg-gray-700 rounded hover:bg-gray-600"
  >
    ← Tilbake
  </button>
</template>

<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";

// Router instances
const router = useRouter();
const route = useRoute();

// Hide button on root path
const showButton = computed(() => route.path !== "/");

// Navigate back
function goBack() {
  router.back();
}
</script>
