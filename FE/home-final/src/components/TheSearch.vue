<template>
  <section class="bg-gray-50 py-8 relative">
    <div class="background-image"></div>
    <div class="container mx-auto relative z-10">
      <h2 class="text-3xl font-bold mb-8 text-center text-white  bg-opacity-50  rounded">
        어떤 집을 찾고 계세요?
      </h2>
      <div class="flex justify-center relative">
        <div class="w-full max-w-lg relative" ref="searchBox">
          <div class="relative">
            <input type="text" v-model="searchInput" placeholder="예) 동, 아파트명, 학교명"
              class="w-full pl-4 pr-12 py-3 rounded-lg border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-800 transition duration-300"
              @keydown.down.prevent="highlightedIndex < searchResults.length - 1 ? highlightedIndex++ : null"
              @keydown.up.prevent="highlightedIndex > 0 ? highlightedIndex-- : null"
              @keydown.enter.prevent="selectHighlighted" />
            <button @click="searchApartment"
              class="absolute top-0 right-0 bg-indigo-500 text-white px-4 py-3 rounded-r-lg text-lg hover:bg-indigo-600 transition duration-300">
              검색
            </button>
          </div>
          <ul v-if="searchResults.length > 0"
            class="absolute top-full left-0 right-0 w-full mt-2 bg-white border border-gray-200 rounded-b-lg shadow-lg z-10 overflow-hidden">
            <li v-for="(result, index) in searchResults" :key="result.aptCode"
              :class="['flex justify-between items-center px-4 py-3 text-gray-800 cursor-pointer transition duration-300 hover:bg-indigo-50', { 'bg-indigo-100': highlightedIndex === index }]"
              @click="selectApartmentAndSearch(result)" @mouseover="highlightedIndex = index">
              <span class="font-medium">{{ result.aptName }}</span>
              <span class="text-gray-600 text-sm ml-2">{{ result.roadNameAddress }}</span>
              <button @click.stop="registerSale(result)"
                class="ml-4 bg-green-500 text-white px-3 py-1 rounded-md text-sm hover:bg-green-600 transition duration-300">
                매물등록
              </button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
</template>


<script setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';
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
const searchBox = ref(null);

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

const selectApartmentAndSearch = (apartment) => {
  selectApartment(apartment);
  searchApartment();
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

const hideSearchResults = () => {
  searchResults.value = [];
};

const handleClickOutside = (event) => {
  if (searchBox.value && !searchBox.value.contains(event.target)) {
    hideSearchResults();
  }
};

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<style scoped>
.bg-gray-50 {
  background-color: #f9fafb;
}

.text-indigo-600 {
  color: #5a67d8;
}

.border-gray-300 {
  border-color: #d2d6dc;
}

.focus\:border-indigo-500:focus {
  border-color: #667eea;
}

.hover\:bg-indigo-50:hover {
  background-color: #ebf4ff;
}

.hover\:bg-indigo-600:hover {
  background-color: #4c51bf;
}

.bg-indigo-500 {
  background-color: #667eea;
}

.text-gray-600 {
  color: #718096;
}

.bg-gray-200 {
  background-color: #edf2f7;
}

.bg-green-500 {
  background-color: #48bb78;
}

.hover\:bg-green-600:hover {
  background-color: #38a169;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('https://www.zillowstatic.com/bedrock/app/uploads/sites/5/2024/04/homepage-hero-xl%401x.jpg');
  background-size: cover;
  background-position: center;
  z-index: 0;
  /* Adjust opacity as needed */
}

.container {
  position: relative;
  z-index: 10;
}
</style>
