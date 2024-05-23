<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-8 text-center text-indigo-600">아파트 목록</h2>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
      <div v-for="apartment in paginatedApartments" :key="apartment.aptCode"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition duration-300 cursor-pointer"
        @click="goToApartmentMap(apartment)">
        <img src="@/assets/img/apt.png" alt="아파트 이미지" class="w-full h-40 object-cover rounded-t-lg mb-4">
        <div class="mb-4">
          <h3 class="text-xl font-semibold text-gray-800">{{ apartment.aptName }}</h3>
          <p class="text-gray-600">{{ apartment.roadName }}</p>
        </div>
        <div class="text-gray-600 space-y-1">
          <p><span class="font-medium text-gray-800">건축년도:</span> {{ apartment.constructionYear }}</p>
        </div>
      </div>
    </div>
    <div class="mt-8 flex justify-center">
      <button @click="previousPage" :disabled="offset === 0" class="bg-indigo-600 text-white px-4 py-2 rounded-l-lg">Previous</button>
      <button @click="nextPage" :disabled="offset + limit >= apartments.length" class="bg-indigo-600 text-white px-4 py-2 rounded-r-lg">Next</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { sample } from "@/api/aptSaleApi.js";

export default {
  setup() {
    const router = useRouter();
    const apartments = ref([]);
    const paginatedApartments = ref([]);
    const limit = ref(9);
    const offset = ref(0);

    const fetchApartments = async (dongCode) => {
      try {
        const response = await sample(dongCode);
        apartments.value = response.data || []; // 데이터가 없을 경우 빈 배열 할당
        localStorage.setItem('apartments', JSON.stringify(apartments.value));
        updatePaginatedApartments();
      } catch (error) {
        console.error("Failed to fetch apartments:", error);
      }
    };

    const updatePaginatedApartments = () => {
      const start = offset.value;
      const end = offset.value + limit.value;
      paginatedApartments.value = apartments.value.slice(start, end);
    };

    const goToApartmentMap = (apartment) => {
      localStorage.setItem('selectedApartment', JSON.stringify(apartment));
      router.push({
        name: 'ApartmentMap',
        params: {
          apartment: apartment.aptCode,
        },
      });
    };

    const previousPage = () => {
      if (offset.value > 0) {
        offset.value -= limit.value;
        updatePaginatedApartments();
      }
    };

    const nextPage = () => {
      if (offset.value + limit.value < apartments.value.length) {
        offset.value += limit.value;
        updatePaginatedApartments();
      }
    };

    onMounted(() => {
      const dongCode = router.currentRoute.value.params.dongCode;
      fetchApartments(dongCode);
    });

    return {
      paginatedApartments,
      goToApartmentMap,
      previousPage,
      nextPage,
      limit,
      offset,
      apartments, // apartments 추가
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  overflow-x: auto;
  padding-bottom: 20px;
}

.card {
  width: calc(50% - 20px);
  margin-right: 20px;
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card.new-line {
  margin-right: 0;
}

.card-content {
  /* 카드 내용에 대한 스타일을 추가하세요 */
}
</style>
