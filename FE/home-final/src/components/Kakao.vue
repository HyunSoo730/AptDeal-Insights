<template>
  <div class="flex">
    <div class="w-2/3 kakao-map">
      <KakaoMap :lat="lat" :lng="lng">
        <KakaoMapMarker
          v-if="marker"
          :lat="marker.latitude"
          :lng="marker.longitude"
          :clickable="true"
          @onClickKakaoMapMarker="onClickKakaoMapMarker(marker.aptCode)"
        />
      </KakaoMap>
    </div>
    <div class="w-1/3 ml-4">
      <ApartmentDetails v-if="selectedAptCode" :aptCode="selectedAptCode" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import ApartmentDetails from '@/components/ApartmentDetails.vue';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const route = useRoute();
const lat = ref(mapStore.lat);
const lng = ref(mapStore.lng);
const marker = ref(null); // 마커 하나만 관리합니다.
const selectedAptCode = ref(null);

const setMarker = (apartment) => {
  marker.value = {
    latitude: apartment.latitude,
    longitude: apartment.longitude,
    aptCode: apartment.aptCode,
  };
  lat.value = apartment.latitude;
  lng.value = apartment.longitude;
  selectedAptCode.value = apartment.aptCode;
};

watch(() => route.query, (newQuery) => {
  if (newQuery.latitude && newQuery.longitude && newQuery.aptCode) {
    setMarker(newQuery);
  }
}, { immediate: true });

const onClickKakaoMapMarker = (aptCode) => {
  mapStore.setSelectedAptCode(aptCode);
  selectedAptCode.value = aptCode;
};
</script>

<style scoped>
.kakao-map {
  width: 100%;
  height: 100%;
  min-height: 400px;
}
</style>
