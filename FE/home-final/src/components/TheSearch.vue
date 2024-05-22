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
              :class="['flex justify-between items-center px-4 py-2 cursor-pointer', { 'bg-gray-200': highlightedIndex === index }]"
              @click="selectApartment(result)" @mouseover="highlightedIndex = index">
              <span>{{ result.aptName }} - {{ result.roadNameAddress }}</span>
              <button @click.stop="registerSale(result)" class="bg-green-500 text-white px-2 py-1 rounded">매물등록</button>
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
// import ApartmentMap from "@/components/ApartmentMap.vue"

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
  mapStore.setSelectedAptCode(apartment.aptCode);
  selectedAptCode.value = apartment.aptCode;
};

const selectHighlighted = () => {
  if (highlightedIndex.value !== -1 && searchResults.value.length > 0) {
    selectApartment(searchResults.value[highlightedIndex.value]);
    searchResults.value = []; // 엔터를 누르면 자동완성 리스트 숨기기
    searchApartment(); // 엔터를 누르면 아파트 정보 갱신
  }
};


const searchApartment = () => {
  if (selectedApartment.value) {
    localStorage.setItem('selectedApartment', JSON.stringify(selectedApartment.value));
    router.push({
      name: 'ApartmentMap',
      params: {
        aptCode: selectedApartment.value.aptCode,
        aptName: selectedApartment.value.aptName,
        latitude: selectedApartment.value.latitude,
        longitude: selectedApartment.value.longitude,
      },
    });
  }
};

const registerSale = (apartment) => {
  localStorage.setItem('selectedApartment', JSON.stringify(apartment));
  router.push({
    name: 'RegisterSale'
  });
};
</script>
