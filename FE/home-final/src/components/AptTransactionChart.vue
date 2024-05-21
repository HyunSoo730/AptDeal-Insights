<template>
    <div>
      <h2 class="text-2xl font-bold mb-4">아파트 거래 정보</h2>
      <select v-model="selectedYears" @change="fetchTransactions">
        <option v-for="year in availableYears" :key="year" :value="year">{{ year }}년</option>
      </select>
      <Line :data="chartData" :options="chartOptions" />
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, watch } from 'vue';
  import { getTransactionsByDongcode, getTransactionsByAptCode } from '@/api/aptTransactionApi';
  import { Line } from 'vue-chartjs';
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale } from 'chart.js';
  
  // Register the components globally
  ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale);
  
  const props = defineProps({
    aptCode: {
      type: String,
      required: true
    },
    dongcode: {
      type: String,
      required: true
    }
  });
  
  const availableYears = ref([1, 2, 3, 4, 5]); // 사용할 수 있는 년도 옵션
  const selectedYears = ref(3); // 기본적으로 3년 선택
  
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
      console.log('Fetching transactions for dongcode:', props.dongcode, 'and aptCode:', props.aptCode);
      const [aptResponse, dongResponse] = await Promise.all([
        getTransactionsByAptCode(props.aptCode, selectedYears.value),
        getTransactionsByDongcode(props.dongcode, selectedYears.value)
      ]);
  
      const aptTransactions = aptResponse.data;
      const dongTransactions = dongResponse.data;
  
      chartData.value.labels = aptTransactions.map(t => t.transactionDate);
      chartData.value.datasets[0].data = aptTransactions.map(t => t.transactionAmount);
      chartData.value.datasets[1].data = dongTransactions.map(t => t.transactionAmount);
  
      console.log('Apt Transactions:', aptTransactions);
      console.log('Dong Transactions:', dongTransactions);
    } catch (error) {
      console.error('Failed to fetch transactions:', error);
    }
  };
  
  onMounted(fetchTransactions);
  watch(selectedYears, fetchTransactions); // 년도가 변경될 때마다 데이터를 다시 불러옴
  </script>
  