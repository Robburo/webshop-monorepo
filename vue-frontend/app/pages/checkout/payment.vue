<script setup lang="ts">
import { ref, computed } from "vue";
import { useRouter, useRoute } from "#app";
import { updateOrderStatus } from "~/services/orderService";

const router = useRouter();
const route = useRoute();

const orderId = computed(() => route.query.orderId);
const form = ref({
  number: "",
  name: "",
  cvc: "",
});

function handleChange(e: Event) {
  const target = e.target as HTMLInputElement;
  form.value = { ...form.value, [target.name]: target.value };
}

const isValid = computed(() => {
  const n = form.value.number;
  const cvc = form.value.cvc;
  const name = form.value.name.trim();
  return (
    n.length === 16 &&
    /^\d+$/.test(n) &&
    cvc.length === 3 &&
    /^\d+$/.test(cvc) &&
    name.length > 0
  );
});

async function handleSubmit(e: Event) {
  e.preventDefault();
  if (!orderId.value) return;

  try {
    await updateOrderStatus(Number(orderId.value), "PAID");
    router.push(`/checkout/confirmation?orderId=${orderId.value}`);
  } catch (err) {
    console.error("Kunne ikke oppdatere betaling:", err);
  }
}
</script>

<template>
  <div class="max-w-md mx-auto mt-10">
    <h2 class="mb-6 text-2xl font-bold">Betaling</h2>

    <form @submit="handleSubmit" class="space-y-4">
      <input
        name="number"
        :value="form.number"
        @input="handleChange"
        placeholder="Kortnummer (16 siffer)"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="name"
        :value="form.name"
        @input="handleChange"
        placeholder="Navn på kortet"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="cvc"
        :value="form.cvc"
        @input="handleChange"
        placeholder="CVC (3 siffer)"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <button
        type="submit"
        :disabled="!isValid"
        class="px-4 py-2 rounded w-full text-center"
        :class="
          isValid
            ? 'bg-blue-600 text-white hover:bg-blue-700'
            : 'bg-gray-400 text-gray-200 cursor-not-allowed'
        "
      >
        Fullfør betaling
      </button>
    </form>
  </div>
</template>
