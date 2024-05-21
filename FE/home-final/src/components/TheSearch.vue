<template>
  <section class="bg-gray-100 py-1">
    <div class="container mx-auto">
      <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">어떤 집을 찾고 계세요?</h2>
      <div class="flex justify-center relative">
        <div class="w-1/2 relative">
          <input type="text" v-model="searchInput" placeholder="예) 동, 아파트명, 학교명"
            class="w-full p-3 rounded-l border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
            @keydown.down.prevent="highlightedIndex < searchResults.length - 1 ? highlightedIndex++ : null"
            @keydown.up.prevent="highlightedIndex > 0 ? highlightedIndex-- : null"
            @keydown.enter.prevent="selectHighlighted" />
          <ul v-if="searchResults.length > 0"
            class="absolute top-full left-0 right-0 w-full mt-2 bg-white border border-gray-400 rounded shadow-md z-10">
            <li v-for="(result, index) in searchResults" :key="result.aptCode"
              :class="['px-4 py-2 cursor-pointer', { 'bg-gray-200': highlightedIndex === index }]"
              @click="selectApartment(result)" @mouseover="highlightedIndex = index">
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
import { useMapStore } from '@/stores/mapStore';

const router = useRouter();
const searchInput = ref('');
const searchResults = ref([]);
const highlightedIndex = ref(-1);
const selectedApartment = ref(null);
const mapStore = useMapStore();
const selectedAptCode = ref(mapStore.selectedAptCode);

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
  highlightedIndex.value = -1;
};

const selectHighlighted = () => {
  if (highlightedIndex.value !== -1 && searchResults.value.length > 0) {
    selectApartment(searchResults.value[highlightedIndex.value]);
  }
};

const searchApartment = () => {
  if (selectedApartment.value) {
    mapStore.setCoordinates(selectedApartment.value.latitude, selectedApartment.value.longitude);
    mapStore.setSelectedAptCode(selectedApartment.value.aptCode);
    router.push({
      name: 'KakaoMap',
      query: {
        latitude: selectedApartment.value.latitude,
        longitude: selectedApartment.value.longitude,
        aptCode: selectedApartment.value.aptCode,
        aptName: selectedApartment.value.aptName,
        dongcode: selectedApartment.value.dongcode // dongcode 추가
      },
    });
  } else {
    console.log('아파트를 선택해주세요.');
  }
};
</script>
