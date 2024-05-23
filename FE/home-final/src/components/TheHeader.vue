<template>
  <header class="bg-white shadow">
    <div class="container mx-auto py-6 flex justify-between items-center">
      <div
        class="flex items-center space-x-4 cursor-pointer p-4 bg-white rounded-lg shadow-lg transform transition-all duration-300 hover:scale-105 hover:bg-gray-50"
        @click="navigateToHome"
      >
        <img
          src="@/assets/img/house.png"
          alt="아파트 거래 정보"
          class="w-14 h-14 rounded-full border-2 border-teal-600"
        />
        <h1 class="text-4xl font-extrabold text-teal-700 tracking-tight">
          아파트 거래 정보
        </h1>
      </div>
      <nav>
        <ul class="flex space-x-4">
          <li v-if="user">
            <span class="text-lg font-semibold text-gray-700"
              >어서오세요, {{ user.nickname }}님</span
            >
          </li>
          <li>
            <router-link to="/" class="text-gray-700 hover:text-teal-600"
              >Home</router-link
            >
          </li>
          <li v-if="isLoggedIn">
            <router-link to="/mypage" class="text-gray-700 hover:text-teal-600"
              >마이페이지</router-link
            >
          </li>
          <li v-if="!isLoggedIn">
            <a
              href="#"
              @click.prevent="openLoginModal"
              class="text-gray-700 hover:text-teal-600"
              >로그인/회원가입</a
            >
          </li>
          <li v-if="isLoggedIn">
            <a
              href="#"
              @click.prevent="logout"
              class="text-gray-700 hover:text-teal-600"
              >로그아웃</a
            >
          </li>
        </ul>
      </nav>
    </div>
  </header>
  <div
    class="modal-backdrop"
    v-if="showLoginModal || showSignupModal || showForgotPasswordModal"
  ></div>
  <LoginModal
    v-if="showLoginModal"
    @close="closeLoginModal"
    @openSignup="openSignupModal"
    @openForgotPassword="openForgotPasswordModal"
  />
  <SignupModal
    v-if="showSignupModal"
    @close="closeSignupModal"
    @openLogin="openLoginModal"
  />
  <ForgotPasswordModal
    v-if="showForgotPasswordModal"
    @close="closeForgotPasswordModal"
    @openLogin="openLoginModal"
  />
</template>

<script setup>
import { useCounterStore } from "@/stores/counter";
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import LoginModal from "@/components/LoginModal.vue";
import SignupModal from "@/components/SignupModal.vue";
import ForgotPasswordModal from "@/components/ForgotPasswordModal.vue";

const store = useCounterStore();
const router = useRouter();

const isLoggedIn = computed(() => store.isLoggedIn);
const user = computed(() => store.user);

const showLoginModal = ref(false);
const showSignupModal = ref(false);
const showForgotPasswordModal = ref(false);

const navigateToHome = () => {
  router.push("/");
};

const openLoginModal = () => {
  showLoginModal.value = true;
};

const closeLoginModal = () => {
  showLoginModal.value = false;
};

const openSignupModal = () => {
  showSignupModal.value = true;
};

const closeSignupModal = () => {
  showSignupModal.value = false;
};

const openForgotPasswordModal = () => {
  showForgotPasswordModal.value = true;
};

const closeForgotPasswordModal = () => {
  showForgotPasswordModal.value = false;
};

const logout = () => {
  store.logout();
  router.push("/");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

h1 {
  font-family: "Poppins", sans-serif;
}
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 998;
}
</style>
