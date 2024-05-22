<template>
    <div>
        <div>
            <input v-model="newRoomName" placeholder="새 방 이름">
            <button @click="createRoom">채팅방 생성</button>
        </div>
        <div class="room-list">
            <div class="room-card" v-for="room in chatRooms" :key="room.id" @click="enterRoom(room.name)">
                <h3>{{ room.name }}</h3>
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
                    const response = await axios.post('api/api/chatrooms', newRoomName.value, {
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
