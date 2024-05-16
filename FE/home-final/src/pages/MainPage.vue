<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const selectedCity = ref("");
const selectedGu = ref("");
const selectedDong = ref("");

const cities = [
  { name: "서울특별시", code: "11" },
  { name: "대구광역시", code: "27" },
  // 다른 시/도 추가
];

const gus = [
  { name: "서초구", code: "11650", cityCode: "11" },
  { name: "달성군", code: "27710", cityCode: "27" },
  // 다른 구/군 추가
];

const dongs = [
  { name: "반포동", code: "11650105", guCode: "11650" },
  { name: "논공읍", code: "27710110", guCode: "27710" },
  { name: "북리", code: "27710125", guCode: "27710" },
  // 다른 동 추가
];

const filteredGus = computed(() => {
  return gus.filter((gu) => gu.cityCode === selectedCity.value);
});

const filteredDongs = computed(() => {
  return dongs.filter((dong) => dong.guCode === selectedGu.value);
});

const searchApartments = () => {
  if (selectedDong.value) {
    router.push({
      name: "ApartmentList",
      params: { dongCode: selectedDong.value },
    });
  }
};
</script>

<template>
  <section class="container mx-auto py-12">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
      <div class="lg:col-span-2">
        <div class="bg-white shadow-md rounded-lg p-6 mb-8">
          <h2 class="text-2xl font-bold mb-6 text-gray-800">아파트 검색</h2>
          <div class="mb-4">
            <input
              type="text"
              placeholder="아파트, 지역 등을 입력하세요"
              class="w-full p-3 rounded-t border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
            />
          </div>
          <div class="mb-4">
            <select
              v-model="selectedCity"
              class="w-full p-3 rounded-t border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
            >
              <option value="" disabled>시/도 선택</option>
              <option
                v-for="city in cities"
                :key="city.code"
                :value="city.code"
              >
                {{ city.name }}
              </option>
            </select>
          </div>
          <div class="mb-4">
            <select
              v-model="selectedGu"
              class="w-full p-3 border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
            >
              <option value="" disabled>구/군 선택</option>
              <option v-for="gu in filteredGus" :key="gu.code" :value="gu.code">
                {{ gu.name }}
              </option>
            </select>
          </div>
          <div class="mb-6">
            <select
              v-model="selectedDong"
              class="w-full p-3 rounded-b border border-gray-400 focus:outline-none focus:border-blue-500 text-lg"
            >
              <option value="" disabled>동 선택</option>
              <option
                v-for="dong in filteredDongs"
                :key="dong.code"
                :value="dong.code"
              >
                {{ dong.name }}
              </option>
            </select>
          </div>
          <button
            @click="searchApartments"
            class="bg-blue-500 text-white px-6 py-3 rounded-lg text-lg w-full"
          >
            검색
          </button>
        </div>

        <div class="bg-white shadow-md rounded-lg p-6">
          <h2 class="text-xl font-bold mb-4 text-gray-800">
            VR로 내 집 구경하기
          </h2>
          <p class="text-gray-700 mb-4">
            VR 기기로 내 집을 미리 살펴볼 수 있습니다.
          </p>
          <img src="" alt="VR Image" class="w-full rounded-lg" />
        </div>
      </div>

      <div>
        <div class="bg-white shadow-md rounded-lg p-6 mb-8">
          <h2 class="text-xl font-bold mb-4 text-gray-800">공지사항</h2>
          <ul>
            <li
              v-for="notice in notices"
              :key="notice.id"
              class="border-b border-gray-200 py-2"
            >
              <a :href="notice.url" class="text-blue-500 hover:underline">{{
                notice.title
              }}</a>
              <span class="text-gray-500 text-sm ml-2">{{ notice.date }}</span>
            </li>
          </ul>
        </div>

        <div class="bg-white shadow-md rounded-lg p-6">
          <h2 class="text-xl font-bold mb-4 text-gray-800">커뮤니티</h2>
          <ul>
            <li
              v-for="post in communityPosts"
              :key="post.id"
              class="border-b border-gray-200 py-2"
            >
              <a :href="post.url" class="text-blue-500 hover:underline">{{
                post.title
              }}</a>
              <span class="text-gray-500 text-sm ml-2">{{ post.author }}</span>
            </li>
          </ul>
          <router-link
            to="/community"
            class="mt-4 text-blue-500 hover:underline"
            >커뮤니티 바로가기</router-link
          >
        </div>
      </div>
    </div>
  </section>
</template>
