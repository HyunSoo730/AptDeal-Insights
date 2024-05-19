<template>
  <header class="bg-white shadow">
    <div class="container mx-auto py-6 flex justify-between items-center">
      <h1 class="text-3xl font-bold text-gray-800">아파트 거래 정보</h1>
      <nav>
        <ul class="flex space-x-4">
          <li><router-link to="/" class="text-gray-700 hover:text-black">Home</router-link></li>
          <li><router-link to="/mypage" class="text-gray-700 hover:text-black">마이페이지</router-link></li>
          <li><a href="#" @click.prevent="openLoginModal" class="text-gray-700 hover:text-black">로그인</a></li>
        </ul>
      </nav>
    </div>
  </header>
  <div class="modal-backdrop" v-if="showLoginModal || showSignupModal"></div>
  <LoginModal v-if="showLoginModal" @close="closeLoginModal" @openSignup="openSignupModal" />
  <SignupModal v-if="showSignupModal" @close="closeSignupModal" />
</template>

<script setup>
import { ref } from 'vue';
import LoginModal from '@/components/LoginModal.vue';
import SignupModal from '@/components/SignupModal.vue';

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
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 998; /* Ensure backdrop is below the modal */
}
</style>
