<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-container" @click.stop>
      <div class="modal-content">
        <h2 class="text-2xl font-bold mb-4">로그인</h2>
        <form @submit.prevent="handleLogin">
          <div class="mb-4">
            <label for="email" class="block mb-2">이메일</label>
            <input v-model="email" type="email" id="email"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <div class="mb-4">
            <label for="password" class="block mb-2">비밀번호</label>
            <input v-model="password" type="password" id="password"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">로그인</button>
        </form>
        <p class="mt-4">
          회원이 아니신가요? <a href="#" @click.prevent="openSignup" class="text-blue-500 hover:underline">회원가입하기</a>
        </p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useCounterStore } from '@/stores/counter'; // Pinia 스토어 가져오기

const email = ref('');
const password = ref('');
const store = useCounterStore(); // Pinia 스토어 사용


const emit = defineEmits(['close', 'openSignup']);

const handleLogin = async () => {
  console.log("로그인 함수")
  try {
    const response = await axios.post('/api/login', {
      email: email.value,
      password: password.value,
    });
    if (response.data) {
      store.login(response.data); // Pinia 스토어의 login 액션 호출
      console.log(response.data)
      emit('close');
    } else {
      alert('로그인 실패');
    }
  } catch (error) {
    console.error(error);
    alert('로그인 실패');
  }
};

const openSignup = () => {
  emit('close');
  emit('openSignup');
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
  z-index: 999;
  /* Ensure modal overlay is above the backdrop */
}

.modal-container {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  /* Ensure modal container is above the overlay */
  position: relative;
}
</style>