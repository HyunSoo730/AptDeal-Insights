<template>
  <div v-if="schoolInfo" class="school-info mt-8 p-6 bg-white shadow-lg rounded-2xl">
    <h2 class="text-2xl font-bold mb-6 text-indigo-600 flex items-center">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-8 h-8 mr-2">
        <path d="M12 14l9-5-9-5-9 5 9 5z" />
        <path d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z" />
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222" />
      </svg>
      학군 정보
    </h2>
    <div class="space-y-4">
      <div class="flex items-center bg-gray-100 p-4 rounded-xl shadow-md">
        <div class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4">
          초
        </div>
        <div>
          <h3 class="text-xl font-semibold text-gray-800">{{ schoolInfo.lowHakgudoNm }}</h3>
          <p class="text-gray-600">{{ schoolInfo.lowEduNm }}</p>
        </div>
      </div>
      <div class="flex items-center bg-gray-100 p-4 rounded-xl shadow-md">
        <div class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4">
          중
        </div>
        <div>
          <h3 class="text-xl font-semibold text-gray-800">{{ schoolInfo.middleHakgudoNm }}</h3>
          <p class="text-gray-600">{{ schoolInfo.middleEduNm }}</p>
        </div>
      </div>
      <div class="flex items-center bg-gray-100 p-4 rounded-xl shadow-md">
        <div class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4">
          고
        </div>
        <div>
          <h3 class="text-xl font-semibold text-gray-800">{{ schoolInfo.highHakgudoNm }}</h3>
          <p class="text-gray-600">{{ schoolInfo.highEduNm }}</p>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="text-xl text-gray-600 text-center mt-8">
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

watch(
  () => [props.aptCode, props.aptName],
  () => {
    fetchSchoolInfo();
  },
  { immediate: true }
);

</script>

<style scoped>
.school-info {
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
</style>