<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "#app";
import { checkout } from "~/services/orderService";

const router = useRouter();

const form = ref({
  recipientName: "",
  street: "",
  postalCode: "",
  city: "",
  country: "Norge",
});

function handleChange(e: Event) {
  const target = e.target as HTMLInputElement;
  form.value = { ...form.value, [target.name]: target.value };
}

async function handleCheckout(redirectToPayment: boolean) {
  try {
    const order = await checkout({
      recipientName: form.value.recipientName,
      street: form.value.street,
      postalCode: form.value.postalCode,
      city: form.value.city,
      country: form.value.country,
    });

    if (redirectToPayment) {
      router.push(`/checkout/payment?orderId=${order.id}`);
    } else {
      router.push("/orders");
    }
  } catch (err) {
    console.error("Kunne ikke opprette ordre:", err);
  }
}

async function handleSubmit(e: Event) {
  e.preventDefault();
  await handleCheckout(true); // Gå videre til betaling
}
</script>

<template>
  <div class="max-w-md mx-auto mt-10">
    <h2 class="mb-6 text-2xl font-bold">Leveringsinformasjon</h2>

    <form @submit="handleSubmit" class="space-y-4">
      <input
        name="recipientName"
        :value="form.recipientName"
        @input="handleChange"
        placeholder="Navn"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="street"
        :value="form.street"
        @input="handleChange"
        placeholder="Gateadresse"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="postalCode"
        :value="form.postalCode"
        @input="handleChange"
        placeholder="Postnummer"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="city"
        :value="form.city"
        @input="handleChange"
        placeholder="By"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <input
        name="country"
        :value="form.country"
        @input="handleChange"
        placeholder="Land"
        class="w-full p-2 rounded text-white bg-gray-800"
      />

      <div class="flex justify-between mt-6">
        <button
          type="submit"
          class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
        >
          Fortsett til betaling
        </button>

        <button
          type="button"
          @click="handleCheckout(false)"
          class="px-4 py-2 bg-yellow-600 text-white rounded hover:bg-yellow-700"
        >
          Betal senere
        </button>
      </div>
    </form>
  </div>
</template>
