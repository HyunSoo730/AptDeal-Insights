<template>
  <div class="apartment-details bg-white p-6 shadow-lg rounded-2xl">
    <h2 class="text-3xl font-bold mb-6 text-indigo-600">아파트 상세 정보</h2>
    <div v-if="details.length">
      <div v-for="(detail, index) in displayedDetails" :key="index" class="mb-8">
        <h3 class="text-2xl font-semibold text-gray-800 mb-2">{{ detail.aptName }}</h3>
        <div class="grid grid-cols-2 gap-4">
          <div>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">거래 날짜:</span> {{ detail.dealYear }}년 {{ detail.dealMonth }}월 {{ detail.dealDay }}일</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">거래 금액:</span> {{ detail.dealAmount }}만원</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">건축년도:</span> {{ detail.constructionYear }}</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">도로명 주소:</span> {{ detail.roadNameAddress }}</p>
          </div>
          <div>
            <p v-if="detail.dong" class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">동:</span> {{ detail.dong }}</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">법정동:</span> {{ detail.legalDong }}</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">거래 면적:</span> {{ detail.exclusiveArea }}㎡</p>
            <p class="text-gray-600 mb-1"><span class="font-semibold text-gray-800">층:</span> {{ detail.floor }}층</p>
          </div>
        </div>
        <hr class="border-gray-300 my-6">
      </div>
      <button v-if="!allDetailsShown" @click="loadMore" class="bg-indigo-500 text-white px-6 py-3 rounded-full text-lg font-semibold hover:bg-indigo-600 transition duration-300">
        더보기
      </button>
    </div>
    <div v-else class="text-xl text-gray-600">
      <p>데이터를 불러오는 중입니다...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getAptSaleDetails } from '@/api/aptSaleApi';
import { useMapStore } from '@/stores/mapStore';

const props = defineProps({
  aptCode: {
    type: String,
    required: true
  }
});

const mapStore = useMapStore();
const aptCode = ref(mapStore.selectedAptCode);

const details = ref([]);
const displayedDetails = ref([]);
const allDetailsShown = ref(false);

const fetchAptSaleDetails = async (aptCode) => {
  try {
    details.value = []; // 초기화
    displayedDetails.value = []; // 초기화
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
.apartment-details {
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

.hover\:bg-indigo-600:hover {
  background-color: #5a67d8;
}

.transition {
  transition-property: background-color, border-color, color, fill, stroke, opacity, box-shadow, transform;
  transition-duration: 300ms;
}
</style>