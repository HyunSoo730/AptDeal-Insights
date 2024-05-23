<template>
    <div>
      <h2 class="text-2xl font-bold mb-4">{{apartment.aptName}} 거래 정보</h2>
      <select v-model="selectedYears" @change="fetchTransactions" class="mb-4 p-2 border rounded">
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
  const selectedYears = ref(3);
  
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
  
      // 데이터가 올바르게 업데이트되는지 확인하는 로그 추가
      console.log('Apt Transactions:', aptTransactions);
      console.log('Dong Transactions:', dongTransactions);
  
      // 데이터 업데이트
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
  
      // 업데이트된 chartData 확인 로그
      console.log('Updated chartData:', JSON.parse(JSON.stringify(chartData.value)));
    } catch (error) {
      console.error('Failed to fetch transactions:', error);
    }
  };
  
  onMounted(fetchTransactions);
  watch([() => props.aptCode, () => props.dongcode, selectedYears], fetchTransactions);
  </script>
  
  <style scoped>
  /* 스타일을 여기 추가할 수 있습니다 */
  </style>
  