// counter.js (Pinia 스토어)
import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCounterStore = defineStore("counter", () => {
  const count = ref(0);
  const doubleCount = computed(() => count.value * 2);
  const isLoggedIn = ref(false);
  const token = ref(null);

  function increment() {
    count.value++;
  }

  function restoreLoginState() {
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      login(storedToken);
    }
  }

  function login(receivedToken) {
    token.value = receivedToken;
    localStorage.setItem("token", receivedToken);
    isLoggedIn.value = true;
    console.log("login store");
    console.log(receivedToken);
    // 토큰을 헤더에 설정하여 API 요청 시 자동으로 포함되도록 함
    axios.defaults.headers.common["Authorization"] = `Bearer ${receivedToken}`;
  }

  function logout() {
    token.value = null;
    localStorage.removeItem("token");
    isLoggedIn.value = false;
    // 토큰을 헤더에서 제거
    delete axios.defaults.headers.common["Authorization"];
  }

  return { count, doubleCount, increment, isLoggedIn, login, logout, restoreLoginState };
});
