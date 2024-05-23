<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-container" @click.stop>
      <div class="modal-content">
        <h2 class="text-2xl font-bold mb-4">로그인</h2>
        <form @submit.prevent="handleLogin">
          <div class="mb-4">
            <label for="email" class="block mb-2 text-gray-700">이메일</label>
            <input v-model="email" type="email" id="email"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <div class="mb-4">
            <label for="password" class="block mb-2 text-gray-700">비밀번호</label>
            <input v-model="password" type="password" id="password"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-colors duration-200">로그인</button>
          <button type="button" @click="openForgotPassword" class="bg-gray-500 text-white px-4 py-2 rounded-md hover:bg-gray-600 transition-colors duration-200 ml-2">비밀번호 찾기</button>
        </form>
        <p class="mt-4">
          회원이 아니신가요? <a href="#" @click.prevent="openSignup" class="text-blue-500 hover:underline">회원가입하기</a>
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';
import { useCounterStore } from '@/stores/counter';
import { useRouter } from 'vue-router';

const email = ref('');
const password = ref('');
const store = useCounterStore();
const emit = defineEmits(['close', 'openSignup', 'openForgotPassword']); // 이벤트 선언
const router = useRouter();

const handleLogin = async () => {
  try {
    const response = await axios.post('/api/login', {
      email: email.value,
      password: password.value,
    });
    if (response.data) {
      store.login(response.data);
      emit('close');
      router.push('/'); // 로그인 성공 후 메인 페이지로 이동
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

const openForgotPassword = () => {
  emit('close');
  emit('openForgotPassword');
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
}

.modal-container {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  position: relative;
  max-width: 400px;
  width: 100%;
}

.modal-content {
  text-align: center;
}
</style>
