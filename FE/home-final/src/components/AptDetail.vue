<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const route = useRoute();
const aptCode = route.params.aptCode;
const apartments = ref([]);
const isLoading = ref(true);

console.log(aptCode);

onMounted(async () => {
    const token = localStorage.getItem('token');
    if (token) {
        try {
            const response = await axios.get(`/api/api/apt-sales/details/${aptCode}`, {
                headers: { Authorization: `${token}` },
            });
            apartments.value = response.data;
        } catch (error) {
            console.error('아파트 목록을 가져오는 중 오류가 발생했습니다:', error);
        } finally {
            isLoading.value = false;
        }
    }
});
</script>

<template>
    <div class="container mx-auto py-8">
        <h2 class="text-2xl font-bold mb-4">아파트 상세 목록</h2>
        <div v-if="isLoading">
            <p>로딩 중...</p>
        </div>
        <div v-else-if="apartments.length">
            <h3 class="text-2xl font-bold mb-4">{{ apartments[0].aptName }}</h3>
            <ul>
                <li v-for="apt in apartments" :key="apt.id" class="mb-4 p-4 border rounded-md">
                    <p><strong>거래 가격:</strong> {{ apt.dealAmount }}원</p>
                </li>
            </ul>
        </div>
        <div v-else>
            <p>해당 아파트에 대한 정보가 없습니다.</p>
        </div>
    </div>
</template>
