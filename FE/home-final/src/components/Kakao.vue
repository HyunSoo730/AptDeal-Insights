<template>
  <div class="flex h-screen">
    <div class="w-2/3 h-full">
      <KakaoMap :lat="lat" :lng="lng" class="h-full">
        <KakaoMapMarker v-if="marker" :lat="marker.latitude" :lng="marker.longitude" :clickable="true" @onClickKakaoMapMarker="onClickKakaoMapMarker(marker.aptCode)" />
      </KakaoMap>
    </div>
    <div class="w-1/3 ml-4 overflow-y-auto h-full">
      <div class="p-4">
        <AptTransactionChart v-if="selectedAptCode && dongcode" :aptCode="selectedAptCode" :dongcode="dongcode" />
        <ApartmentDetails v-if="selectedAptCode" :aptCode="selectedAptCode" />
        <SubwayStationDetails v-if="lat && lng" :lat="lat" :lng="lng" />
        <AptSchoolInfo v-if="selectedAptCode" :aptCode="selectedAptCode" :aptName="aptName" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import ApartmentDetails from '@/components/ApartmentDetails.vue';
import SubwayStationDetails from '@/components/SubwayStationDetails.vue';
import AptSchoolInfo from '@/components/AptSchoolInfo.vue';
import AptTransactionChart from '@/components/AptTransactionChart.vue';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const route = useRoute();
const lat = ref(null);
const lng = ref(null);
const marker = ref(null);
const selectedAptCode = ref(null);
const aptName = ref('');
const dongcode = ref('');

const setMarker = (apartment) => {
  console.log('Setting marker:', apartment);
  marker.value = {
    latitude: apartment.latitude,
    longitude: apartment.longitude,
    aptCode: apartment.aptCode,
  };
  lat.value = apartment.latitude;
  lng.value = apartment.longitude;
  selectedAptCode.value = apartment.aptCode;
  aptName.value = apartment.aptName;

  if (apartment.dongcode) {
    dongcode.value = apartment.dongcode.substring(0, 5);
    console.log('Dongcode set to:', dongcode.value);
    mapStore.setDongcode(dongcode.value);
  } else {
    dongcode.value = '';
    mapStore.setDongcode('');
  }
};

watch(
  () => route.query,
  (newQuery) => {
    console.log('Route query:', newQuery);
    if (newQuery.latitude && newQuery.longitude && newQuery.aptCode) {
      setMarker(newQuery);
    }
  },
  { immediate: true }
);

const onClickKakaoMapMarker = (aptCode) => {
  console.log('Kakao map marker clicked:', aptCode);
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

<style scoped>
.flex {
  display: flex;
}
.h-screen {
  height: 100vh;
}
.overflow-y-auto {
  overflow-y: auto;
}
.h-full {
  height: 100%;
}
.ml-4 {
  margin-left: 1rem;
}
.p-4 {
  padding: 1rem;
}
</style>
