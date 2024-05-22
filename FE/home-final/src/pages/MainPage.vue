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
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${cityCode.substr(0, 2)}*00000&is_ignore_zero=true`
    );
    gus.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch gus:", error);
  }
};

const fetchDongs = async (guCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${guCode.substr(0, 5)}*&is_ignore_zero=true`
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
        <div class="bg-white shadow-lg rounded-lg p-8 mb-8">
          <h2 class="text-3xl font-bold mb-8 text-indigo-600">아파트 검색</h2>
          <div class="mb-6">
            <label class="block text-gray-700 font-bold mb-2" for="city">시/도 선택</label>
            <select id="city" v-model="selectedCity" @change="handleCityChange"
              class="w-full p-4 rounded-t border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700">
              <option value="" disabled>시/도 선택</option>
              <option v-for="city in cities" :key="city.code" :value="city.code">
                {{ city.name }}
              </option>
            </select>
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 font-bold mb-2" for="gu">구/군 선택</label>
            <select id="gu" v-model="selectedGu" @change="handleGuChange"
              class="w-full p-4 border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700">
              <option value="" disabled>구/군 선택</option>
              <option v-for="gu in gus" :key="gu.code" :value="gu.code">
                {{ gu.name.split(" ")[1] }}
              </option>
            </select>
          </div>
          <div class="mb-8">
            <label class="block text-gray-700 font-bold mb-2" for="dong">동 선택</label>
            <select id="dong" v-model="selectedDong"
              class="w-full p-4 rounded-b border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700">
              <option value="" disabled>동 선택</option>
              <option v-for="dong in dongs" :key="dong.code" :value="dong.code">
                {{ dong.name.split(" ").pop() }}
              </option>
            </select>
          </div>
          <button @click="searchApartments" class="bg-indigo-600 text-white px-8 py-4 rounded-lg text-lg w-full hover:bg-indigo-700 transition duration-300">
            검색
          </button>
        </div>
        <div class="bg-white shadow-lg rounded-lg p-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">VR로 내 집 구경하기</h2>
          <p class="text-gray-700 mb-6">VR 기기로 내 집을 미리 살펴볼 수 있습니다.</p>
          <img src="" alt="VR Image" class="w-full rounded-lg shadow-md" />
        </div>
      </div>
      <div>
        <div class="bg-white shadow-lg rounded-lg p-8 mb-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">공지사항</h2>
          <ul>
            <li v-for="notice in notices" :key="notice.id" class="border-b-2 border-gray-100 py-4">
              <a :href="notice.url" class="text-indigo-600 hover:text-indigo-800 transition duration-300">{{ notice.title }}</a>
              <span class="text-gray-500 text-sm ml-4">{{ notice.date }}</span>
            </li>
          </ul>
        </div>
        <div class="bg-white shadow-lg rounded-lg p-8">
      <h2 class="text-2xl font-bold mb-6 text-indigo-600">커뮤니티</h2>
      <ul>
        <li v-for="post in communityPosts" :key="post.id" class="border-b-2 border-gray-100 py-4">
          <a :href="post.url" class="text-indigo-600 hover:text-indigo-800 transition duration-300">{{ post.title }}</a>
          <span class="text-gray-500 text-sm ml-4">{{ post.author }}</span>
        </li>
      </ul>
      <router-link to="/chatroomlist" class="mt-6 text-indigo-600 hover:text-indigo-800 transition duration-300 font-bold">커뮤니티 바로가기</router-link>
    </div>
  </div>  
</div>
</section>
</template>
<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
