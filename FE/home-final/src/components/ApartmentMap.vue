<template>

  <div class="container mx-auto px-4 py-8">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <div class="lg:col-span-2">
        <div class="map-container shadow-lg rounded-lg overflow-hidden">
          <KakaoMap :lat="mapCenter.lat" :lng="mapCenter.lng" width="100%" height="600px">
            <KakaoMapMarker v-for="apartment in apartments" :key="apartment.aptCode" :lat="apartment.latitude"
              :lng="apartment.longitude" :clickable="true" :image="{
                imageSrc: 'src/assets/img/housemarker.png',
                imageWidth: 30,
                imageHeight: 30,
                imageOption: {}
              }" @onClickKakaoMapMarker="showApartmentDetail(apartment)" />
            <KakaoMapMarker v-for="station in stations" :key=station.name :lat="station.latitude"
              :lng="station.longitude" :clickable="true" :image="{
                imageSrc: 'src/assets/img/subway.png',
                imageWidth: 25,
                imageHeight: 25,
                imageOption: {}
              }" />
          </KakaoMap>
        </div>
        <div v-if="selectedApartment" class="mt-8">
          <SubwayStationDetails :lat="selectedApartment?.latitude" :lng="selectedApartment?.longitude" class="mb-6"
            @update:stations="updateStations" />
          <AptSchoolInfo :aptCode="selectedApartment?.aptCode" :aptName="selectedApartment?.aptName" class="mb-6" />
          <div class="flex justify-between mb-6">
            <button class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
              @click="likeApartment(selectedApartment)">
              찜하기
            </button>
          </div>
          <div class="mb-6">
            <h3 class="text-xl font-semibold mb-4">리뷰 작성</h3>
            <button v-if="!reviewFormOpen" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              @click="openReviewForm">
              리뷰 쓰기
            </button>
            <ReviewForm v-else :apartment="selectedApartment" :user="user" @review-submitted="handleReviewSubmitted"
              @cancel="closeReviewForm" />
          </div>
          <div class="review-section">
            <h3 class="text-xl font-semibold mb-4">작성된 리뷰</h3>
            <div v-if="visibleReviews.length > 0" class="reviews grid grid-cols-1 gap-4">
              <div v-for="review in visibleReviews" :key="review.id" class="review-card bg-gray-100 p-4 rounded-lg">
                <p><strong>작성자:</strong> {{ review.member.nickname }}</p>
                <p><strong>내용:</strong> {{ review.content }}</p>
                <p><strong>평점:</strong> {{ review.rating }}</p>
              </div>
            </div>
            <p v-else>리뷰가 없습니다.</p>
            <button v-if="reviews.length > visibleReviews.length" @click="showMoreReviews"
              class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-2 px-4 rounded mt-4">
              리뷰 더보기
            </button>
          </div>
        </div>
      </div>
      <div v-if="selectedApartment" class="lg:col-span-1">
        <div class="detail-container bg-white shadow-lg rounded-lg p-6">
          <h2 class="text-2xl font-bold mb-4">아파트 상세 정보</h2>
          <AptTransactionChart v-if="selectedApartment" :apartment="selectedApartment"
            :aptCode="selectedApartment?.aptCode" :dongcode="dongcode" class="mb-6" />
          <div class="apartment-details space-y-4">
            <h3 class="text-xl font-semibold">
              아파트 이름: {{ selectedApartment.aptName }}
            </h3>
            <div v-if="apartmentDetails" class="info-cards grid grid-cols-1 gap-4">
              <div v-for="(detail, index) in visibleDetails" :key="detail.aptCode"
                class="info-card bg-gray-100 p-4 rounded-lg">
                <h4 class="text-lg font-semibold mb-2">거래 금액</h4>
                <p>{{ detail.dealAmount }}원</p>
                <h4 class="text-lg font-semibold mb-2 mt-4">거래 날짜</h4>
                <p>{{ detail.dealYear }}년 {{ detail.dealMonth }}월 {{ detail.dealDay }}일</p>
                <h4 class="text-lg font-semibold mb-2 mt-4">전용 면적</h4>
                <p>{{ detail.exclusiveArea }}m²</p>
                <h4 class="text-lg font-semibold mb-2 mt-4">층</h4>
                <p>{{ detail.floor }}층</p>
              </div>
            </div>
            <button
              v-if="apartmentDetails && apartmentDetails.length > 3 && visibleDetails.length < apartmentDetails.length"
              @click="showMoreDetails" class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-2 px-4 rounded">
              거래 내역 더보기
            </button>
          </div>
        </div>
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
import { getClosestSubwayStations } from '@/api/subwayStationApi';

import SubwayStationDetails from '@/components/SubwayStationDetails.vue';
import AptSchoolInfo from '@/components/AptSchoolInfo.vue';
import AptTransactionChart from '@/components/AptTransactionChart.vue';

const route = useRoute();
const router = useRouter();

const apartments = ref([]);
const selectedApartment = ref(null);
const apartmentDetails = ref(null);
const visibleDetails = ref([]);

const reviews = ref([]);
const visibleReviews = ref([]);

const reviewFormOpen = ref(false);
const user = ref(null);
const isLoggedIn = ref(false);

const fetchReviews = async (apartmentCode) => {
  try {
    const response = await axios.get('/api/api/apt-review/list', {
      params: { apartmentCode },
    });
    reviews.value = response.data;
    visibleReviews.value = reviews.value.slice(0, 4);
  } catch (error) {
    console.error('리뷰 데이터 가져오기 실패:', error);
  }
};

const fetchApartmentDetails = async () => {
  if (selectedApartment.value && selectedApartment.value.aptCode) {
    try {
      const response = await getAptSaleDetails(selectedApartment.value.aptCode);
      apartmentDetails.value = response.data;
      visibleDetails.value = apartmentDetails.value.slice(0, 3);
    } catch (error) {
      console.error('아파트 상세 정보 가져오기 실패:', error);
    }
  }
};

const openReviewForm = () => {
  reviewFormOpen.value = true;
};

const closeReviewForm = () => {
  reviewFormOpen.value = false;
};

const handleReviewSubmitted = (review) => {
  reviews.value.unshift(review);
  visibleReviews.value.unshift(review);
  closeReviewForm();
};

const showMoreReviews = () => {
  const currentLength = visibleReviews.value.length;
  const nextReviews = reviews.value.slice(currentLength, currentLength + 4);
  visibleReviews.value = [...visibleReviews.value, ...nextReviews];
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

  const apartmentCode = route.params.aptCode;
  const savedApartments = localStorage.getItem('apartments');
  const savedSelectedApartment = localStorage.getItem('selectedApartment');

  if (savedApartments && savedSelectedApartment) {
    apartments.value = JSON.parse(savedApartments);
    selectedApartment.value = JSON.parse(savedSelectedApartment);
    fetchReviews(selectedApartment.value.aptCode);
    fetchApartmentDetails();
  } else if (apartmentCode) {
    // 서버에서 아파트 정보를 가져오는 코드 추가
  }
});

const mapCenter = computed(() => ({
  lat: selectedApartment.value?.latitude || 37.5665,
  lng: selectedApartment.value?.longitude || 126.9780,
}));

const showApartmentDetail = (apartment) => {
  selectedApartment.value = apartment;
  fetchReviews(apartment.aptCode);
  fetchApartmentDetails();
  localStorage.setItem('selectedApartment', JSON.stringify(apartment));
  localStorage.setItem('apartments', JSON.stringify(apartments.value));
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

const showMoreDetails = () => {
  visibleDetails.value = apartmentDetails.value;
};

const dongcode = computed(() => {
  return selectedApartment.value?.aptCode.substring(0, 10) || '';
});

const stations = ref([]); // 부모 컴포넌트에서 stations 데이터를 관리

const updateStations = (newStations) => {
  stations.value = newStations;
};

</script>
