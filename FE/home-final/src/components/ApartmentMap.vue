<template>
  <div class="container">
    <div class="map-container">
      <KakaoMap :lat="selectedApartment.latitude" :lng="selectedApartment.longitude">
        <KakaoMapMarker v-for="apartment in apartments" :key="apartment.aptCode" :lat="apartment.latitude"
          :lng="apartment.longitude" :clickable="true" @onClickKakaoMapMarker="showApartmentDetail(apartment)" />
      </KakaoMap>
    </div>
    <div v-if="selectedApartment" class="detail-container">
      <h2>아파트 상세 정보</h2>
      <div>
        <h3>아파트 이름: {{ selectedApartment.aptName }}</h3>
        <p>거래 금액: {{ selectedApartment.dealAmount }}</p>
        <p>건축년도: {{ selectedApartment.constructionYear }}</p>
        <p>주소: {{ selectedApartment.roadName }}</p>
        <p>위도: {{ selectedApartment.latitude }}</p>
        <p>경도: {{ selectedApartment.longitude }}</p>
        <p>아파트 고유번호: {{ selectedApartment.aptCode }}</p>
        <h3>리뷰 목록</h3>
        <ul v-if="reviews.length > 0">
          <li v-for="review in reviews" :key="review.id">
            <p>작성자: {{ review.member.username }}</p>
            <p>내용: {{ review.content }}</p>
            <p>평점: {{ review.rating }}</p>
          </li>
        </ul>
        <p v-else>리뷰가 없습니다.</p>
        <button @click="openReviewForm(selectedApartment)">리뷰 남기기</button>
      </div>
    </div>
  </div>
  <ReviewForm v-if="reviewFormOpen" :apartment="selectedApartment" :user="user" @review-submitted="closeReviewForm" />
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { useRoute } from 'vue-router';
import ReviewForm from '@/components/ReviewForm.vue';
import axios from "axios";
const reviews = ref([]);

const fetchReviews = async (apartmentCode) => {
  try {
    const response = await axios.get('/api/api/apt-review/list', {
      params: { apartmentCode },
    });
    reviews.value = response.data;
  } catch (error) {
    console.error('리뷰 데이터 가져오기 실패:', error);
  }
};

onMounted(() => {
  if (selectedApartment.value) {
    fetchReviews(selectedApartment.value.aptCode);
  }
});

const route = useRoute();
const apartments = ref(JSON.parse(route.params.apartments || '[]'));
const initialApartment = ref(JSON.parse(route.params.initialApartment || 'null'));
const selectedApartment = ref(initialApartment.value);
const reviewFormOpen = ref(false);
const user = ref(null); // 로그인한 사용자 정보를 저장할 변수

const mapCenter = computed(() => ({
  lat: selectedApartment.value?.latitude || 37.5665,
  lng: selectedApartment.value?.longitude || 126.9780,
}));

const showApartmentDetail = (apartment) => {
  console.log("클릭됨!")
  selectedApartment.value = apartment;
};

const openReviewForm = (apartment) => {
  selectedApartment.value = apartment;
  reviewFormOpen.value = true;
};

const closeReviewForm = () => {
  reviewFormOpen.value = false;
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