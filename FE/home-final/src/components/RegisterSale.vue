<template>
  <section class="bg-gray-100 py-8">
    <div class="container mx-auto px-4 lg:px-8">
      <h2 class="text-3xl font-bold mb-6 text-gray-800 text-center">매물 등록</h2>
      <form @submit.prevent="submitForm" class="bg-white shadow-md rounded-lg p-6">
        <div class="form-group mb-4">
          <label for="aptName" class="block mb-2 text-gray-700">아파트 이름</label>
          <input
            type="text"
            v-model="formData.aptName"
            id="aptName"
            class="w-full p-3 border border-gray-300 rounded-md bg-gray-100 cursor-not-allowed"
            readonly
          />
        </div>
        <div class="form-group mb-4">
          <label for="roadNameAddress" class="block mb-2 text-gray-700">도로명 주소</label>
          <input
            type="text"
            v-model="formData.roadNameAddress"
            id="roadNameAddress"
            class="w-full p-3 border border-gray-300 rounded-md bg-gray-100 cursor-not-allowed"
            readonly
          />
        </div>
        <!-- 필요한 추가 필드들 -->
        <div class="form-group mb-4">
          <label for="dealAmount" class="block mb-2 text-gray-700">거래 금액</label>
          <input
            type="number"
            v-model="formData.dealAmount"
            id="dealAmount"
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div class="form-group mb-4">
          <label for="exclusiveArea" class="block mb-2 text-gray-700">전용 면적</label>
          <input
            type="number"
            v-model="formData.exclusiveArea"
            id="exclusiveArea"
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <button type="submit" class="bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600 transition-colors duration-200">
          등록
        </button>
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
const formData = ref({ ...apartment, dealAmount: '', exclusiveArea: '' });

const submitForm = async () => {
    try {
        const response = await axios.post('/api/api/apt-sales/registerSale', formData.value);
        console.log(response.data);
        alert('매물이 성공적으로 등록되었습니다.');
        router.push({ name: 'MainPage' }); // 등록 후 홈으로 이동 또는 다른 페이지로 이동
    } catch (error) {
        console.error('매물 등록 중 오류가 발생했습니다:', error);
        alert('매물 등록 중 오류가 발생했습니다.');
    }
};
</script>

<style scoped>
.form-group label {
    display: block;
    margin-bottom: 0.5rem;
    color: #4a5568; /* text-gray-700 */
}

.form-group input {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #e2e8f0; /* border-gray-300 */
    border-radius: 0.375rem; /* rounded-md */
}

.bg-blue-500 {
    background-color: #4299e1; /* Tailwind's blue-500 */
}

.bg-blue-500:hover {
    background-color: #2b6cb0; /* Tailwind's blue-600 */
}
</style>
