<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCounterStore } from '@/stores/counter.js';
import axios from 'axios';

const store = useCounterStore();
const router = useRouter();
const isLoggedIn = computed(() => store.isLoggedIn);
const user = ref(null);
const editedUser = ref(null);
const isEditing = ref(false);

onMounted(async () => {
  if (isLoggedIn.value) {
    try {
      const token = localStorage.getItem('token');
      console.log(token)
      const response = await axios.get('/api/user', {
        headers: { Authorization: `Bearer ${token}` },
      });
      user.value = response.data;
      console.log(user)
    } catch (error) {
      console.error(error);
    }
  } else {
    router.push('/');
  }
});

const startEditing = () => {
  editedUser.value = { ...user.value };
  isEditing.value = true;
};

const updateUser = async () => {
  try {
    const token = localStorage.getItem('token');
    await axios.put('/api/user', editedUser.value, {
      headers: { Authorization: `Bearer ${token}` },
    });
    user.value = { ...editedUser.value };
    isEditing.value = false;
  } catch (error) {
    console.error(error);
  }
};

const cancelEditing = () => {
  editedUser.value = null;
  isEditing.value = false;
};
</script>

<!-- MyPage.vue -->
<template>
  <div class="container mx-auto py-8">
    <h2 class="text-2xl font-bold mb-4">마이페이지</h2>
    <div v-if="!isEditing">
      {{ user }}
      <p><strong>이메일:</strong> {{ user.email }}</p>
      <p><strong>이름:</strong> {{ user.name }}</p>
      <p><strong>닉네임:</strong> {{ user.nickname }}</p>
      <button @click="startEditing" class="bg-blue-500 text-white px-4 py-2 rounded-md mt-4">수정</button>
    </div>
    <div v-else>
      <form @submit.prevent="updateUser">
        <div class="mb-4">
          <label for="name" class="block mb-2">이름</label>
          <input v-model="editedUser.name" type="text" id="name" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div class="mb-4">
          <label for="nickname" class="block mb-2">닉네임</label>
          <input v-model="editedUser.nickname" type="text" id="nickname" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-md">저장</button>
        <button @click="cancelEditing" class="bg-gray-500 text-white px-4 py-2 rounded-md ml-2">취소</button>
      </form>
    </div>
  </div>
</template>

