<!-- Modal.vue -->
<template>
    <div v-if="isVisible" class="fixed inset-0 bg-gray-800 bg-opacity-75 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-lg">
            <h3 class="text-2xl font-bold mb-4 text-gray-800">AI 채팅 내역 (세션: {{ sessionId }})</h3>
            <div v-if="chatHistory.length">
                <div v-for="(message, index) in chatHistory" :key="index" class="mb-2">
                    <p><strong>{{ message.role === 'user' ? '나' : 'AI 봇🤖' }}:</strong> {{ message.content }}</p>
                </div>
            </div>
            <div v-else>
                <p>대화 내역이 없습니다.</p>
            </div>
            <div class="flex justify-end mt-4">
                <button @click="deleteSession"
                    class="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition-colors duration-200 mr-2">삭제</button>
                <button @click="closeModal"
                    class="bg-gray-500 text-white px-4 py-2 rounded-md hover:bg-gray-600 transition-colors duration-200">닫기</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const props = defineProps({
    isVisible: Boolean,
    sessionId: String,
    userId: Number
});

console.log('Modal props:', props); // 디버깅용 콘솔 출력

const emit = defineEmits(['close', 'deleted']);
const chatHistory = ref([]);

const closeModal = () => {
    emit('close');
};

const deleteSession = async () => {
    if (!props.userId) {
        console.error('User ID is not provided');
        return;
    }

    try {
        await axios.delete(`/api/api/chat/${props.userId}/${props.sessionId}`);
        emit('deleted', props.sessionId);
        closeModal();
    } catch (error) {
        console.error('Failed to delete session:', error);
    }
};

const loadChatHistory = async () => {
    if (!props.userId) {
        console.error('User ID is not provided');
        return;
    }

    try {
        console.log('Loading chat history for userId:', props.userId, 'sessionId:', props.sessionId);
        const response = await axios.get(`/api/api/chat/${props.userId}/${props.sessionId}`);
        chatHistory.value = response.data;
    } catch (error) {
        console.error('Failed to load chat history:', error);
    }
};

onMounted(() => {
    if (props.isVisible && props.userId) {
        loadChatHistory();
    }
});

watch(() => props.isVisible, (newValue) => {
    if (newValue && props.userId) {
        loadChatHistory();
    }
});
</script>

<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
