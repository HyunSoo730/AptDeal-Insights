<template>
  <div class="container mx-auto py-8 px-4 lg:px-8">
    <!-- Tailwind CSS를 사용하여 스타일링된 컴포넌트 -->
    <div class="mb-6 flex flex-col items-center space-y-4">
      <div class="relative w-full max-w-md">
        <input
          v-model="newRoomName"
          placeholder="새 방 이름"
          class="w-full p-3 pl-10 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <span
          class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400"
        >
          <i class="fas fa-door-open"></i>
        </span>
      </div>
      <button
        @click="createRoom"
        class="w-full max-w-md bg-gradient-to-r from-blue-500 to-purple-500 text-white px-6 py-3 rounded-md shadow-lg transform hover:scale-105 transition-transform duration-300"
      >
        <i class="fas fa-plus-circle mr-2"></i> 채팅방 생성
      </button>
    </div>

    <!-- Font Awesome 아이콘 사용 -->
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
/>

<!-- 방 리스트를 스타일링하는 컴포넌트 -->
<div class="room-list grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-4">
  <div
    class="room-card p-6 w-full border border-gray-200 rounded-xl shadow-lg bg-white hover:bg-blue-50 cursor-pointer transition-all duration-300 transform hover:scale-105"
    v-for="room in chatRooms"
    :key="room.id"
  >
    <div class="flex flex-col justify-between h-full">
      <div class="flex items-center mb-4">
        <i class="fas fa-comments text-blue-500 text-3xl mr-4"></i>
        <h3 class="text-xl font-bold text-gray-800">{{ room.name }}</h3>
      </div>
      <div class="flex space-x-4 mt-auto">
        <button
          @click="enterRoom(room.name)"
          class="flex items-center justify-center bg-gradient-to-r from-green-400 to-green-500 text-white px-8 py-3 rounded-full shadow-md hover:from-green-500 hover:to-green-600 transition-all transform hover:scale-105"
        >
          <i class="fas fa-door-open mr-2"></i> 입장
        </button>
        <button
          @click="deleteRoom(room.id)"
          class="flex items-center justify-center bg-gradient-to-r from-red-400 to-red-500 text-white px-8 py-3 rounded-full shadow-md hover:from-red-500 hover:to-red-600 transition-all transform hover:scale-105"
        >
          <i class="fas fa-trash-alt mr-2"></i> 삭제
        </button>
      </div>
    </div>
  </div>
</div>

  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useCounterStore } from "@/stores/counter";

export default {
  name: "ChatRoomList",
  setup() {
    const router = useRouter();
    const counterStore = useCounterStore();
    const newRoomName = ref("");
    const chatRooms = ref([]);

    const loadChatRooms = async () => {
      try {
        const response = await axios.get("/api/api/chatrooms");
        chatRooms.value = response.data;
      } catch (error) {
        console.error("Failed to load chat rooms", error);
      }
    };

    const createRoom = async () => {
      if (newRoomName.value !== "") {
        try {
          const response = await axios.post(
            "/api/api/chatrooms",
            newRoomName.value,
            {
              headers: {
                "Content-Type": "text/plain",
              },
            }
          );
          // 채팅방 생성 후 방 목록 다시 로드
          await loadChatRooms();
          newRoomName.value = "";
        } catch (error) {
          console.error("Failed to create chat room", error);
        }
      }
    };

    const deleteRoom = async (roomId) => {
      try {
        await axios.delete(`/api/api/chatrooms/${roomId}`);
        chatRooms.value = chatRooms.value.filter((room) => room.id !== roomId);
      } catch (error) {
        console.error("Failed to delete chat room", error);
      }
    };

    const enterRoom = (roomName) => {
      router.push({ name: "ChatRoom", params: { name: roomName } });
    };

    onMounted(loadChatRooms);

    return {
      newRoomName,
      chatRooms,
      createRoom,
      deleteRoom,
      enterRoom,
      user: counterStore.user,
    };
  },
};
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


<!-- 1초마다 계속 채팅방 생기는지 체크 하는 코드 (서버 계속...) -->
<!-- <template>
  <div class="container mx-auto py-8 px-4 lg:px-8">
    <div class="mb-6 flex flex-col items-center space-y-4">
      <div class="relative w-full max-w-md">
        <input
          v-model="newRoomName"
          placeholder="새 방 이름"
          class="w-full p-3 pl-10 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-400">
          <i class="fas fa-door-open"></i>
        </span>
      </div>
      <button
        @click="createRoom"
        class="w-full max-w-md bg-gradient-to-r from-blue-500 to-purple-500 text-white px-6 py-3 rounded-md shadow-lg transform hover:scale-105 transition-transform duration-300"
      >
        <i class="fas fa-plus-circle mr-2"></i> 채팅방 생성
      </button>
    </div>

    <div class="room-list grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-4">
      <div
        class="room-card p-6 w-full border border-gray-200 rounded-xl shadow-lg bg-white hover:bg-blue-50 cursor-pointer transition-all duration-300 transform hover:scale-105"
        v-for="room in chatRooms"
        :key="room.id"
      >
        <div class="flex flex-col justify-between h-full">
          <div class="flex items-center mb-4">
            <i class="fas fa-comments text-blue-500 text-3xl mr-4"></i>
            <h3 class="text-xl font-bold text-gray-800">{{ room.name }}</h3>
          </div>
          <div class="flex space-x-4 mt-auto">
            <button
              @click="enterRoom(room.name)"
              class="flex items-center justify-center bg-gradient-to-r from-green-400 to-green-500 text-white px-8 py-3 rounded-full shadow-md hover:from-green-500 hover:to-green-600 transition-all transform hover:scale-105"
            >
              <i class="fas fa-door-open mr-2"></i> 입장
            </button>
            <button
              @click="deleteRoom(room.id)"
              class="flex items-center justify-center bg-gradient-to-r from-red-400 to-red-500 text-white px-8 py-3 rounded-full shadow-md hover:from-red-500 hover:to-red-600 transition-all transform hover:scale-105"
            >
              <i class="fas fa-trash-alt mr-2"></i> 삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useCounterStore } from "@/stores/counter";

export default {
  name: "ChatRoomList",
  setup() {
    const router = useRouter();
    const counterStore = useCounterStore();
    const newRoomName = ref("");
    const chatRooms = ref([]);
    let intervalId = null;

    const loadChatRooms = async () => {
      try {
        const response = await axios.get("/api/api/chatrooms");
        chatRooms.value = response.data;
      } catch (error) {
        console.error("Failed to load chat rooms", error);
      }
    };

    const createRoom = async () => {
      if (newRoomName.value !== "") {
        try {
          await axios.post(
            "/api/api/chatrooms",
            newRoomName.value,
            {
              headers: {
                "Content-Type": "text/plain",
              },
            }
          );
          await loadChatRooms();
          newRoomName.value = "";
        } catch (error) {
          console.error("Failed to create chat room", error);
        }
      }
    };

    const deleteRoom = async (roomId) => {
      try {
        await axios.delete(`/api/api/chatrooms/${roomId}`);
        await loadChatRooms();
      } catch (error) {
        console.error("Failed to delete chat room", error);
      }
    };

    const enterRoom = (roomName) => {
      router.push({ name: "ChatRoom", params: { name: roomName } });
    };

    onMounted(() => {
      loadChatRooms();
      intervalId = setInterval(loadChatRooms, 1000); // 5초마다 서버에서 채팅방 목록을 불러옴
    });

    onUnmounted(() => {
      clearInterval(intervalId);
    });

    return {
      newRoomName,
      chatRooms,
      createRoom,
      deleteRoom,
      enterRoom,
      user: counterStore.user,
    };
  },
};
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
</style> -->

