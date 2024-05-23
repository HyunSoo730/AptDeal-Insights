<template>
  <div class="container mx-auto py-8 px-4 lg:px-8">
    <div class="mb-6 flex flex-col items-center">
      <input v-model="newRoomName" placeholder="새 방 이름"
        class="w-full max-w-md p-3 mb-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
      <button @click="createRoom"
        class="w-full max-w-md bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600 transition-colors duration-200">
        채팅방 생성
      </button>
    </div>
    <div class="room-list grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
      <div
        class="room-card p-8 border border-gray-300 rounded-md shadow-md hover:bg-gray-100 cursor-pointer transition-colors duration-200"
        v-for="room in chatRooms" :key="room.id">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-semibold text-gray-800">{{ room.name }}</h3>
          <div class="flex space-x-2">
            <button @click="enterRoom(room.name)"
              class="flex items-center justify-center bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600 transition-colors duration-200">
              입장
            </button>
            <button @click="deleteRoom(room.id)"
              class="flex items-center justify-center bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition-colors duration-200">
              삭제
            </button>
          </div>
        </div>
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

    const deleteRoom = async (roomId) => {
      console.log(roomId)
      try {
        await axios.delete(`/api/api/chatrooms/${roomId}`);
        chatRooms.value = chatRooms.value.filter(room => room.id !== roomId);
      } catch (error) {
        console.error('Failed to delete chat room', error);
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
      deleteRoom,
      enterRoom,
      user: counterStore.user
    };
  }
}
</script>




<style scoped>
.room-list {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 16px;
}

@media (min-width: 640px) {
  .room-list {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 768px) {
  .room-list {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (min-width: 1024px) {
  .room-list {
    grid-template-columns: repeat(4, 1fr);
  }
}

.room-card {
  border: 1px solid #ccc;
  padding: 32px;
  margin: 10px;
  width: 100%;
  text-align: left;
  cursor: pointer;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.room-card:hover {
  background-color: #f0f0f0;
}

.room-card h3 {
  margin-bottom: 16px;
}

.room-card button {
  margin-top: 8px;
}
</style>
