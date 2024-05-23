<template>
  <section class="container mx-auto py-12">
    <h1 class="text-4xl font-bold mb-8 text-center text-indigo-600">
      전월세 검색
    </h1>
    <div
      class="bg-white shadow-lg rounded-2xl p-8 mb-8 mx-auto w-full max-w-4xl"
    >
      <h2 class="text-3xl font-semibold mb-6 text-gray-800 text-center">
        아파트 검색
      </h2>
      <div class="mb-6">
        <label class="block mb-2 text-lg font-medium text-gray-700"
          >시/도 선택</label
        >
        <select
          v-model="selectedCity"
          @change="handleCityChange"
          class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
        >
          <option value="" disabled>시/도 선택</option>
          <option v-for="city in cities" :key="city.code" :value="city.code">
            {{ city.name }}
          </option>
        </select>
      </div>
      <div class="mb-6">
        <label class="block mb-2 text-lg font-medium text-gray-700"
          >구/군 선택</label
        >
        <select
          v-model="selectedGu"
          @change="handleGuChange"
          class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
        >
          <option value="" disabled>구/군 선택</option>
          <option v-for="gu in gus" :key="gu.code" :value="gu.code">
            {{ gu.name.split(" ")[1] }}
          </option>
        </select>
      </div>
      <div class="mb-6">
        <label class="block mb-2 text-lg font-medium text-gray-700"
          >동 선택</label
        >
        <select
          v-model="selectedDong"
          class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
        >
          <option value="" disabled>동 선택</option>
          <option v-for="dong in dongs" :key="dong.code" :value="dong.name">
            {{ dong.name.split(" ").pop() }}
          </option>
        </select>
      </div>
      <div class="mb-8">
        <label class="block mb-2 text-lg font-medium text-gray-700"
          >아파트명 입력</label
        >
        <input
          v-model="apartmentName"
          placeholder="아파트명 입력"
          class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
        />
      </div>
      <div class="mb-8">
        <h2 class="text-2xl font-semibold mb-4 text-gray-800">필터링 옵션</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >보증금 범위</label
            >
            <VueSimpleRangeSlider
              class="mt-2"
              :min="100"
              :max="100000"
              exponential
              v-model="depositRange"
            >
              <template #suffix="{ value }">만원</template>
            </VueSimpleRangeSlider>
          </div>
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >월세 범위</label
            >
            <VueSimpleRangeSlider
              class="mt-2"
              :min="0"
              :max="1000"
              v-model="monthlyRentRange"
            >
              <template #suffix="{ value }">만원</template>
            </VueSimpleRangeSlider>
          </div>
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >시작 날짜</label
            >
            <input
              type="date"
              v-model="startDate"
              class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
            />
          </div>
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >종료 날짜</label
            >
            <input
              type="date"
              v-model="endDate"
              class="w-full p-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
            />
          </div>
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >임대 유형</label
            >
            <div class="mt-2 space-y-2">
              <label class="flex items-center">
                <input
                  type="radio"
                  v-model="rentType"
                  value="all"
                  class="form-radio text-indigo-600"
                />
                <span class="ml-2 text-gray-700">전체</span>
              </label>
              <label class="flex items-center">
                <input
                  type="radio"
                  v-model="rentType"
                  value="deposit"
                  class="form-radio text-indigo-600"
                />
                <span class="ml-2 text-gray-700">전세</span>
              </label>
              <label class="flex items-center">
                <input
                  type="radio"
                  v-model="rentType"
                  value="monthly"
                  class="form-radio text-indigo-600"
                />
                <span class="ml-2 text-gray-700">반전세(월세)</span>
              </label>
            </div>
          </div>
          <div>
            <label class="block mb-2 text-lg font-medium text-gray-700"
              >평수 선택</label
            >
            <div class="mt-2 space-y-2">
              <div
                v-for="pyeong in pyeongOptions"
                :key="pyeong"
                class="flex items-center space-x-4"
              >
                <label class="inline-flex items-center">
                  <input
                    type="checkbox"
                    v-model="selectedPyeongRanges"
                    :value="pyeong"
                    class="form-checkbox text-indigo-600"
                  />
                  <span class="ml-2 text-gray-700">{{ pyeong }}평대</span>
                </label>
              </div>
            </div>
          </div>
        </div>
        <button
          @click="handleSearch"
          class="mt-8 w-full py-3 px-6 bg-indigo-600 hover:bg-indigo-700 rounded-lg text-lg font-semibold text-white transition duration-300"
        >
          검색
        </button>
      </div>
    </div>
    <div
      v-if="leaseListings.length"
      class="mt-12 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
    >
      <div
        v-for="lease in groupedListings"
        :key="lease.apartmentName"
        class="bg-white shadow-lg rounded-2xl p-6"
      >
        <h2 class="text-2xl font-semibold mb-4 text-gray-800">
          {{ lease.apartmentName }}
        </h2>
        <p class="text-lg text-gray-600">총 {{ lease.count }}건의 거래</p>
        <button
          @click="openModal(lease)"
          class="mt-6 w-full py-3 px-6 bg-indigo-600 hover:bg-indigo-700 rounded-lg text-lg font-semibold text-white transition duration-300"
        >
          상세보기
        </button>
      </div>
    </div>
    <div
      v-if="!loading && leaseListings.length && !allDataLoaded"
      class="mt-12 text-center"
    >
      <button
        @click="loadMoreListings"
        class="py-3 px-8 bg-indigo-600 hover:bg-indigo-700 rounded-lg text-lg font-semibold text-white transition duration-300"
      >
        더보기
      </button>
    </div>
    <div v-else-if="loading" class="mt-12 text-center">
      <p class="text-xl text-gray-600">데이터를 불러오는 중입니다...</p>
    </div>
    <ModalComponent
      v-if="isModalVisible"
      :isVisible="isModalVisible"
      :apartmentName="selectedApartment.apartmentName"
      :transactions="selectedApartment.transactions"
      :pyeongOptions="pyeongOptions"
      :searchCondition="searchCondition"
      :regionCode="selectedGu"
      :depositRange="depositRange" 
      :monthlyRentRange="monthlyRentRange" 
      @close="isModalVisible = false"
    />
  </section>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import {
  getLeaseListingsByRegionAndDong,
  getRentSalesByApartmentAndYears,
  getRentSalesByRegionCodeAndYears,
} from "@/api/aptLeaseApi";
import VueSimpleRangeSlider from "vue-simple-range-slider";
import "vue-simple-range-slider/css";
import ModalComponent from "@/components/ModalComponent.vue";

const selectedCity = ref("");
const selectedGu = ref("");
const selectedDong = ref("");
const apartmentName = ref("");

const cities = ref([]);
const gus = ref([]);
const dongs = ref([]);
const initialListings = ref([]);
const leaseListings = ref([]);

const depositRange = ref([100, 100000]);
const monthlyRentRange = ref([0, 1000]);
const startDate = ref("");
const endDate = ref("");
const rentType = ref("all");

const offset = ref(0);
const limit = ref(9);
const loading = ref(false);
const allDataLoaded = ref(false);

const pyeongOptions = [10, 20, 30, 40, 50, 60, 70];
const selectedPyeongRanges = ref<number[]>([]);
const yearOptions = [1, 2, 3, 4, 5];

const isModalVisible = ref(false);
const selectedApartment = ref({});

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
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${cityCode.substr(
        0,
        2
      )}*00000&is_ignore_zero=true`
    );
    gus.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch gus:", error);
  }
};

const fetchDongs = async (guCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${guCode.substr(
        0,
        5
      )}*&is_ignore_zero=true`
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

const handleSearch = async () => {
  if (selectedDong.value) {
    offset.value = 0;
    allDataLoaded.value = false;
    leaseListings.value = [];
    await loadMoreListings();
  }
};

const loadMoreListings = async () => {
  if (selectedDong.value && !loading.value && !allDataLoaded.value) {
    loading.value = true;
    const dongName = selectedDong.value.split(" ").pop();
    const regionCode = selectedGu.value.substr(0, 5);

    let isCharter = null;
    if (rentType.value === "deposit") {
      isCharter = true;
    } else if (rentType.value === "monthly") {
      isCharter = false;
    }

    const searchCondition = {
      regionCode,
      legalDong: dongName,
      minDeposit: depositRange.value[0],
      maxDeposit: depositRange.value[1],
      minMonthlyRent: monthlyRentRange.value[0],
      maxMonthlyRent: monthlyRentRange.value[1],
      startDate: startDate.value,
      endDate: endDate.value,
      isCharter,
      selectedPyeongRanges: selectedPyeongRanges.value,
      offset: offset.value,
      limit: limit.value,
      apartmentName: apartmentName.value,
    };

    try {
      const response = await getLeaseListingsByRegionAndDong(searchCondition);
      if (response.data.length < limit.value) {
        allDataLoaded.value = true;
      }
      leaseListings.value.push(
        ...response.data.map((lease) => ({
          ...lease,
          selectedYears: 3,
          chartsVisible: false,
          apartmentChartData: null,
          regionChartData: null,
        }))
      );
      offset.value += limit.value;
    } catch (error) {
      console.error("Failed to fetch lease listings:", error);
    } finally {
      loading.value = false;
    }
  }
};

const groupedListings = computed(() => {
  const groups = leaseListings.value.reduce((acc, lease) => {
    const { apartmentName } = lease;
    if (!acc[apartmentName]) {
      acc[apartmentName] = {
        apartmentName,
        count: 0,
        transactions: [],
      };
    }
    acc[apartmentName].count += 1;
    acc[apartmentName].transactions.push(lease);
    return acc;
  }, {});
  return Object.values(groups);
});

const openModal = (lease) => {
  selectedApartment.value = lease;
  isModalVisible.value = true;
};

const showCharts = async (lease) => {
  const years = lease.selectedYears;

  if (confirm(`${years}년 차트를 보시겠습니까?`)) {
    try {
      const [apartmentResponse, regionResponse] = await Promise.all([
        getRentSalesByApartmentAndYears(lease.apartmentName, years),
        getRentSalesByRegionCodeAndYears(selectedGu.value.substr(0, 5), years),
      ]);

      const apartmentData = apartmentResponse.data;
      const regionData = regionResponse.data;

      if (!apartmentData.length || !regionData.length) {
        console.error("No data available for charts");
        return;
      }

      lease.apartmentChartData = formatChartData(apartmentData);
      lease.regionChartData = formatChartData(regionData);
      lease.chartsVisible = true;
    } catch (error) {
      console.error("Failed to fetch chart data:", error);
    }
  }
};

const formatChartData = (data) => {
  if (!data || !data.length) return { labels: [], datasets: [] };

  const labels = data.map((item) => `${item.year}-${item.month}`);
  const depositData = data.map((item) => item.depositAmount || 0);
  const rentData = data.map((item) => item.monthlyRent || 0);

  return {
    labels,
    datasets: [
      {
        label: "보증금",
        data: depositData,
        borderColor: "rgba(75, 192, 192, 1)",
        backgroundColor: "rgba(75, 192, 192, 0.2)",
        borderWidth: 1,
      },
      {
        label: "월세",
        data: rentData,
        borderColor: "rgba(153, 102, 255, 1)",
        backgroundColor: "rgba(153, 102, 255, 0.2)",
        borderWidth: 1,
      },
    ],
  };
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap");

.container {
  max-width: 1200px;
  font-family: "Noto Sans KR", sans-serif;
}

h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #4c51bf;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 2rem;
  font-weight: 600;
  color: #2d3748;
}

p {
  font-size: 1.125rem;
  color: #4a5568;
}

label {
  font-size: 1.125rem;
  font-weight: 500;
  color: #2d3748;
}

input::placeholder,
select option:first-child {
  color: #a0aec0;
}

button {
  font-size: 1.25rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.text-xl {
  font-size: 1.25rem;
}

.text-lg {
  font-size: 1.125rem;
}

.font-medium {
  font-weight: 500;
}

.font-bold {
  font-weight: 700;
}

.range-slider {
  width: 100%;
}

.range-slider .range-slider__track {
  background: #e2e8f0;
  height: 4px;
}

.range-slider .range-slider__thumb {
  background: #4c51bf;
  border: none;
  width: 16px;
  height: 16px;
}

.range-slider .range-slider__thumb:focus {
  outline: none;
  box-shadow: 0 0 0 4px rgba(76, 81, 191, 0.3);
}
</style>
