<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-container" @click.stop>
      <div class="modal-content">
        <h2 class="text-2xl font-bold mb-4">회원가입</h2>
        <form @submit.prevent="handleSignup">
          <div class="mb-4">
            <label for="email" class="block mb-2">이메일</label>
            <input
              type="email"
              id="email"
              v-model="email"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div class="mb-4">
            <label for="password" class="block mb-2">비밀번호</label>
            <input
              type="password"
              id="password"
              v-model="password"
              @input="checkPasswordMatch"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div class="mb-4">
            <label for="confirm-password" class="block mb-2">비밀번호 확인</label>
            <input
              type="password"
              id="confirm-password"
              v-model="confirmPassword"
              @input="checkPasswordMatch"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <p
              :class="{
                'text-green-500': passwordMatch,
                'text-red-500': !passwordMatch && confirmPassword !== ''
              }"
              class="text-sm mt-1"
            >
              {{ passwordMatch ? '일치합니다' : confirmPassword !== '' ? '틀렸습니다' : '' }}
            </p>
          </div>
          <div class="mb-4">
            <label for="name" class="block mb-2">이름</label>
            <input
              type="text"
              id="name"
              v-model="name"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div class="mb-4">
            <label for="nickname" class="block mb-2">닉네임</label>
            <input
              type="text"
              id="nickname"
              v-model="nickname"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div class="mb-4">
            <label for="role" class="block mb-2">역할</label>
            <select
              id="role"
              v-model="role"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="AGENT">공인중개사</option>
              <option value="USER">일반사용자</option>
            </select>
          </div>
          <button
            type="submit"
            class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
          >
            회원가입
          </button>
        </form>
        <p class="mt-4">
          이미 회원이신가요?
          <a href="#" @click.prevent="openLogin" class="text-blue-500 hover:underline">로그인하기</a>
        </p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';

const handleSignup = async () => {
  try {
    const response = await axios.post('/api/join', {
      email: email.value,
      password: password.value,
      name: name.value,
      nickname: nickname.value,
      role: role.value,
    });
    console.log('회원가입 완료:', response.data);
    emit('close');
  } catch (error) {
    console.error('회원가입 실패:', error);
  }
};

const emit = defineEmits(['close','openLogin']);

let email = ref('');
let password = ref('');
let confirmPassword = ref('');
let name = ref('');
let nickname = ref('');
let role = ref('');
let passwordMatch = ref(false);


const openLogin = () => {
  emit('close');
  emit('openLogin');
};

const checkPasswordMatch = () => {
  passwordMatch.value = confirmPassword.value === password.value;
};

watch([password, confirmPassword], () => {
  checkPasswordMatch();
});
</script>
<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999; /* Ensure modal overlay is above the backdrop */
}

.modal-container {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000; /* Ensure modal container is above the overlay */
  position: relative;
}
</style>