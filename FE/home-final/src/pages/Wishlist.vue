<script setup>
import { ref, onMounted, computed, watch } from "vue";
import axios from "axios";
import { useCounterStore } from "@/stores/counter.js";
import { useRouter } from "vue-router";

const store = useCounterStore();
const likedApts = ref([]);
const groupedApts = ref({});
const memberId = computed(() => store.user.id);
const router = useRouter();

const groupByAptCode = (apartments) => {
  return apartments.reduce((groups, apt) => {
    const { aptCode } = apt;
    if (!groups[aptCode]) {
      groups[aptCode] = [];
    }
    groups[aptCode].push(apt);
    return groups;
  }, {});
};

const navigateToAptDetail = (aptCode) => {
  const selectedApartment = likedApts.value.find(
    (apt) => apt.aptCode === aptCode
  );
  localStorage.setItem("selectedApartment", JSON.stringify(selectedApartment));
  localStorage.setItem(
    "apartments",
    JSON.stringify(groupedApts.value[aptCode])
  );
  router.push({
    name: "ApartmentMap",
    params: { aptCode },
  });
};

const loadLikedAptsFromLocalStorage = () => {
  const storedApts = localStorage.getItem("likedApts");
  if (storedApts) {
    likedApts.value = JSON.parse(storedApts);
    groupedApts.value = groupByAptCode(likedApts.value);
  }
};

const saveLikedAptsToLocalStorage = () => {
  localStorage.setItem("likedApts", JSON.stringify(likedApts.value));
};

onMounted(async () => {
  const token = localStorage.getItem("token");
  if (token) {
    loadLikedAptsFromLocalStorage(); // 로컬 스토리지에서 데이터 로드
    try {
      const response = await axios.get("/api/api/likes", {
        params: { memberId: memberId.value },
        headers: { Authorization: `${token}` },
      });
      likedApts.value = response.data;
      groupedApts.value = groupByAptCode(likedApts.value);
      saveLikedAptsToLocalStorage(); // 데이터 로드 후 로컬 스토리지에 저장
    } catch (error) {
      console.error("찜 목록을 가져오는 중 오류가 발생했습니다:", error);
    }
  }
});

// likedApts가 변경될 때마다 로컬 스토리지에 저장
watch(likedApts, saveLikedAptsToLocalStorage);
</script>

<template>
  <div class="container mx-auto py-12 px-4 lg:px-8">
    <h2 class="text-4xl font-bold mb-8 text-indigo-600 flex items-center">
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
          d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
        />
      </svg>
      찜 목록
    </h2>
    <div
      v-if="Object.keys(groupedApts).length"
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
    >
      <div
        v-for="(apts, aptCode) in groupedApts"
        :key="aptCode"
        @click="navigateToAptDetail(aptCode)"
        class="bg-white shadow-lg rounded-2xl p-6 cursor-pointer transition duration-300 ease-in-out transform hover:-translate-y-1 hover:scale-105"
      >
        <div class="flex items-center mb-4">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            class="w-8 h-8 text-indigo-600 mr-2"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"
            />
          </svg>
          <h3 class="text-2xl font-semibold text-gray-800">
            {{ apts[0].aptName }}
          </h3>
        </div>
        <p class="text-lg text-gray-600">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            class="w-5 h-5 inline-block mr-1"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
            />
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
            />
          </svg>
          {{ apts[0].roadNameAddress }}
        </p>
      </div>
    </div>
    <div v-else class="bg-white shadow-lg rounded-2xl p-6">
      <p class="text-xl text-gray-700">찜한 아파트가 없습니다.</p>
    </div>
  </div>
</template>

<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
