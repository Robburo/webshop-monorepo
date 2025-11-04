import { defineStore } from "pinia";
import { getCurrentUser } from "~/services/userService";

interface UserResponseDto {
  id: number;
  username: string;
  role: string;
  email: string;
}

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null as UserResponseDto | null,
    loading: true,
    isAdmin: false,
  }),

  actions: {
    async fetchUser() {
      const token = localStorage.getItem("jwt");
      if (!token) {
        this.loading = false;
        return;
      }

      try {
        const u = await getCurrentUser();
        this.user = u;
        this.isAdmin = u.role === "ROLE_ADMIN";
      } catch (err) {
        console.error("Kunne ikke hente innlogget bruker:", err);
        localStorage.removeItem("jwt");
        this.user = null;
        this.isAdmin = false;
      } finally {
        this.loading = false;
      }
    },

    async login(token: string) {
      localStorage.setItem("jwt", token);
      try {
        const u = await getCurrentUser();
        this.user = u;
        this.isAdmin = u.role === "ROLE_ADMIN";
      } catch (err) {
        console.error("Kunne ikke hente bruker etter innlogging:", err);
        this.user = null;
        this.isAdmin = false;
      }
    },

    logout() {
      localStorage.removeItem("jwt");
      this.user = null;
      this.isAdmin = false;
      navigateTo("/");
    },
  },
});
