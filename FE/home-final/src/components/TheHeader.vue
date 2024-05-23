<template>
  <header class="bg-white shadow">
    <div class="container mx-auto py-6 flex justify-between items-center">
      <div class="flex items-center space-x-4 cursor-pointer" @click="navigateToHome">
        <img src="@/assets/img/house.png" alt="아파트 거래 정보" class="w-12 h-12">
        <h1 class="text-3xl font-bold text-gray-800">아파트 거래 정보</h1>
      </div>
      <nav>
        <ul class="flex space-x-4">
          <li v-if="user"><span class="text-lg font-semibold text-gray-700">어서오세요, {{ user.nickname }}님</span></li>
          <li><router-link to="/" class="text-gray-700 hover:text-black">Home</router-link></li>
          <li v-if="isLoggedIn"><router-link to="/mypage" class="text-gray-700 hover:text-black">마이페이지</router-link>
          </li>
          <li v-if="!isLoggedIn"><a href="#" @click.prevent="openLoginModal"
              class="text-gray-700 hover:text-black">로그인/회원가입</a></li>
          <li v-if="isLoggedIn"><a href="#" @click.prevent="logout" class="text-gray-700 hover:text-black">로그아웃</a></li>
        </ul>
      </nav>
    </div>
  </header>
  <div class="modal-backdrop" v-if="showLoginModal || showSignupModal || showForgotPasswordModal"></div>
  <LoginModal v-if="showLoginModal" @close="closeLoginModal" @openSignup="openSignupModal"
    @openForgotPassword="openForgotPasswordModal" />
  <SignupModal v-if="showSignupModal" @close="closeSignupModal" @openLogin="openLoginModal" />
  <ForgotPasswordModal v-if="showForgotPasswordModal" @close="closeForgotPasswordModal" @openLogin="openLoginModal" />
</template>

<script setup>
import { useCounterStore } from '@/stores/counter';
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import LoginModal from '@/components/LoginModal.vue';
import SignupModal from '@/components/SignupModal.vue';
import ForgotPasswordModal from '@/components/ForgotPasswordModal.vue';

const store = useCounterStore();
const router = useRouter();

const isLoggedIn = computed(() => store.isLoggedIn);
const user = computed(() => store.user);

const showLoginModal = ref(false);
const showSignupModal = ref(false);
const showForgotPasswordModal = ref(false);

const navigateToHome = () => {
  router.push('/');
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
  router.push('/');
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 998;
}

header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

header h1 {
  font-size: 1.75rem;
  font-weight: 700;
  color: #333;
}

header nav ul {
  display: flex;
  align-items: center;
}

header nav ul li a {
  font-size: 1rem;
  color: #555;
  transition: color 0.3s;
}

header nav ul li a:hover {
  color: #000;
}
</style>
