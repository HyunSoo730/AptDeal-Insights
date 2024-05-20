<template>
  <div class="bg-white p-6 shadow-md rounded-lg">
    <h2 class="text-2xl font-bold mb-4">아파트 상세 정보</h2>
    <div v-if="details.length">
      <div v-for="(detail, index) in displayedDetails" :key="index" class="mb-4">
        <h3 class="text-xl font-semibold">{{ detail.aptName }}</h3>
        <p>거래 금액: {{ detail.dealAmount }}원</p>
        <p>건축년도: {{ detail.constructionYear }}</p>
        <p>주소: {{ detail.roadNameAddress }}</p>
        <p>위도: {{ detail.latitude }}</p>
        <p>경도: {{ detail.longitude }}</p>
        <hr class="my-2">
      </div>
      <button
        v-if="!allDetailsShown"
        @click="loadMore"
        class="bg-blue-500 text-white px-4 py-2 rounded-lg"
      >
        더보기
      </button>
    </div>
    <div v-else>
      <p>데이터를 불러오는 중입니다...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getAptSaleDetails } from '@/api/aptSaleApi';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const aptCode = ref(mapStore.selectedAptCode);

const details = ref([]);
const displayedDetails = ref([]);
const allDetailsShown = ref(false);

const fetchAptSaleDetails = async (aptCode) => {
  try {
    const response = await getAptSaleDetails(aptCode);
    details.value = response.data;
    loadMore();
  } catch (error) {
    console.error('Failed to fetch apartment details:', error);
  }
};

const loadMore = () => {
  const currentLength = displayedDetails.value.length;
  const nextItems = details.value.slice(currentLength, currentLength + 3);
  if (nextItems.length > 0) {
    displayedDetails.value.push(...nextItems);
  }
  if (displayedDetails.value.length === details.value.length) {
    allDetailsShown.value = true;
  }
};

watch(() => mapStore.selectedAptCode, (newAptCode) => {
  if (newAptCode) {
    aptCode.value = newAptCode;
    fetchAptSaleDetails(newAptCode);
  }
}, { immediate: true });
</script>

<style scoped>
.bg-white {
  background-color: #fff;
}
.p-6 {
  padding: 1.5rem;
}
.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.rounded-lg {
  border-radius: 0.5rem;
}
.text-2xl {
  font-size: 1.5rem;
}
.font-bold {
  font-weight: 700;
}
.mb-4 {
  margin-bottom: 1rem;
}
.text-xl {
  font-size: 1.25rem;
}
.font-semibold {
  font-weight: 600;
}
.my-2 {
  margin: 0.5rem 0;
}
.bg-blue-500 {
  background-color: #3b82f6;
}
.text-white {
  color: #fff;
}
.px-4 {
  padding-left: 1rem;
  padding-right: 1rem;
}
.py-2 {
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
}
.rounded-lg {
  border-radius: 0.5rem;
}
</style>
