<template>
  <div class="container">
    <div class="map-container">
      <KakaoMap :lat="mapCenter.lat" :lng="mapCenter.lng" width="70rem" height="50rem">
        <KakaoMapMarker v-for="apartment in apartments" :key="apartment.aptCode" :lat="apartment.latitude"
          :lng="apartment.longitude" :clickable="true" @onClickKakaoMapMarker="showApartmentDetail(apartment)" />
      </KakaoMap>
    </div>
    <div v-if="selectedApartment" class="detail-container">
      <h2>아파트 상세 정보</h2>
      <div class="apartment-details">
        <h3>아파트 이름: {{ selectedApartment.aptName }}</h3>
        <div v-if="apartmentDetails" class="info-cards">
          <div v-for="detail in apartmentDetails" :key="detail.aptCode" class="info-card">
            <h4>거래 금액</h4>
            <p>{{ detail.dealAmount }}원</p>
            <h4>거래 날짜</h4>
            <p>{{ detail.dealYear }}년 {{ detail.dealMonth }}월 {{ detail.dealDay }}일</p>
            <h4>전용 면적</h4>
            <p>{{ detail.exclusiveArea }}m²</p>
            <h4>층</h4>
            <p>{{ detail.floor }}층</p>
          </div>
        </div>
        <div class="button-container">
          <button class="like-button" @click="likeApartment(selectedApartment)">찜하기</button>
          <button class="review-button" @click="openReviewForm(selectedApartment)">리뷰 남기기</button>
        </div>
        <ReviewForm v-if="reviewFormOpen" :apartment="selectedApartment" :user="user"
          @review-submitted="handleReviewSubmitted" />
        <h3>리뷰</h3>
        <div v-if="reviews.length > 0" class="reviews">
          <div v-for="review in reviews" :key="review.id" class="review-card">
            <p><strong>작성자:</strong> {{ review.member.nickname }}</p>
            <p><strong>내용:</strong> {{ review.content }}</p>
            <p><strong>평점:</strong> {{ review.rating }}</p>
          </div>
        </div>
        <p v-else>리뷰가 없습니다.</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { useRoute, useRouter } from 'vue-router';
import ReviewForm from '@/components/ReviewForm.vue';
import axios from "axios";
import { getAptSaleDetails } from '@/api/aptSaleApi';

const reviews = ref([]);
const route = useRoute();
const router = useRouter();
const apartments = ref(JSON.parse(route.params.apartments || '[]'));
const initialApartment = ref(JSON.parse(route.params.initialApartment || 'null'));
const selectedApartment = ref(initialApartment.value || {});
const reviewFormOpen = ref(false);
const user = ref(null);
const isLoggedIn = ref(false);
const apartmentDetails = ref(null);

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

const fetchApartmentDetails = async () => {
  console.log()
  if (selectedApartment.value && selectedApartment.value.aptCode) {
    try {
      const response = await getAptSaleDetails(selectedApartment.value.aptCode);
      apartmentDetails.value = response.data;
    } catch (error) {
      console.error('아파트 상세 정보 가져오기 실패:', error);
    }
  }
};

const saveToLocalStorage = () => {
  localStorage.setItem('apartments', JSON.stringify(apartments.value));
  localStorage.setItem('initialApartment', JSON.stringify(initialApartment.value));
  localStorage.setItem('selectedApartment', JSON.stringify(selectedApartment.value));
};

onMounted(async () => {
  const token = localStorage.getItem('token');
  if (token) {
    try {
      const response = await axios.get('/api/user', {
        headers: { Authorization: `${token}` },
      });
      user.value = response.data;
      isLoggedIn.value = true;
    } catch (error) {
      console.error('사용자 정보 가져오기 실패:', error);
      router.push('/login');
    }
  } else {
    router.push('/login');
  }

  if (selectedApartment.value && selectedApartment.value.aptCode) {
    console.log(selectedApartment.value.aptCode)
    fetchReviews(selectedApartment.value.aptCode);
    fetchApartmentDetails();
  }
});

watch([apartments, initialApartment, selectedApartment], saveToLocalStorage, { deep: true });

const mapCenter = computed(() => ({
  lat: selectedApartment.value?.latitude || 37.5665,
  lng: selectedApartment.value?.longitude || 126.9780,
}));

const showApartmentDetail = (apartment) => {
  selectedApartment.value = apartment;
  fetchReviews(apartment.aptCode);
  fetchApartmentDetails();
};

const openReviewForm = (apartment) => {
  selectedApartment.value = apartment;
  reviewFormOpen.value = true;
};

const closeReviewForm = () => {
  reviewFormOpen.value = false;
};

const handleReviewSubmitted = (review) => {
  reviews.value.push(review);
  closeReviewForm();
};

const likeApartment = async (apartment) => {
  try {
    const token = localStorage.getItem('token');
    await axios.post('/api/api/like', {
      member: user.value,
      aptCode: apartment.aptCode,
    }, {
      headers: { Authorization: `Bearer ${token}` },
    });
    alert('아파트가 찜목록에 추가되었습니다.');
  } catch (error) {
    console.error('찜하기 실패:', error);
  }
};
</script>

<style scoped>
.info-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.info-card {
  background-color: #f1f1f1;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.info-card h4 {
  margin-top: 0;
  color: #333;
}

.info-card p {
  margin-bottom: 0;
  color: #555;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.review-card {
  background-color: #f1f1f1;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.container {
  display: flex;
  height: 100vh;
  width: 100vw;
  /* 전체 너비 사용 */
}

.map-container {
  flex: 7;
  height: 100%;
}

.detail-container {
  flex: 3;
  padding: 20px;
  overflow-y: auto;
  background-color: #f9f9f9;
  border-left: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.apartment-details {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.apartment-details h3 {
  margin-top: 0;
  color: #333;
}

.apartment-details p {
  margin: 5px 0;
  color: #555;
}

.reviews {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.review-item {
  background-color: #f1f1f1;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.review-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.like-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: green;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.review-button:hover {
  background-color: #0056b3;
}
</style>
