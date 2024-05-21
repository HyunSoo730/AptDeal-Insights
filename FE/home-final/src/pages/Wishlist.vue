<script setup>
import { ref, onMounted, computed } from 'vue';
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
    router.push({
        name: 'ApartmentMap',
        params: {
            apartments: JSON.stringify(groupedApts.value[aptCode]),
            initialApartment: JSON.stringify(selectedApartment),
        },
    });
};

onMounted(async () => {
    const token = localStorage.getItem('token');
    if (token) {
        try {
            const response = await axios.get('/api/api/likes', {
                params: { memberId: memberId.value },
                headers: { Authorization: `Bearer ${token}` },
            });
            likedApts.value = response.data;
            groupedApts.value = groupByAptCode(likedApts.value);
        } catch (error) {
            console.error('찜 목록을 가져오는 중 오류가 발생했습니다:', error);
        }
    }
});
</script>

<template>
    <div class="container mx-auto py-8">
        <h2 class="text-2xl font-bold mb-4">찜 목록</h2>
        <div v-if="Object.keys(groupedApts).length">
            <div v-for="(apts, aptCode) in groupedApts" :key="aptCode" class="mb-8 p-4 border rounded-md">
                <h3 class="text-xl font-bold mb-2">아파트 코드: {{ aptCode }}</h3>
                <div v-if="apts.length" @click="navigateToAptDetail(aptCode)" class="cursor-pointer">
                    <p><strong>아파트 이름:</strong> {{ apts[0].aptName }}</p>
                    <p><strong>주소:</strong> {{ apts[0].roadNameAddress }}</p>
                </div>
            </div>
        </div>
        <div v-else>
            <p>찜한 아파트가 없습니다.</p>
        </div>
    </div>
</template>
