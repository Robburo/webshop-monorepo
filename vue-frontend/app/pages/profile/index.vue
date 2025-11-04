<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getCurrentUser } from "~/services/userService";
import type { UserResponseDto } from "~/types/user";

const user = ref<UserResponseDto | null>(null);

onMounted(async () => {
  try {
    user.value = await getCurrentUser();
  } catch (err) {
    console.error("Kunne ikke hente bruker:", err);
  }
});
</script>

<template>
  <section
    v-if="user"
    class="max-w-md p-8 mx-auto my-12 bg-gray-800 shadow-lg rounded-2xl"
  >
    <h1 class="mb-8 text-3xl font-bold text-center text-white">Min Profil</h1>

    <!-- Brukerinfo -->
    <div class="mb-10 space-y-4 text-gray-200">
      <div class="flex justify-between">
        <span class="font-semibold">Brukernavn:</span>
        <span>{{ user.username }}</span>
      </div>
      <div class="flex justify-between">
        <span class="font-semibold">E-post:</span>
        <span>{{ user.email }}</span>
      </div>
    </div>

    <!-- Handling-knapper -->
    <div class="flex justify-center space-x-4">
      <NuxtLink to="/orders">
        <button
          class="px-5 py-2 font-medium text-white transition bg-blue-600 rounded-lg hover:bg-blue-700"
        >
          Mine Ordrer
        </button>
      </NuxtLink>

      <button
        class="px-5 py-2 font-medium text-white transition bg-gray-600 rounded-lg hover:bg-gray-700"
      >
        Rediger Profil
      </button>
    </div>
  </section>

  <p v-else class="mt-10 text-center">Laster profil…</p>
</template>
