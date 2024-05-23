<template>
  <div v-if="schoolInfo" class="bg-white p-4 shadow-md rounded-lg">
    <h3 class="text-xl font-bold mb-2">학군 정보</h3>
    <p><strong>초등학교:</strong> {{ schoolInfo.lowHakgudoNm }} ({{ schoolInfo.lowEduNm }})</p>
    <p><strong>중학교:</strong> {{ schoolInfo.middleHakgudoNm }} ({{ schoolInfo.middleEduNm }})</p>
    <p><strong>고등학교:</strong> {{ schoolInfo.highHakgudoNm }} ({{ schoolInfo.highEduNm }})</p>
  </div>
  <div v-else class="text-center">
    학군 정보를 불러오는 중입니다...
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getAptSchoolInfo } from '@/api/aptSchoolApi';

const props = defineProps({
  aptCode: {
    type: String,
    required: true
  },
  aptName: {
    type: String,
    required: true
  }
});

const schoolInfo = ref(null);

const fetchSchoolInfo = async () => {
  try {
    console.log(`Fetching school info for aptCode: ${props.aptCode}, aptName: ${props.aptName}`);
    const response = await getAptSchoolInfo(props.aptCode, props.aptName);
    console.log('School info response:', response.data);
    schoolInfo.value = response.data;
  } catch (error) {
    console.error('Failed to fetch school info:', error);
  }
};

// Watch for changes in aptCode and aptName and refetch school info
watch(
  () => [props.aptCode, props.aptName],
  () => {
    fetchSchoolInfo();
  },
  { immediate: true }
);

</script>

<style scoped>
.bg-white {
  background-color: #fff;
}

.p-4 {
  padding: 1rem;
}

.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.rounded-lg {
  border-radius: 0.5rem;
}

.text-xl {
  font-size: 1.25rem;
}

.font-bold {
  font-weight: 700;
}

.mb-2 {
  margin-bottom: 0.5rem;
}

.text-center {
  text-align: center;
}
</style>
