<template>
    <div v-if="stations.length" class="mt-6 p-4 bg-white shadow rounded-lg">
      <h2 class="text-lg font-semibold mb-4">가장 가까운 지하철역</h2>
      <ul class="list-disc pl-5 space-y-2">
        <li v-for="(station, index) in stations" :key="index" class="text-sm">
          {{ station.stationName }} - {{ station.lineNum }} (거리: {{ station.distance.toFixed(2) }} km, 걷기 시간: {{ station.walkingTime }})
        </li>
      </ul>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { getClosestSubwayStations } from '@/api/subwayStationApi';
  
  const props = defineProps({
    lat: Number,
    lng: Number
  });
  
  const stations = ref([]);
  
  const fetchClosestSubwayStations = async () => {
    try {
      const response = await getClosestSubwayStations(props.lat, props.lng);
      console.log('Closest subway stations:', response.data); // 로그 추가
      stations.value = response.data;
    } catch (error) {
      console.error('Failed to fetch closest subway stations:', error);
    }
  };
  
  onMounted(fetchClosestSubwayStations);
  </script>
  
  <style scoped>
  /* 스타일을 여기 추가할 수 있습니다 */
  </style>
  