<script setup lang="ts">
import { ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import LoginModal from "~/modals/LoginModal.vue";
import RegisterModal from "~/modals/RegisterModal.vue";
import { useRouter } from "#app";
import { useToast } from "vue-toastification";

const auth = useAuthStore();
const router = useRouter();
const toast = useToast();

const showLoginModal = ref(false);
const showRegisterModal = ref(false);

async function handleLogout(e: Event) {
  e.preventDefault();
  auth.logout();
  await router.push("/");
  toast.success("Du ble logget ut!");
}
</script>

<template>
  <nav class="flex justify-between p-4 text-white bg-gray-800">
    <div class="flex gap-4">
      <NuxtLink to="/">Hjem</NuxtLink>
      <NuxtLink to="/products">Produkter</NuxtLink>
      <NuxtLink to="/categories">Kategorier</NuxtLink>
    </div>

    <div class="flex gap-4">
      <template v-if="auth.user">
        <NuxtLink to="/cart">Handlekurv</NuxtLink>
        <NuxtLink to="/profile" class="text-blue-400 hover:underline">
          Min side
        </NuxtLink>
        <NuxtLink v-if="auth.isAdmin" to="/admin">Admin</NuxtLink>
        <button @click="handleLogout" class="text-red-400 hover:text-red-600">
          Logg ut
        </button>
      </template>

      <template v-else>
        <button
          @click="showLoginModal = true"
          class="text-blue-400 hover:underline"
        >
          Logg inn
        </button>
        <button
          @click="showRegisterModal = true"
          class="text-blue-400 hover:underline"
        >
          Registrer
        </button>
      </template>
    </div>
  </nav>

  <!-- Login Modal -->
  <LoginModal :open="showLoginModal" @close="showLoginModal = false" />

  <!-- Register Modal -->
  <RegisterModal :open="showRegisterModal" @close="showRegisterModal = false" />
</template>
