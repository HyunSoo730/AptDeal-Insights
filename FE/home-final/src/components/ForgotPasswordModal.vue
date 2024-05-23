<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-container" @click.stop>
      <div class="modal-content">
        <h2 class="text-2xl font-bold mb-4">비밀번호 찾기</h2>
        <form @submit.prevent="handleSendCode">
          <div class="mb-4">
            <label for="email" class="block mb-2 text-gray-700">이메일</label>
            <input v-model="email" type="email" id="email"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-colors duration-200">인증번호 전송</button>
        </form>
        <div v-if="codeSent" class="mt-4">
          <label for="code" class="block mb-2 text-gray-700">인증번호</label>
          <input v-model="code" type="text" id="code" @input="removeSpaces"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4" />
          <button @click="handleVerifyCode" class="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition-colors duration-200">확인</button>
        </div>
        <div v-if="password" class="mt-4">
          <p class="text-gray-700">비밀번호: {{ password }}</p>
          <a href="#" @click.prevent="goToLogin" class="text-blue-500 hover:underline">로그인 하러가기</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';

const email = ref('');
const code = ref('');
const codeSent = ref(false);
const serverCode = ref('');  // 서버에서 받은 인증번호를 저장할 변수
const password = ref('');  // 서버에서 받은 비밀번호를 저장할 변수
const emit = defineEmits(['close', 'openLogin']);  // 'openLogin' 이벤트 추가

const handleSendCode = async () => {
  try {
    const response = await axios.post('/api/send-email', { to: email.value });
    if (response.data.success) {
      serverCode.value = response.data.code;  // 서버에서 받은 인증번호 저장
      codeSent.value = true;
      alert('인증번호가 전송되었습니다.');
    } else {
      alert('인증번호 전송 실패');
    }
  } catch (error) {
    console.error(error);
    alert('인증번호 전송 실패');
  }
};

const handleVerifyCode = async () => {
  if (code.value === serverCode.value) {
    try {
      const response = await axios.post('/api/get-password', { to: email.value });
      if (response.data.success) {
        password.value = response.data.password;
        alert('인증번호가 확인되었습니다.');
      } else {
        alert('비밀번호 조회 실패: 사용자 정보를 찾을 수 없습니다.');
      }
    } catch (error) {
      console.error(error);
      alert('비밀번호 조회 실패');
    }
  } else {
    alert('인증번호가 틀립니다.');
  }
};

const removeSpaces = () => {
  code.value = code.value.replace(/\s+/g, '');
};

const goToLogin = () => {
  emit('close');
  emit('openLogin');
};

const close = () => {
  emit('close');
};
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
  max-width: 400px;
  width: 100%;
}

.modal-content {
  text-align: center;
}
</style>
