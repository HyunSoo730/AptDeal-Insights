<template>
  <div class="container mx-auto py-12 px-4 lg:px-8">
    <h2 class="text-4xl font-bold mb-8 text-indigo-600 flex items-center">
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
        class="w-8 h-8 mr-2">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
          d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
      마이페이지
    </h2>
    <div v-if="user && !isEditing">
      <div class="bg-white shadow-lg rounded-2xl p-8 mb-6">
        <p class="mb-4 text-xl text-gray-700"><span class="font-semibold text-indigo-600">이메일:</span> {{ user.email }}</p>
        <p class="mb-4 text-xl text-gray-700"><span class="font-semibold text-indigo-600">이름:</span> {{ user.name }}</p>
        <p class="mb-4 text-xl text-gray-700"><span class="font-semibold text-indigo-600">닉네임:</span> {{ user.nickname }}</p>
      </div>
      <div class="flex space-x-4 mx-auto mt-auto">
        <button @click="startEditing"
          class="bg-indigo-600 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-indigo-700 transition duration-300 flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
            class="w-5 h-5 mr-2">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
          </svg>
          수정
        </button>
        <button @click="navigateToWishlist"
          class="bg-green-500 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-green-600 transition duration-300 flex items-center">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
            class="w-5 h-5 mr-2">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
          </svg>
          찜 목록
        </button>
      </div>
      <div class="mt-6">
        <h3 class="text-xl font-bold mb-4 text-gray-800">세션 목록</h3>
        <ul class="flex space-x-2 overflow-x-auto">
          <li v-for="sessionId in sessionIds" :key="sessionId" class="flex items-center">
            <button @click="openModal(sessionId)"
              class="bg-purple-500 text-white px-4 py-2 rounded-md hover:bg-purple-600 transition-colors duration-200 mr-2">
              {{ formatSessionTitle(sessionId) }}
            </button>
          </li>
        </ul>
      </div>
    </div>
    <div v-else-if="isEditing" class="bg-white shadow-lg rounded-2xl p-8">
      <form @submit.prevent="updateUser">
        <div class="mb-6">
          <label for="nickname" class="block mb-2 text-xl font-semibold text-gray-700">닉네임</label>
          <input v-model="editedUser.nickname" type="text" id="nickname"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700" />
        </div>
        <div class="mb-6">
          <label for="password" class="block mb-2 text-xl font-semibold text-gray-700">비밀번호 변경 (선택사항)</label>
          <input v-model="editedUser.password" type="password" id="password"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700" />
        </div>
        <div v-if="editedUser.password" class="mb-6">
          <label for="confirmPassword" class="block mb-2 text-xl font-semibold text-gray-700">비밀번호 확인</label>
          <input v-model="confirmPassword" type="password" id="confirmPassword"
            class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700" />
          <p :class="{'text-green-500': passwordMatch, 'text-red-500': !passwordMatch && confirmPassword !== ''}" class="text-sm mt-1">
            {{ passwordMatch ? '일치합니다' : confirmPassword !== '' ? '틀렸습니다' : '' }}
          </p>
        </div>
        <div class="flex space-x-4">
          <button type="submit"
            class="bg-indigo-600 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-indigo-700 transition duration-300 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
              class="w-5 h-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
            저장
          </button>
          <button @click="cancelEditing"
            class="bg-gray-500 text-white px-6 py-3 rounded-lg text-lg font-semibold hover:bg-gray-600 transition duration-300 flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
              class="w-5 h-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
            취소
          </button>
        </div>
      </form>
    </div>
    <div v-else class="bg-white shadow-lg rounded-2xl p-8">
      <p class="text-xl text-gray-700">사용자 정보를 로드 중입니다...</p>
    </div>
    <AiChatModal :isVisible="isModalVisible" :sessionId="selectedSessionId" :userId="user?.id || 0"
      @close="handleModalClose" @deleted="handleSessionDeleted" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useCounterStore } from '@/stores/counter.js';
import axios from 'axios';
import AiChatModal from '@/components/AiChatModal.vue';

const store = useCounterStore();
const router = useRouter();
const isLoggedIn = computed(() => store.isLoggedIn);
const user = ref(null);
const editedUser = ref({ password: '', nickname: '' });
const isEditing = ref(false);
const chatHistory = ref([]);
const sessionIds = ref([]);
const selectedSessionId = ref('');
const isModalVisible = ref(false);
const confirmPassword = ref('');
const passwordMatch = ref(false);

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
      sessionIds.value = response.data;
    } catch (error) {
      console.error('Failed to load session IDs:', error);
    }
  }
};

const startEditing = () => {
  editedUser.value = { password: '', nickname: user.value.nickname };
  isEditing.value = true;
};

const updateUser = async () => {
  if (!editedUser.value.password || passwordMatch.value) {
    try {
      const token = localStorage.getItem('token');
      await axios.put('/api/user', editedUser.value, {
        headers: { Authorization: `${token}` },
      });
      user.value.nickname = editedUser.value.nickname;
      isEditing.value = false;
      editedUser.value = { password: '', nickname: '' };
      confirmPassword.value = '';
      alert("수정 되었습니다!");
      window.location.reload(); // 수정 완료 후 새로고침
    } catch (error) {
      console.error(error);
    }
  } else {
    alert('비밀번호가 일치하지 않습니다.');
  }
};


const navigateToWishlist = () => {
  router.push('/wishlist');
};

const cancelEditing = () => {
  editedUser.value = null;
  isEditing.value = false;
};

const openModal = (sessionId) => {
  selectedSessionId.value = sessionId;
  isModalVisible.value = true;
};

const handleModalClose = () => {
  isModalVisible.value = false;
};

const handleSessionDeleted = async (sessionId) => {
  await loadSessionIds();
  if (selectedSessionId.value === sessionId) {
    selectedSessionId.value = '';
    chatHistory.value = [];
  }
};

const formatSessionTitle = (sessionId) => {
  const date = new Date(Number(sessionId));
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ${date.getHours()}시 ${date.getMinutes()}분 대화`;
};

const checkPasswordMatch = () => {
  passwordMatch.value = confirmPassword.value === editedUser.value.password;
};

watch([editedUser.value.password, confirmPassword], () => {
  checkPasswordMatch();
});
</script>

<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
