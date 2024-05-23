<template>
  <div class="container mx-auto px-4 py-8">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <div class="lg:col-span-2">
        <div class="map-container shadow-lg rounded-lg overflow-hidden">
          <KakaoMap
            :lat="mapCenter.lat"
            :lng="mapCenter.lng"
            width="100%"
            height="600px"
          >
            <KakaoMapMarker
              v-for="apartment in apartments"
              :key="apartment.aptCode"
              :lat="apartment.latitude"
              :lng="apartment.longitude"
              :clickable="true"
              :image="{
                imageSrc: 'src/assets/img/housemarker.png',
                imageWidth: 30,
                imageHeight: 30,
                imageOption: {},
              }"
              @onClickKakaoMapMarker="showApartmentDetail(apartment)"
            />
            <KakaoMapMarker
              v-for="station in stations"
              :key="station.name"
              :lat="station.latitude"
              :lng="station.longitude"
              :clickable="true"
              :image="{
                imageSrc: 'src/assets/img/subway.png',
                imageWidth: 25,
                imageHeight: 25,
                imageOption: {},
              }"
            />
          </KakaoMap>
        </div>
        <div v-if="selectedApartment" class="mt-8">
          <SubwayStationDetails
            :lat="selectedApartment?.latitude"
            :lng="selectedApartment?.longitude"
            class="mb-6"
            @update:stations="updateStations"
          />
          <AptSchoolInfo
            :aptCode="selectedApartment?.aptCode"
            :aptName="selectedApartment?.aptName"
            class="mb-6"
          />
          <div class="flex justify-between mb-6">
            <button
              class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded"
              @click="likeApartment(selectedApartment)"
            >
              찜하기 ✅
            </button>
          </div>
          <div class="mb-6">
            <button
              v-if="!reviewFormOpen"
              class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
              @click="openReviewForm"
            >
              리뷰 쓰기 ✏
            </button>
            <ReviewForm
              v-else
              :apartment="selectedApartment"
              :user="user"
              @review-submitted="handleReviewSubmitted"
              @cancel="closeReviewForm"
            />
          </div>
          <div class="review-section bg-white p-6 shadow-lg rounded-2xl">
            <h3
              class="text-2xl font-bold mb-6 text-indigo-600 flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
                class="w-8 h-8 mr-2"
              >
                <path
                  d="M11.645 20.91l-.007-.003-.022-.012a15.247 15.247 0 01-.383-.218 25.18 25.18 0 01-4.244-3.17C4.688 15.36 2.25 12.174 2.25 8.25 2.25 5.322 4.714 3 7.688 3A5.5 5.5 0 0112 5.052 5.5 5.5 0 0116.313 3c2.973 0 5.437 2.322 5.437 5.25 0 3.925-2.438 7.111-4.739 9.256a25.175 25.175 0 01-4.244 3.17 15.247 15.247 0 01-.383.219l-.022.012-.007.004-.003.001a.752.752 0 01-.704 0l-.003-.001z"
                />
              </svg>
              작성된 리뷰
            </h3>
            <div
              v-if="visibleReviews.length > 0"
              class="reviews grid grid-cols-1 md:grid-cols-2 gap-6"
            >
              <div
                v-for="review in visibleReviews"
                :key="review.id"
                class="review-card bg-gray-50 p-6 rounded-xl shadow-md"
              >
                <div class="flex items-center mb-4">
                  <div
                    class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4"
                  >
                    {{ review.member.nickname.charAt(0).toUpperCase() }}
                  </div>
                  <div>
                    <h4 class="text-xl font-semibold text-gray-800">
                      {{ review.member.nickname }}
                    </h4>
                    <div class="flex items-center mt-1">
                      <svg
                        v-for="i in review.rating"
                        :key="i"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        class="w-5 h-5 text-yellow-500"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z"
                          clip-rule="evenodd"
                        />
                      </svg>
                      <svg
                        v-for="i in 5 - review.rating"
                        :key="i"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                        class="w-5 h-5 text-gray-400"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"
                        />
                      </svg>
                    </div>
                  </div>
                </div>
                <p class="text-gray-700">{{ review.content }}</p>
              </div>
            </div>
            <p v-else class="text-xl text-gray-600">리뷰가 없습니다.</p>
            <button
              v-if="reviews.length > visibleReviews.length"
              @click="showMoreReviews"
              class="bg-indigo-500 text-white px-6 py-3 rounded-full text-lg font-semibold hover:bg-indigo-600 transition duration-300 mt-6"
            >
              리뷰 더보기
            </button>
          </div>
        </div>
      </div>
      <div v-if="selectedApartment" class="lg:col-span-1">
        <div class="detail-container bg-white shadow-lg rounded-lg p-6">
          <AptTransactionChart
            v-if="selectedApartment"
            :apartment="selectedApartment"
            :aptCode="selectedApartment?.aptCode"
            :dongcode="dongcode"
            class="mb-6"
          />
          <div class="apartment-details space-y-4">
            <h3 class="text-xl font-semibold"></h3>
            <div
              v-if="apartmentDetails"
              class="info-cards grid grid-cols-1 gap-4"
            >
              <div
                v-for="(detail, index) in visibleDetails"
                :key="detail.aptCode"
                :class="[
                  'info-card p-4 rounded-lg',
                  {
                    'bg-gray-100': index % 2 === 0,
                    'bg-blue-100': index % 2 !== 0,
                  },
                ]"
              >
                <div class="bg-white p-6 shadow-lg rounded-2xl">
                  <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="mb-4">
                      <div class="flex items-center">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                          class="w-6 h-6 text-indigo-500 mr-2"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                          />
                        </svg>
                        <h4 class="text-xl font-bold text-gray-800">
                          거래 금액
                        </h4>
                      </div>
                      <p class="text-2xl font-semibold text-indigo-600 mt-2">
                        {{ detail.dealAmount }}원
                      </p>
                    </div>
                    <div class="mb-4">
                      <div class="flex items-center">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                          class="w-6 h-6 text-indigo-500 mr-2"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                          />
                        </svg>
                        <h4 class="text-xl font-bold text-gray-800">
                          거래 날짜
                        </h4>
                      </div>
                      <p class="text-lg text-gray-600 mt-2">
                        {{ detail.dealYear }}년 {{ detail.dealMonth }}월
                        {{ detail.dealDay }}일
                      </p>
                    </div>
                    <div class="mb-4">
                      <div class="flex items-center">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                          class="w-6 h-6 text-indigo-500 mr-2"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"
                          />
                        </svg>
                        <h4 class="text-xl font-bold text-gray-800">
                          전용 면적
                        </h4>
                      </div>
                      <p class="text-lg text-gray-600 mt-2">
                        {{ detail.exclusiveArea }}m²
                      </p>
                    </div>
                    <div class="mb-4">
                      <div class="flex items-center">
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke="currentColor"
                          class="w-6 h-6 text-indigo-500 mr-2"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M3 10h18M3 14h18m-9-4v8m-7 0h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"
                          />
                        </svg>
                        <h4 class="text-xl font-bold text-gray-800">층</h4>
                      </div>
                      <p class="text-lg text-gray-600 mt-2">
                        {{ detail.floor }}층
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <button
              v-if="
                apartmentDetails &&
                apartmentDetails.length > 3 &&
                visibleDetails.length < apartmentDetails.length
              "
              @click="showMoreDetails"
              class="bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-2 px-4 rounded"
            >
              거래 내역 더보기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { useRoute, useRouter } from "vue-router";
import ReviewForm from "@/components/ReviewForm.vue";
import axios from "axios";
import { getAptSaleDetails } from "@/api/aptSaleApi";
import { getClosestSubwayStations } from "@/api/subwayStationApi";

import SubwayStationDetails from "@/components/SubwayStationDetails.vue";
import AptSchoolInfo from "@/components/AptSchoolInfo.vue";
import AptTransactionChart from "@/components/AptTransactionChart.vue";

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
    const response = await axios.get("/api/api/apt-review/list", {
      params: { apartmentCode },
    });
    reviews.value = response.data;
    visibleReviews.value = reviews.value.slice(0, 4);
  } catch (error) {
    console.error("리뷰 데이터 가져오기 실패:", error);
  }
};

const fetchApartmentDetails = async () => {
  if (selectedApartment.value && selectedApartment.value.aptCode) {
    try {
      const response = await getAptSaleDetails(selectedApartment.value.aptCode);
      apartmentDetails.value = response.data;
      visibleDetails.value = apartmentDetails.value.slice(0, 3);
    } catch (error) {
      console.error("아파트 상세 정보 가져오기 실패:", error);
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
  const token = localStorage.getItem("token");
  if (token) {
    try {
      const response = await axios.get("/api/user", {
        headers: { Authorization: `${token}` },
      });
      user.value = response.data;
      isLoggedIn.value = true;
    } catch (error) {
      console.error("사용자 정보 가져오기 실패:", error);
      router.push("/login");
    }
  } else {
    router.push("/login");
  }

  const apartmentCode = route.params.aptCode;
  const savedApartments = localStorage.getItem("apartments");
  const savedSelectedApartment = localStorage.getItem("selectedApartment");

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
  lng: selectedApartment.value?.longitude || 126.978,
}));

const showApartmentDetail = (apartment) => {
  selectedApartment.value = apartment;
  fetchReviews(apartment.aptCode);
  fetchApartmentDetails();
  localStorage.setItem("selectedApartment", JSON.stringify(apartment));
  localStorage.setItem("apartments", JSON.stringify(apartments.value));
};

const likeApartment = async (apartment) => {
  try {
    const token = localStorage.getItem("token");
    await axios.post(
      "/api/api/like",
      {
        member: user.value,
        aptCode: apartment.aptCode,
      },
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    alert("아파트가 찜목록에 추가되었습니다.");
  } catch (error) {
    console.error("찜하기 실패:", error);
  }
};

const showMoreDetails = () => {
  visibleDetails.value = apartmentDetails.value;
};

const dongcode = computed(() => {
  return selectedApartment.value?.aptCode.substring(0, 10) || "";
});

const stations = ref([]); // 부모 컴포넌트에서 stations 데이터를 관리

const updateStations = (newStations) => {
  stations.value = newStations;
};
</script>
