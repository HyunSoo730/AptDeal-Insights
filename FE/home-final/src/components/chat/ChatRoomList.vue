<template>
  <div class="container mx-auto py-8 px-4 lg:px-8">
    <div class="mb-6">
      <input v-model="newRoomName" placeholder="새 방 이름" 
             class="w-full p-3 mb-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <button @click="createRoom" 
              class="w-full bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600 transition-colors duration-200">
        채팅방 생성
      </button>
    </div>
    <div class="room-list grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
      <div class="room-card p-4 border border-gray-300 rounded-md shadow-md hover:bg-gray-100 cursor-pointer transition-colors duration-200"
           v-for="room in chatRooms" :key="room.id" @click="enterRoom(room.name)">
        <h3 class="text-xl font-semibold text-gray-800">{{ room.name }}</h3>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useCounterStore } from '@/stores/counter';

export default {
  name: 'ChatRoomList',
  setup() {
    const router = useRouter();
    const counterStore = useCounterStore();
    const newRoomName = ref("");
    const chatRooms = ref([]);

    const loadChatRooms = async () => {
      try {
        const response = await axios.get('/api/api/chatrooms');
        chatRooms.value = response.data;
      } catch (error) {
        console.error('Failed to load chat rooms', error);
      }
    };

    const createRoom = async () => {
      if (newRoomName.value !== '') {
        try {
          const response = await axios.post('/api/api/chatrooms', newRoomName.value, {
            headers: {
              'Content-Type': 'text/plain'
            }
          });
          chatRooms.value.push(response.data);
          newRoomName.value = '';
        } catch (error) {
          console.error('Failed to create chat room', error);
        }
      }
    };

    const enterRoom = (roomName) => {
      router.push({ name: 'ChatRoom', params: { name: roomName } });
    };

    onMounted(loadChatRooms);

    return {
      newRoomName,
      chatRooms,
      createRoom,
      enterRoom,
      user: counterStore.user
    };
  }
}
</script>

<style scoped>
.room-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.room-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  width: 200px;
  text-align: center;
  cursor: pointer;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.room-card:hover {
  background-color: #f0f0f0;
}
</style>
