<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();
const selectedCity = ref("");
const selectedGu = ref("");
const selectedDong = ref("");

const cities = ref([]);
const gus = ref([]);
const dongs = ref([]);

onMounted(() => {
  fetchCities();
});

const fetchCities = async () => {
  try {
    const response = await axios.get(
      "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000"
    );
    cities.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch cities:", error);
  }
};

const fetchGus = async (cityCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${cityCode.substr(
        0,
        2
      )}*00000&is_ignore_zero=true`
    );
    gus.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch gus:", error);
  }
};

const fetchDongs = async (guCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${guCode.substr(
        0,
        5
      )}*&is_ignore_zero=true`
    );
    dongs.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch dongs:", error);
  }
};

const handleCityChange = () => {
  if (selectedCity.value) {
    fetchGus(selectedCity.value);
  } else {
    gus.value = [];
    selectedGu.value = "";
  }
};

const handleGuChange = () => {
  if (selectedGu.value) {
    fetchDongs(selectedGu.value);
  } else {
    dongs.value = [];
    selectedDong.value = "";
  }
};

const searchApartments = () => {
  if (selectedDong.value) {
    router.push({
      name: "ApartmentListByDong",
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
            <!-- <input type="text" id="search" placeholder="예) 강남구, 반포자이" class="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 text-lg text-gray-700"> -->
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
        <div class="bg-white shadow-lg rounded-lg p-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">
            VR로 내 집 구경하기
          </h2>
          <p class="text-gray-700 mb-6">
            VR 기기로 내 집을 미리 살펴볼 수 있습니다.
          </p>
        </div>
      </div>
      <div>
        <div class="bg-white shadow-lg rounded-lg p-8 mb-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">공지사항</h2>
          <ul>
            <li
              v-for="notice in notices"
              :key="notice.id"
              class="border-b-2 border-gray-100 py-4"
            >
              <a
                :href="notice.url"
                class="text-indigo-600 hover:text-indigo-800 transition duration-300"
                >{{ notice.title }}</a
              >
              <span class="text-gray-500 text-sm ml-4">{{ notice.date }}</span>
            </li>
          </ul>
        </div>
        <div class="bg-white shadow-lg rounded-2xl p-8">
          <h2 class="text-3xl font-bold mb-8 text-indigo-600 flex items-center">
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
          <ul class="space-y-6">
            <li
              v-for="post in communityPosts"
              :key="post.id"
              class="flex items-center"
            >
              <div
                class="bg-indigo-500 text-white font-bold w-12 h-12 flex items-center justify-center rounded-full mr-4"
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
                  class="text-xl font-semibold text-indigo-600 hover:text-indigo-800 transition duration-300"
                  >{{ post.title }}</a
                >
                <p class="text-gray-500 mt-1">{{ post.author }}</p>
              </div>
            </li>
          </ul>
          <router-link
            to="/chatroomlist"
            class="mt-8 inline-block bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 px-6 rounded-lg transition duration-300"
          >
            커뮤니티 바로가기
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              class="w-5 h-5 inline-block ml-2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M17 8l4 4m0 0l-4 4m4-4H3"
              />
            </svg>
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.bg-gray-50 {
  background-color: #f9fafb;
}

.text-indigo-600 {
  color: #5a67d8;
}

.border-gray-300 {
  border-color: #d2d6dc;
}

.focus\:border-indigo-500:focus {
  border-color: #667eea;
}

.hover\:bg-indigo-50:hover {
  background-color: #ebf4ff;
}

.hover\:bg-indigo-600:hover {
  background-color: #4c51bf;
}

.bg-indigo-500 {
  background-color: #667eea;
}

.text-gray-600 {
  color: #718096;
}

.bg-gray-200 {
  background-color: #edf2f7;
}

.bg-green-500 {
  background-color: #48bb78;
}

.hover\:bg-green-600:hover {
  background-color: #38a169;
}

.dropdown-list {
  max-height: 14rem;
  /* 7 items * 2rem each */
  overflow-y: auto;
}
</style>
