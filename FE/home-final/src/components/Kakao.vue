<template>
  <div class="flex">
    <div class="w-2/3">
      <KakaoMap :lat="lat" :lng="lng">
        <KakaoMapMarker :lat="lat" :lng="lng" />
      </KakaoMap>
    </div>
    <div class="w-1/3 ml-4">
      <!-- 아파트 상세 정보 컴포넌트 자리 -->
    </div>
  </div>
</template>

<script setup>
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const lat = mapStore.lat;
const lng = mapStore.lng;
</script>

<style scoped>
.kakao-map {
  width: 100%;
  height: 100%;
  min-height: 400px;
}
</style>
