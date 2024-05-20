import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0);
  const doubleCount = computed(() => count.value * 2);
  const isLoggedIn = ref(false);
  
  function increment() {
    count.value++;
  }

  function login(token) {
    localStorage.setItem('token', token);
    isLoggedIn.value = true;
    console.log("login store")
    console.log(token)
  }
  function logout() {
    localStorage.removeItem('token');
    isLoggedIn.value = false;
  }

  return { count, doubleCount, increment, isLoggedIn, login, logout };
});