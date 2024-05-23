<script setup>
import ApartmentDetails from "@/components/ApartmentDetails.vue";
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
      name: "apartmentMap",
      params: { dongCode: selectedDong.value },
    });
  }
};
</script>

<template>
  <section class="container mx-auto py-12">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
      <div class="lg:col-span-2">
        <div class="bg-white shadow-lg rounded-2xl p-8 mb-8">
          <h2 class="text-3xl font-bold mb-6 text-indigo-600 flex items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              class="w-8 h-8 mr-2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              />
            </svg>
            아파트 검색
          </h2>
          <div class="mb-6">
            <label
              for="search"
              class="block mb-2 text-lg font-medium text-gray-700"
              >아파트, 지역 등을 입력하세요</label
            >
            <input
              type="text"
              id="search"
              placeholder="예) 강남구, 반포자이"
              class="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
            />
          </div>
          <div class="mb-6">
            <label
              for="city"
              class="block mb-2 text-lg font-medium text-gray-700"
              >시/도 선택</label
            >
            <select
              id="city"
              v-model="selectedCity"
              class="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
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
          <div class="mb-6">
            <label for="gu" class="block mb-2 text-lg font-medium text-gray-700"
              >구/군 선택</label
            >
            <select
              id="gu"
              v-model="selectedGu"
              class="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
            >
              <option value="" disabled>구/군 선택</option>
              <option v-for="gu in filteredGus" :key="gu.code" :value="gu.code">
                {{ gu.name }}
              </option>
            </select>
          </div>
          <div class="mb-8">
            <label
              for="dong"
              class="block mb-2 text-lg font-medium text-gray-700"
              >동 선택</label
            >
            <select
              id="dong"
              v-model="selectedDong"
              class="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"
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
            class="w-full py-3 px-6 bg-indigo-600 hover:bg-indigo-700 rounded-lg text-lg font-semibold text-white transition duration-300"
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

        <div class="bg-white shadow-lg rounded-2xl p-8">
          <h2 class="text-3xl font-bold mb-6 text-indigo-600 flex items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              class="w-8 h-8 mr-2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
              />
            </svg>
            커뮤니티
          </h2>
          <ul class="space-y-4">
            <li
              v-for="post in communityPosts"
              :key="post.id"
              class="flex items-center"
            >
              <div
                class="bg-indigo-500 text-white font-bold w-10 h-10 flex items-center justify-center rounded-full mr-4"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M19 21l-7-5-7 5V5a2 2 0 012-2h10a2 2 0 012 2v16z"
                  />
                </svg>
              </div>
              <div>
                <a
                  :href="post.url"
                  class="text-lg font-semibold text-indigo-600 hover:underline"
                  >{{ post.title }}</a
                >
                <p class="text-gray-500">{{ post.author }}</p>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>

  <ApartmentDetails />
</template>
