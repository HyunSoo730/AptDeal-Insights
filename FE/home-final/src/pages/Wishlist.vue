<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import axios from 'axios';
import { useCounterStore } from '@/stores/counter.js';
import { useRouter } from 'vue-router';

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
    const selectedApartment = likedApts.value.find(apt => apt.aptCode === aptCode);
    localStorage.setItem('selectedApartment', JSON.stringify(selectedApartment));
    localStorage.setItem('apartments', JSON.stringify(groupedApts.value[aptCode]));
    router.push({
        name: 'ApartmentMap',
        params: { aptCode },
    });
};

const loadLikedAptsFromLocalStorage = () => {
    const storedApts = localStorage.getItem('likedApts');
    if (storedApts) {
        likedApts.value = JSON.parse(storedApts);
        groupedApts.value = groupByAptCode(likedApts.value);
    }
};

const saveLikedAptsToLocalStorage = () => {
    localStorage.setItem('likedApts', JSON.stringify(likedApts.value));
};

onMounted(async () => {
    const token = localStorage.getItem('token');
    if (token) {
        loadLikedAptsFromLocalStorage();  // 로컬 스토리지에서 데이터 로드
        try {
            const response = await axios.get('/api/api/likes', {
                params: { memberId: memberId.value },
                headers: { Authorization: `${token}` },
            });
            likedApts.value = response.data;
            groupedApts.value = groupByAptCode(likedApts.value);
            saveLikedAptsToLocalStorage();  // 데이터 로드 후 로컬 스토리지에 저장
        } catch (error) {
            console.error('찜 목록을 가져오는 중 오류가 발생했습니다:', error);
        }
    }
});

// likedApts가 변경될 때마다 로컬 스토리지에 저장
watch(likedApts, saveLikedAptsToLocalStorage);

</script>

<template>
    <div class="container mx-auto py-8 px-4 lg:px-8">
        <h2 class="text-3xl font-bold mb-6 text-gray-800">찜 목록</h2>
        <div v-if="Object.keys(groupedApts).length">
            <div v-for="(apts, aptCode) in groupedApts" :key="aptCode" class="mb-8 p-4 bg-white shadow-md rounded-lg border border-gray-200">
                <h3 class="text-xl font-bold mb-2 text-gray-800">아파트 코드: {{ aptCode }}</h3>
                <div v-if="apts.length" @click="navigateToAptDetail(aptCode)" class="cursor-pointer">
                    <p class="text-lg text-gray-700"><strong>아파트 이름:</strong> {{ apts[0].aptName }}</p>
                    <p class="text-lg text-gray-700"><strong>주소:</strong> {{ apts[0].roadNameAddress }}</p>
                </div>
            </div>
        </div>
        <div v-else>
            <p class="text-lg text-gray-700">찜한 아파트가 없습니다.</p>
        </div>
    </div>
</template>

<style scoped>
/* 추가적인 스타일을 여기에서 정의할 수 있습니다 */
</style>
