<template>
  <div>
    <h2 class="text-2xl font-bold mb-4">{{ apartment.aptName }} 거래 정보</h2>
    <select v-model="selectedYears" @change="fetchTransactions" class="mb-4 p-2 border rounded">
      <option v-for="year in availableYears" :key="year" :value="year">{{ year }}년</option>
    </select>
    <button @click="openModal" class="mb-4 p-2 border rounded bg-blue-500 text-white">차트 크게 보기</button>
    <Line :data="chartData" :options="chartOptions" />
    
    <div v-if="isModalOpen" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div class="bg-white p-6 rounded-lg shadow-lg w-4/5 h-4/5 overflow-auto">
        <button @click="closeModal" class="mb-4 p-2 border rounded bg-red-500 text-white">닫기</button>
        <Line :data="chartData" :options="chartOptions" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { getTransactionsByDongcode, getTransactionsByAptCode } from '@/api/aptTransactionApi';
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale);

const props = defineProps({
  aptCode: {
    type: String,
    required: true
  },
  dongcode: {
    type: String,
    required: true
  },
  apartment: {
    type: Object,
    required: true
  }
});

const availableYears = ref([1, 2, 3, 4, 5]);
const selectedYears = ref(5);
const isModalOpen = ref(false);

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: '아파트 거래 금액 (만원)',
      data: [],
      borderColor: 'rgba(75, 192, 192, 1)',
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
    },
    {
      label: '구 거래 금액 (만원)',
      data: [],
      borderColor: 'rgba(255, 99, 132, 1)',
      backgroundColor: 'rgba(255, 99, 132, 0.2)',
    }
  ]
});

const chartOptions = {
  responsive: true,
  plugins: {
    legend: {
      position: 'top',
    },
    title: {
      display: true,
      text: '아파트 거래 정보'
    }
  }
};

const fetchTransactions = async () => {
  try {
    const [aptResponse, dongResponse] = await Promise.all([
      getTransactionsByAptCode(props.aptCode, selectedYears.value),
      getTransactionsByDongcode(props.dongcode, selectedYears.value)
    ]);

    const aptTransactions = aptResponse.data;
    const dongTransactions = dongResponse.data;

    chartData.value = {
      labels: aptTransactions.map(t => t.transactionDate),
      datasets: [
        {
          label: '아파트 거래 금액 (만원)',
          data: aptTransactions.map(t => t.transactionAmount),
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
        },
        {
          label: '구 거래 금액 (만원)',
          data: dongTransactions.map(t => t.transactionAmount),
          borderColor: 'rgba(255, 99, 132, 1)',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
        }
      ]
    };
  } catch (error) {
    console.error('Failed to fetch transactions:', error);
  }
};

const openModal = () => {
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

onMounted(fetchTransactions);
watch([() => props.aptCode, () => props.dongcode, selectedYears], fetchTransactions);
</script>

<style scoped>
/* 스타일을 여기 추가할 수 있습니다 */
</style>
