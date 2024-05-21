<template>
    {{ formData }}
    <section class="bg-gray-100 py-8">
        <div class="container mx-auto">
            <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">매물 등록</h2>
            <form @submit.prevent="submitForm">
                <div class="form-group mb-4">
                    <label for="aptName" class="block mb-2">아파트 이름</label>
                    <input type="text" v-model="formData.aptName" id="aptName"
                        class="w-full p-3 border border-gray-400 rounded" readonly />
                </div>
                <div class="form-group mb-4">
                    <label for="roadNameAddress" class="block mb-2">도로명 주소</label>
                    <input type="text" v-model="formData.roadNameAddress" id="roadNameAddress"
                        class="w-full p-3 border border-gray-400 rounded" readonly />
                </div>
                <!-- 필요한 추가 필드들 -->
                <div class="form-group mb-4">
                    <label for="dealAmount" class="block mb-2">거래 금액</label>
                    <input type="number" v-model="formData.dealAmount" id="dealAmount"
                        class="w-full p-3 border border-gray-400 rounded" />
                </div>
                <div class="form-group mb-4">
                    <label for="exclusiveArea" class="block mb-2">전용 면적</label>
                    <input type="number" v-model="formData.exclusiveArea" id="exclusiveArea"
                        class="w-full p-3 border border-gray-400 rounded" />
                </div>
                <button type="submit" class="bg-blue-500 text-white px-6 py-3 rounded">등록</button>
            </form>
        </div>
    </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

// 로컬 스토리지에서 apartment 객체를 가져옴
const router = useRouter();
const apartment = JSON.parse(localStorage.getItem('selectedApartment') || '{}');
const formData = ref({ ...apartment, dealAmount: '' });

const submitForm = async () => {
    try {
        const response = await axios.post('/api/api/apt-sales/registerSale', formData.value);
        console.log(response.data);
        alert('매물이 성공적으로 등록되었습니다.');
        router.push({ name: 'Home' }); // 등록 후 홈으로 이동 또는 다른 페이지로 이동
    } catch (error) {
        console.error('매물 등록 중 오류가 발생했습니다:', error);
        alert('매물 등록 중 오류가 발생했습니다.');
    }
};
</script>

<style scoped>
.form-group {
    margin-bottom: 1rem;
}

.form-group label {
    display: block;
    margin-bottom: 0.5rem;
}

.form-group input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.bg-blue-500 {
    background-color: #4299e1;
}

.bg-blue-500:hover {
    background-color: #2b6cb0;
}
</style>
