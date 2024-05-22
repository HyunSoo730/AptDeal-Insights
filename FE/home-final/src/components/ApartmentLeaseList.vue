<template>
    <section class="container mx-auto py-12">
      <h1 class="text-3xl font-bold mb-8 text-center">전월세 검색</h1>
      <div class="bg-white shadow-md rounded-lg p-8 mb-8 max-w-xl mx-auto">
        <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">아파트 검색</h2>
        <div class="mb-4">
          <select v-model="selectedCity" @change="handleCityChange"
            class="w-full p-3 rounded-t border border-gray-400 focus:outline-none focus:border-blue-500 text-lg">
            <option value="" disabled>시/도 선택</option>
            <option v-for="city in cities" :key="city.code" :value="city.code">
              {{ city.name }}
            </option>
          </select>
        </div>
        <div class="mb-4">
          <select v-model="selectedGu" @change="handleGuChange"
            class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg">
            <option value="" disabled>구/군 선택</option>
            <option v-for="gu in gus" :key="gu.code" :value="gu.code">
              {{ gu.name.split(" ")[1] }}
            </option>
          </select>
        </div>
        <div class="mb-4">
          <select v-model="selectedDong"
            class="w-full p-3 rounded-b border border-gray-400 focus:outline-none focus:border-blue-500 text-lg">
            <option value="" disabled>동 선택</option>
            <option v-for="dong in dongs" :key="dong.code" :value="dong.name">
              {{ dong.name.split(" ").pop() }}
            </option>
          </select>
        </div>
        <button @click="fetchInitialListings" class="bg-blue-500 text-white px-6 py-3 rounded-lg text-lg w-full mb-6">
          초기 검색
        </button>
        <div v-if="initialListings.length" class="mb-8">
          <h2 class="text-xl font-bold mb-4">필터링 옵션</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <label class="w-full p-3 text-lg">보증금 범위</label>
            <VueSimpleRangeSlider
              style="width: 100%"
              :min="0"
              :max="1000000000"
              exponential
              v-model="depositRange"
            >
              <template #suffix="{ value }">만원</template>
            </VueSimpleRangeSlider>
            <label class="w-full p-3 text-lg">월세 범위</label>
            <VueSimpleRangeSlider
              style="width: 100%"
              :min="0"
              :max="100000"
              v-model="monthlyRentRange"
            >
              <template #suffix="{ value }">만원</template>
            </VueSimpleRangeSlider>
            <input type="number" v-model="minExclusiveArea" placeholder="최소 전용 면적" 
              class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg" />
            <input type="number" v-model="maxExclusiveArea" placeholder="최대 전용 면적" 
              class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg" />
            <input type="date" v-model="startDate" placeholder="시작 날짜" 
              class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg" />
            <input type="date" v-model="endDate" placeholder="종료 날짜" 
              class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg" />
          </div>
          <button @click="filterListings" class="bg-blue-500 text-white px-6 py-3 rounded-lg text-lg w-full mt-4">
            필터링
          </button>
        </div>
      </div>
      <div v-if="leaseListings.length" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div v-for="lease in leaseListings" :key="lease.id" class="bg-white shadow-md rounded-lg p-6">
          <h2 class="text-xl font-bold mb-4">{{ lease.apartmentName }}</h2>
          <p>임대 유형: {{ lease.contractType }}</p>
          <p>임대 금액: {{ lease.depositAmount }}만원</p>
          <p>월세: {{ lease.monthlyRent }}만원</p>
          <p>주소: {{ lease.legalDong }}</p>
          <p>건축년도: {{ lease.constructionYear }}</p>
          <p>층: {{ lease.floor }}</p>
        </div>
      </div>
      <div v-else>
        <p>데이터를 불러오는 중입니다...</p>
      </div>
    </section>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from "vue";
  import axios from "axios";
  import { getLeaseListingsByRegionAndDong } from '@/api/aptLeaseApi';
  import VueSimpleRangeSlider from "vue-simple-range-slider";
  import "vue-simple-range-slider/css";
  
  const selectedCity = ref("");
  const selectedGu = ref("");
  const selectedDong = ref("");
  
  const cities = ref([]);
  const gus = ref([]);
  const dongs = ref([]);
  const initialListings = ref([]);
  const leaseListings = ref([]);
  
  const depositRange = ref([0, 1000000000]); // 보증금 범위
  const monthlyRentRange = ref([0, 100000]); // 월세 범위
  const minExclusiveArea = ref("");
  const maxExclusiveArea = ref("");
  const startDate = ref("");
  const endDate = ref("");
  
  onMounted(() => {
    fetchCities();
  });
  
  const fetchCities = async () => {
    try {
      const response = await axios.get(
        "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000"
      );
      cities.value = response.data.regcodes;
    } catch (error) {
      console.error("Failed to fetch cities:", error);
    }
  };
  
  const fetchGus = async (cityCode) => {
    try {
      const response = await axios.get(
        `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${cityCode.substr(0, 2)}*00000&is_ignore_zero=true`
      );
      gus.value = response.data.regcodes;
    } catch (error) {
      console.error("Failed to fetch gus:", error);
    }
  };
  
  const fetchDongs = async (guCode) => {
    try {
      const response = await axios.get(
        `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${guCode.substr(0, 5)}*&is_ignore_zero=true`
      );
      dongs.value = response.data.regcodes;
    } catch (error) {
      console.error("Failed to fetch dongs:", error);
    }
  };
  
  const handleCityChange = () => {
    if (selectedCity.value) {
      fetchGus(selectedCity.value);
    } else {
      gus.value = [];
      selectedGu.value = "";
    }
  };
  
  const handleGuChange = () => {
    if (selectedGu.value) {
      fetchDongs(selectedGu.value);
    } else {
      dongs.value = [];
      selectedDong.value = "";
    }
  };
  
  const fetchInitialListings = async () => {
    if (selectedDong.value) {
      const dongName = selectedDong.value.split(" ").pop();
      const regionCode = selectedGu.value.substr(0, 5);
  
      const searchCondition = {
        regionCode,
        legalDong: dongName
      };
  
      try {
        const response = await getLeaseListingsByRegionAndDong(searchCondition);
        initialListings.value = response.data;
        leaseListings.value = response.data;
      } catch (error) {
        console.error("Failed to fetch initial lease listings:", error);
      }
    }
  };
  
  const filterListings = async () => {
    if (selectedDong.value) {
      const dongName = selectedDong.value.split(" ").pop();
      const regionCode = selectedGu.value.substr(0, 5);
  
      const searchCondition = {
        regionCode,
        legalDong: dongName,
        minDeposit: depositRange.value[0],
        maxDeposit: depositRange.value[1],
        minMonthlyRent: monthlyRentRange.value[0],
        maxMonthlyRent: monthlyRentRange.value[1],
        minExclusiveArea: minExclusiveArea.value,
        maxExclusiveArea: maxExclusiveArea.value,
        startDate: startDate.value,
        endDate: endDate.value,
      };
  
      try {
        const response = await getLeaseListingsByRegionAndDong(searchCondition);
        leaseListings.value = response.data;
      } catch (error) {
        console.error("Failed to fetch filtered lease listings:", error);
      }
    }
  };
  </script>
  
  <style scoped>
  @import 'vue-simple-range-slider/css';
  .container {
    max-width: 1200px;
  }
  .text-center {
    text-align: center;
  }
  .bg-white {
    background-color: #fff;
  }
  .p-8 {
    padding: 2rem;
  }
  .shadow-md {
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  .rounded-lg {
    border-radius: 0.5rem;
  }
  .text-3xl {
    font-size: 1.875rem;
  }
  .font-bold {
    font-weight: 700;
  }
  .mb-8 {
    margin-bottom: 2rem;
  }
  .mb-4 {
    margin-bottom: 1rem;
  }
  .text-xl {
    font-size: 1.25rem;
  }
  .grid {
    display: grid;
  }
  .grid-cols-1 {
    grid-template-columns: repeat(1, 1fr);
  }
  .md\:grid-cols-2 {
    grid-template-columns: repeat(2, 1fr);
  }
  .lg\:grid-cols-3 {
    grid-template-columns: repeat(3, 1fr);
  }
  .gap-8 {
    gap: 2rem;
  }
  </style>
  