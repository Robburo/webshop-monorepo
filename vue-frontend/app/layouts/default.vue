<script setup lang="ts">
import Navbar from "~/components/Navbar.vue";
import BackButton from "~/components/BackButton.vue";
import { useAuthStore } from "~/stores/auth";
import "~/assets/css/globals.css";

const auth = useAuthStore();
</script>

<template>
  <div class="flex flex-col min-h-screen text-gray-100 bg-gray-900">
    <Navbar />
    <BackButton />
    <main class="flex-1 max-w-6xl p-6 mx-auto">
      <slot />
    </main>
    <footer class="py-4 text-sm text-center text-gray-400 bg-gray-800">
      © {{ new Date().getFullYear() }} Webshop. Alle rettigheter forbeholdt.
    </footer>
  </div>
</template>
