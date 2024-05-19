<template>
  <div>
    <h2>아파트 목록</h2>
    <div class="card-container">
      <div
      v-for="(apartment, index) in apartments"
      :key="apartment.aptCode"
      class="card"
      :class="{ 'new-line': index % 2 === 0 }"
      @click="goToApartmentMap(apartment)"
    >
        <div class="card-content">
          <!-- 아파트 정보를 표시하는 내용을 추가하세요 -->
          <h3>{{ apartment.aptName }}</h3>
          <p>거래 금액: {{ apartment.dealAmount }}</p>
          <p>건축년도: {{ apartment.constructionYear }}</p>
          <p>주소: {{ apartment.roadName }}</p>
          <p>위도: {{ apartment.latitude }}</p>
          <p>경도: {{ apartment.longitude }}</p>
          

          <!-- 추가적인 아파트 정보를 표시할 수 있습니다 -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

export default {
  setup() {
    const router = useRouter();
    const apartments = ref([]);

    const fetchApartments = async (dongCode) => {
      try {
        const response = await axios.get(`/apartments?dongcode=${dongCode}`);
        apartments.value = response.data;
      } catch (error) {
        console.error("Failed to fetch apartments:", error);
      }
    };

    const goToApartmentMap = (apartment) => {
  router.push({
    name: 'ApartmentMap',
    params: {
      apartments: JSON.stringify(apartments.value),
      initialApartment: JSON.stringify(apartment),
    },
  });
};

    onMounted(() => {
      const dongCode = router.currentRoute.value.params.dongCode;
      fetchApartments(dongCode);
    });

    return {
      apartments,
      goToApartmentMap,
    };
  },
};
</script>
<style scoped>
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