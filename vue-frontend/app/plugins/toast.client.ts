import Toast, { POSITION } from "vue-toastification";
import type { PluginOptions } from "vue-toastification";
import "vue-toastification/dist/index.css";

export default defineNuxtPlugin((nuxtApp) => {
  const options: PluginOptions = {
    position: POSITION.BOTTOM_RIGHT,
    timeout: 2500,
    closeOnClick: true,
    pauseOnHover: true,
  };

  nuxtApp.vueApp.use(Toast, options);
});
