<template>
  <div class="container">
    <!-- {{ apartments }} -->
    <div class="map-container">
      <KakaoMap :lat=selectedApartment.latitude :lng=selectedApartment.longitude>
        <KakaoMapMarker v-for="apartment in apartments" :key="apartment.aptCode" :lat=apartment.latitude
          :lng=apartment.longitude :clickable="true" @onClickKakaoMapMarker="showApartmentDetail(apartment)" />
      </KakaoMap>
    </div>
    <div v-if="selectedApartment" class="detail-container">
      <h2>아파트 상세 정보</h2>
      <div>
        <!-- {{ selectedApartment }} -->
        <h3>아파트 이름: {{ selectedApartment.aptName }}</h3>
        <p>거래 금액: {{ selectedApartment.dealAmount }}</p>
        <p>건축년도: {{ selectedApartment.constructionYear }}</p>
        <p>주소: {{ selectedApartment.roadName }}</p>
        <p>위도: {{ selectedApartment.latitude }}</p>
        <p>경도: {{ selectedApartment.longitude }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { useRoute } from 'vue-router';

const route = useRoute();
const apartments = ref(JSON.parse(route.params.apartments || '[]'));
const initialApartment = ref(JSON.parse(route.params.initialApartment || 'null'));

const selectedApartment = ref(initialApartment.value);
const mapCenter = computed(() => ({
  lat: selectedApartment.value?.latitude || 37.5665,
  lng: selectedApartment.value?.longitude || 126.9780,
}));

const showApartmentDetail = (apartment) => {
  console.log("클릭됨!")
  selectedApartment.value = apartment;
};
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
}

.map-container {
  flex: 7;
  height: 100%;
}

.detail-container {
  flex: 3;
  padding: 20px;
  overflow-y: auto;
}
</style>