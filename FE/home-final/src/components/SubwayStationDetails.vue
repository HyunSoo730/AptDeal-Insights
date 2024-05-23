<template>
  <div v-if="stations.length" class="subway-stations mt-8 p-6 bg-white shadow-lg rounded-2xl">
    <h2 class="text-2xl font-bold mb-6 text-indigo-600 flex items-center">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-8 h-8 mr-2">
        <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zM6.262 6.072a8.25 8.25 0 1010.562-.766 4.5 4.5 0 01-1.318 1.357L14.25 7.5l.165.33a.809.809 0 01-1.086 1.085l-.604-.302a1.125 1.125 0 00-1.298.21l-.132.131c-.439.44-.439 1.152 0 1.591l.296.296c.256.257.622.374.98.314l1.17-.195c.323-.054.654.036.905.245l1.33 1.108c.32.267.46.694.358 1.1a8.7 8.7 0 01-2.288 4.04l-.723.724a1.125 1.125 0 01-1.298.21l-.153-.076a1.125 1.125 0 01-.622-1.006v-1.089c0-.298-.119-.585-.33-.796l-1.347-1.347a1.125 1.125 0 01-.21-1.298L9.75 12l-1.64-1.64a6 6 0 01-1.676-3.257l-.172-1.03z" clip-rule="evenodd" />
      </svg>
      가장 가까운 지하철역
    </h2>
    <ul class="space-y-4">
      <li v-for="(station, index) in stations" :key="index" class="flex items-center bg-gray-100 p-4 rounded-xl shadow-md">
        <div class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4">
          {{ index + 1 }}
        </div>
        <div>
          <h3 class="text-xl font-semibold text-gray-800">{{ station.stationName }}</h3>
          <p class="text-gray-600">{{ station.lineNum }}</p>
          <p class="text-sm text-gray-500 mt-1">거리: {{ station.distance.toFixed(2) }} km, 걷기 시간: {{ station.walkingTime }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, defineProps, defineEmits } from 'vue';
import { getClosestSubwayStations } from '@/api/subwayStationApi';

const props = defineProps({
  lat: Number,
  lng: Number
});

const emit = defineEmits(['update:stations']);

const stations = ref([]);

const fetchClosestSubwayStations = async () => {
  try {
    const response = await getClosestSubwayStations(props.lat, props.lng);
    console.log('Closest subway stations:', response.data);
    stations.value = response.data;
    emit('update:stations', stations.value);
  } catch (error) {
    console.error('Failed to fetch closest subway stations:', error);
  }
};

watch([() => props.lat, () => props.lng], fetchClosestSubwayStations, { immediate: true });

onMounted(fetchClosestSubwayStations);
</script>

<style scoped>
.subway-stations {
  font-family: 'Noto Sans KR', sans-serif;
}

.bg-white {
  background-color: #fff;
}

.shadow-lg {
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

.rounded-2xl {
  border-radius: 1rem;
}

.text-indigo-600 {
  color: #5a67d8;
}

.text-gray-800 {
  color: #2d3748;
}

.text-gray-600 {
  color: #718096;
}

.text-gray-500 {
  color: #a0aec0;
}

.bg-gray-100 {
  background-color: #f7fafc;
}

.rounded-xl {
  border-radius: 0.75rem;
}

.shadow-md {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.bg-indigo-500 {
  background-color: #667eea;
}
</style>