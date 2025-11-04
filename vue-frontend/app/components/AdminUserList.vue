<script setup lang="ts">
import { useRouter, useRoute } from "#app";

const router = useRouter();
const route = useRoute();

function goBack() {
  router.back();
}
</script>

<template>
  <!-- hidden if user is on home page -->
  <button
    v-if="route.path !== '/'"
    @click="goBack"
    class="fixed top-20 left-4 z-50 px-3 py-1 text-sm text-gray-100 bg-gray-700 rounded hover:bg-gray-600"
  >
    ← Tilbake
  </button>
</template>
