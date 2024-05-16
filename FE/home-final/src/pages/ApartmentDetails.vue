<script setup>
import { ref } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

const selectedApartment = ref(null);
const apartments = ref([
  // 아파트 데이터를 배열로 저장
]);

const selectApartment = (apartment) => {
  selectedApartment.value = apartment;
};
</script>

<template>
  <div class="container mx-auto py-12">
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
      <div>
        <KakaoMap :center="{ lat: 37.566826, lng: 126.9786567 }" :level="3" class="w-full h-[600px]">
          <KakaoMapMarker
            v-for="apartment in apartments"
            :key="apartment.id"
            :position="apartment.position"
            @click="selectApartment(apartment)"
          ></KakaoMapMarker>
        </KakaoMap>
      </div>
      <div>
        <div v-if="selectedApartment" class="bg-white shadow-md rounded-lg p-6">
          <h2 class="text-2xl font-bold mb-4">{{ selectedApartment.name }}</h2>
          <p class="text-gray-700 mb-4">주소: {{ selectedApartment.address }}</p>
          <p class="text-gray-700 mb-4">건축년도: {{ selectedApartment.buildYear }}</p>
          <p class="text-gray-700 mb-4">세대수: {{ selectedApartment.numberOfHouseholds }}</p>
          <!-- 추가적인 아파트 상세 정보 표시 -->
        </div>
        <div v-else class="bg-white shadow-md rounded-lg p-6">
          <p class="text-gray-700">아파트를 선택하면 상세 정보가 여기에 표시됩니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>