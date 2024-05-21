import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore(
  "counter",
  () => {
    const count = ref(0);
    const doubleCount = computed(() => count.value * 2);
    const isLoggedIn = ref(false);
    const token = ref(null);
    const user = ref(null);

    function increment() {
      count.value++;
    }

    function restoreLoginState() {
      const storedToken = localStorage.getItem("token");
      if (storedToken) {
        login(storedToken);
      }
    }

    async function login(receivedToken) {
      token.value = receivedToken;
      localStorage.setItem("token", receivedToken);
      isLoggedIn.value = true;
      console.log("login store");
      console.log(receivedToken);
      axios.defaults.headers.common["Authorization"] = `${receivedToken}`;

      // Fetch user data
      try {
        const response = await axios.get("/api/user");
        user.value = response.data;
      } catch (error) {
        console.error("Error fetching user data:", error);
        logout();
      }
    }

    function logout() {
      token.value = null;
      localStorage.clear(); // localStorage의 모든 데이터를 비웁니다.
      isLoggedIn.value = false;
      delete axios.defaults.headers.common["Authorization"];
      user.value = null;
    }

    return { count, doubleCount, increment, isLoggedIn, user, login, logout, restoreLoginState };
  },
  {}
);
