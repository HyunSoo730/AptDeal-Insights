<template>
  <section class="bg-gray-100 py-12">
    <div class="container mx-auto">
      <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">어떤 집을 찾고 계세요?</h2>
      <div class="flex justify-center relative">
        <div class="w-1/2">
          <input
            type="text"
            v-model="searchInput"
            placeholder="예) 동, 아파트명, 학교명"
            class="w-full p-3 rounded-l border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
          >
          <ul
            v-if="searchResults.length > 0"
            class="absolute top-full left-0 right-0 w-full mt-2 bg-white border border-gray-400 rounded shadow-md"
          >
            <li
              v-for="result in searchResults"
              :key="result.aptCode"
              @click="selectApartment(result)"
              class="px-4 py-2 hover:bg-gray-200 cursor-pointer"
            >
              {{ result.aptName }} - {{ result.roadNameAddress }}
            </li>
          </ul>
        </div>
        <button @click="searchApartment" class="bg-blue-500 text-white px-6 py-3 rounded-r text-lg">검색</button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { getAptNamesByPrefix } from '@/api/aptSaleApi';

const router = useRouter();
const searchInput = ref('');
const searchResults = ref([]);
const selectedApartment = ref(null);

const fetchSearchResults = async () => {
  if (searchInput.value.trim() !== '') {
    try {
      const response = await getAptNamesByPrefix(searchInput.value);
      searchResults.value = response.data;
    } catch (error) {
      console.error('Error fetching search results:', error);
    }
  } else {
    searchResults.value = [];
  }
};

watch(searchInput, fetchSearchResults);

const selectApartment = (apartment) => {
  searchInput.value = apartment.aptName;
  selectedApartment.value = apartment;
  searchResults.value = [];
};

const searchApartment = () => {
  if (selectedApartment.value) {
    router.push({
      name: 'KakaoMap',
      params: {
        lat: selectedApartment.value.latitude,
        lng: selectedApartment.value.longitude
      }
    });
  } else {
    console.log('아파트를 선택해주세요.');
  }
};
</script>