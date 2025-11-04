<script setup lang="ts">
import { ref, watch, nextTick } from "vue";
import { useRouter } from "#app";
import { registerUser } from "~/services/userService";

const props = defineProps<{
  open: boolean;
  onClose: () => void;
}>();

const router = useRouter();

const username = ref("");
const email = ref("");
const password = ref("");
const error = ref("");
const nameInputRef = ref<HTMLInputElement | null>(null);

async function handleSubmit(e: Event) {
  e.preventDefault();

  if (!username.value || !email.value || !password.value) {
    error.value = "Alle felt må fylles inn";
    return;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    error.value = "Ugyldig e-postadresse";
    return;
  }

  try {
    await registerUser({
      username: username.value,
      email: email.value,
      password: password.value,
    });

    error.value = "";
    username.value = "";
    email.value = "";
    password.value = "";
    props.onClose();
    router.push("/");
  } catch (err) {
    console.error("Kunne ikke registrere bruker:", err);
    error.value = "Feil ved registrering av ny bruker";
  }
}

// Autofokus ved åpning
watch(
  () => props.open,
  async (isOpen) => {
    if (isOpen) {
      await nextTick();
      nameInputRef.value?.focus();
    }
  }
);
</script>

<template>
  <Teleport to="body">
    <div v-if="props.open" class="modal-overlay">
      <div
        class="w-[90%] sm:max-w-md md:max-w-lg p-8 text-center bg-gray-800 rounded-xl shadow-2xl border border-gray-700"
      >
        <h2 class="mb-4 text-2xl font-bold">Registrer ny bruker</h2>
        <p v-if="error" class="mb-2 text-red-500">{{ error }}</p>

        <form @submit="handleSubmit" class="space-y-4">
          <input
            ref="nameInputRef"
            type="text"
            placeholder="Brukernavn"
            v-model="username"
            class="block w-full p-3 bg-gray-700 rounded outline-none focus:ring-2 focus:ring-blue-500"
            required
          />

          <input
            type="email"
            placeholder="E-post"
            v-model="email"
            class="block w-full p-3 bg-gray-700 rounded outline-none focus:ring-2 focus:ring-blue-500"
            required
          />

          <input
            type="password"
            placeholder="Passord"
            v-model="password"
            class="block w-full p-3 bg-gray-700 rounded outline-none focus:ring-2 focus:ring-blue-500"
            required
          />

          <div class="flex justify-center gap-4 mt-4">
            <button
              type="submit"
              class="px-5 py-2 text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              Registrer
            </button>
            <button
              type="button"
              @click="props.onClose"
              class="px-5 py-2 text-white bg-gray-600 rounded hover:bg-gray-700"
            >
              Avbryt
            </button>
          </div>
        </form>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
.modal-overlay {
  position: fixed !important;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.6);
}
</style>
