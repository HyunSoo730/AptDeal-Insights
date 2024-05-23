<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCounterStore } from '@/stores/counter.js';
import axios from 'axios';

const store = useCounterStore();
const router = useRouter();
const isLoggedIn = computed(() => store.isLoggedIn);
const user = ref(null);
const editedUser = ref(null);
const isEditing = ref(false);
const chatHistory = ref([]);
const sessionIds = ref([]);
const selectedSessionId = ref('');

onMounted(async () => {
  if (isLoggedIn.value) {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/user', {
        headers: { Authorization: `${token}` },
      });
      user.value = response.data;
      await loadSessionIds();
    } catch (error) {
      console.error(error);
    }
  } else {
    router.push('/');
  }
});

const loadSessionIds = async () => {
  if (user.value) {
    try {
      const response = await axios.get(`/api/api/chat/${user.value.id}/sessions`);
      console.log('Session IDs:', response.data); // 디버깅용 로그
      sessionIds.value = response.data;
    } catch (error) {
      console.error('Failed to load session IDs:', error);
    }
  }
};

const startEditing = () => {
  editedUser.value = { ...user.value };
  isEditing.value = true;
};

const updateUser = async () => {
  try {
    const token = localStorage.getItem('token');
    await axios.put('/api/user', editedUser.value, {
      headers: { Authorization: `${token}` },
    });
    user.value = { ...editedUser.value };
    isEditing.value = false;
  } catch (error) {
    console.error(error);
  }
};

const navigateToWishlist = () => {
  router.push('/wishlist');
};

const cancelEditing = () => {
  editedUser.value = null;
  isEditing.value = false;
};

const loadChatHistory = async (sessionId) => {
  console.log('Loading chat history for session:', sessionId); // 디버깅용 로그
  console.log(user.value.id)
  try {
    const response = await axios.get(`/api/api/chat/${user.value.id}/${sessionId}`);
    console.log('Chat history:', response.data); // 디버깅용 로그
    chatHistory.value = response.data;
    selectedSessionId.value = sessionId;
  } catch (error) {
    console.error('Failed to load chat history:', error);
  }
};
</script>

<!-- MyPage.vue -->
<template>
  <div class="container mx-auto py-8 px-4 lg:px-8">
    <h2 class="text-3xl font-bold mb-6 text-gray-800">마이페이지</h2>
    <div v-if="user && !isEditing">
      <div class="bg-white shadow-md rounded-lg p-6 mb-4">
        <p class="mb-2"><strong>이메일:</strong> {{ user.email }}</p>
        <p class="mb-2"><strong>이름:</strong> {{ user.name }}</p>
        <p class="mb-2"><strong>닉네임:</strong> {{ user.nickname }}</p>
      </div>
      <button @click="startEditing"
        class="bg-blue-500 text-white px-4 py-2 rounded-md mt-4 mr-2 hover:bg-blue-600 transition-colors duration-200">수정</button>
      <button @click="navigateToWishlist"
        class="bg-green-500 text-white px-4 py-2 rounded-md mt-4 hover:bg-green-600 transition-colors duration-200">찜
        목록</button>
      <div class="mt-6">
        <h3 class="text-xl font-bold mb-4 text-gray-800">세션 목록</h3>
        <ul>
          <li v-for="sessionId in sessionIds" :key="sessionId" class="mb-2">
            <button @click="loadChatHistory(sessionId)"
              class="bg-purple-500 text-white px-4 py-2 rounded-md hover:bg-purple-600 transition-colors duration-200">{{
                sessionId }}</button>
          </li>
        </ul>
      </div>
      <div v-if="chatHistory.length" class="mt-4 bg-white shadow-md rounded-lg p-6">
        <h3 class="text-2xl font-bold mb-4 text-gray-800">AI 채팅 내역 (세션: {{ selectedSessionId }})</h3>
        <div v-for="(message, index) in chatHistory" :key="index" class="mb-2">
          <p><strong>{{ message.role === 'user' ? '나' : 'AI 봇🤖' }}:</strong> {{ message.content }}</p>
        </div>
      </div>
    </div>
    <div v-else-if="isEditing" class="bg-white shadow-md rounded-lg p-6">
      <form @submit.prevent="updateUser">
        <div class="mb-4">
          <label for="name" class="block mb-2 text-gray-700">이름</label>
          <input v-model="editedUser.name" type="text" id="name"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div class="mb-4">
          <label for="nickname" class="block mb-2 text-gray-700">닉네임</label>
          <input v-model="editedUser.nickname" type="text" id="nickname"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <button type="submit"
          class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-colors duration-200">저장</button>
        <button @click="cancelEditing"
          class="bg-gray-500 text-white px-4 py-2 rounded-md ml-2 hover:bg-gray-600 transition-colors duration-200">취소</button>
      </form>
    </div>
    <div v-else class="bg-white shadow-md rounded-lg p-6">
      <p>사용자 정보를 로드 중입니다...</p>
    </div>
  </div>
</template>

<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
