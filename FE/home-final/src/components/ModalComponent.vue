<template>
  <div
    v-if="isVisible"
    class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center overflow-y-auto"
  >
    <div
      class="relative bg-white w-3/4 max-w-3xl mx-auto p-5 border shadow-lg rounded-md"
    >
      <div class="mt-3 text-center">
        <h3 class="text-3xl font-bold leading-6 text-gray-900 mb-4">
          {{ apartmentName }}
        </h3>
        <div class="mt-2 px-7 py-3">
          <p class="text-lg text-gray-600 mb-4">평수별 거래 정보</p>
          <div class="mb-4">
            <select
              v-model="selectedPyeong"
              @change="fetchFilteredTransactions"
              class="w-full p-3 border border-gray-400 rounded text-lg text-gray-700 font-medium"
            >
              <option value="" disabled>평수 선택</option>
              <option
                v-for="pyeong in pyeongOptions"
                :key="pyeong"
                :value="pyeong"
              >
                {{ pyeong }}평대
              </option>
            </select>
          </div>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div
              v-for="transaction in displayedTransactions"
              :key="transaction.id"
              class="bg-gray-100 p-4 rounded-lg shadow"
            >
              <p class="text-lg font-bold mb-2">
                {{ transaction.apartmentName }} -
                {{ transaction.exclusiveArea }}㎡
              </p>
              <p class="text-gray-700">
                보증금: {{ transaction.depositAmount }}만원
              </p>
              <p class="text-gray-700" v-if="transaction.monthlyRent > 0">
                월세: {{ transaction.monthlyRent }}만원
              </p>
              <p class="text-gray-700" v-else>전세</p>
              <p class="text-gray-700">
                건축년도: {{ transaction.constructionYear }}
              </p>
              <p class="text-gray-700">계약년도: {{ transaction.year }}</p>
              <p class="text-gray-700">계약월: {{ transaction.month }}</p>
              <p class="text-gray-700">계약일: {{ transaction.day }}</p>
              <p class="text-gray-700">층수: {{ transaction.floor }}층</p>
            </div>
          </div>
          <button
            v-if="displayedTransactions.length < filteredTransactions.length"
            @click="loadMore"
            class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded mt-4 transition duration-300"
          >
            더보기
          </button>
          <button
            @click="showCharts"
            class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded mt-4 transition duration-300"
          >
            차트보기
          </button>
        </div>
        <div class="items-center px-4 py-3">
          <button
            @click="closeModal"
            class="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded transition duration-300"
          >
            닫기
          </button>
        </div>
        <div v-if="chartsVisible" class="mt-8">
          <ChartComponent
            :chart-data="apartmentChartData"
            :options="{
              title: {
                display: true,
                text: '아파트 전월세 거래 차트',
                fontSize: 18,
              },
            }"
          />
          <ChartComponent
            :chart-data="regionChartData"
            :options="{
              title: {
                display: true,
                text: '해당 구 전월세 거래 차트',
                fontSize: 18,
              },
            }"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from "vue";
import ChartComponent from "@/components/ChartComponent.vue";
import {
  getLeaseListingsByRegionAndDong2,
  getRentSalesByApartmentAndYears,
  getRentSalesByRegionCodeAndYears,
} from "@/api/aptLeaseApi";

export default defineComponent({
  name: "ModalComponent",
  components: { ChartComponent },
  props: {
    isVisible: Boolean,
    apartmentName: String,
    transactions: Array,
    pyeongOptions: Array,
    searchCondition: Object,
    regionCode: String,
  },
  setup(props, { emit }) {
    const selectedPyeong = ref("");
    const filteredTransactions = ref([]);
    const displayedTransactions = ref([]);
    const transactionsToShow = ref(6); // 한 번에 더 많은 거래 정보를 보여주기 위해 조정
    const chartsVisible = ref(false);
    const apartmentChartData = ref(null);
    const regionChartData = ref(null);

    watch(
      () => props.transactions,
      () => {
        loadInitialTransactions();
      }
    );

    const fetchFilteredTransactions = async () => {
      const updatedSearchCondition = {
        ...props.searchCondition,
        apartmentName: props.apartmentName, // 아파트명 필터 추가
        selectedPyeongRanges: [selectedPyeong.value],
        offset: 0, // Reset offset for new search
        limit: 20, // Limit results per request
      };
      try {
        const response = await getLeaseListingsByRegionAndDong2(
          updatedSearchCondition
        );
        filteredTransactions.value = response.data;
        loadInitialTransactions();
      } catch (error) {
        console.error("Failed to fetch filtered transactions:", error);
      }
    };

    const loadInitialTransactions = () => {
      displayedTransactions.value = filteredTransactions.value.slice(
        0,
        transactionsToShow.value
      );
    };

    const loadMore = () => {
      const currentLength = displayedTransactions.value.length;
      const additionalTransactions = filteredTransactions.value.slice(
        currentLength,
        currentLength + transactionsToShow.value
      );
      displayedTransactions.value.push(...additionalTransactions);
    };

    const showCharts = async () => {
      const years = 3; // 예시로 3년 설정
      try {
        const [apartmentResponse, regionResponse] = await Promise.all([
          getRentSalesByApartmentAndYears(props.apartmentName, years),
          getRentSalesByRegionCodeAndYears(props.regionCode, years),
        ]);

        apartmentChartData.value = formatChartData(apartmentResponse.data);
        regionChartData.value = formatChartData(regionResponse.data);
        chartsVisible.value = true;
      } catch (error) {
        console.error("Failed to fetch chart data:", error);
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

    const closeModal = () => {
      emit("close");
      chartsVisible.value = false;
    };

    return {
      selectedPyeong,
      filteredTransactions,
      displayedTransactions,
      transactionsToShow,
      chartsVisible,
      apartmentChartData,
      regionChartData,
      fetchFilteredTransactions,
      loadInitialTransactions,
      loadMore,
      showCharts,
      closeModal,
    };
  },
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap");

.modal-content {
  font-family: "Noto Sans KR", sans-serif;
}

h3 {
  font-size: 2rem;
  font-weight: 700;
  color: #1a202c;
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

button {
  font-size: 1.125rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
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

/* ... (기존 스타일 유지) ... */
.bg-gray-600 {
  background-color: rgba(75, 85, 99, 0.5);
}

.bg-white {
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 2px 4px rgba(0, 0, 0, 0.06);
}

.text-gray-900 {
  color: #1a202c;
}

.text-gray-600 {
  color: #4a5568;
}

.text-gray-700 {
  color: #2d3748;
}

.bg-gray-100 {
  background-color: #f7fafc;
}

.bg-green-500 {
  background-color: #48bb78;
}

.hover\:bg-green-600:hover {
  background-color: #38a169;
}

.bg-red-500 {
  background-color: #f56565;
}

.hover\:bg-red-600:hover {
  background-color: #e53e3e;
}

.shadow {
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
}

.rounded-lg {
  border-radius: 0.5rem;
}

.transition {
  transition-property: background-color, border-color, color, fill, stroke,
    opacity, box-shadow, transform;
  transition-duration: 300ms;
}
</style>
