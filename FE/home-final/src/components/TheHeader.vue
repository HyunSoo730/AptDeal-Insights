<template>
  <header class="bg-white shadow">
    <div class="container mx-auto py-6 flex justify-between items-center">
      <h1 class="text-3xl font-bold text-gray-800">아파트 거래 정보</h1>
      <nav>
        <ul class="flex space-x-4">
          <li v-if="user"><span class="text-lg font-semibold text-gray-700">어서오세요, {{ user.nickname }}님</span></li>
          <li><router-link to="/" class="text-gray-700 hover:text-black">Home</router-link></li>
          <li v-if="isLoggedIn"><router-link to="/mypage" class="text-gray-700 hover:text-black">마이페이지</router-link></li>
          <li v-if="!isLoggedIn"><a href="#" @click.prevent="openLoginModal" class="text-gray-700 hover:text-black">로그인</a></li>
          <li v-if="isLoggedIn"><a href="#" @click.prevent="logout" class="text-gray-700 hover:text-black">로그아웃</a></li>
        </ul>
      </nav>
    </div>
  </header>
  <div class="modal-backdrop" v-if="showLoginModal || showSignupModal"></div>
  <LoginModal v-if="showLoginModal" @close="closeLoginModal" @openSignup="openSignupModal" />
  <SignupModal v-if="showSignupModal" @close="closeSignupModal" @openLogin="openLoginModal" />
</template>

<script setup>
import { useCounterStore } from '@/stores/counter';
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import LoginModal from '@/components/LoginModal.vue';
import SignupModal from '@/components/SignupModal.vue';

const store = useCounterStore();
const router = useRouter();

const isLoggedIn = computed(() => store.isLoggedIn);
const user = computed(() => store.user);

const showLoginModal = ref(false);
const showSignupModal = ref(false);

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
  /* Ensure backdrop is below the modal */
}
</style>
