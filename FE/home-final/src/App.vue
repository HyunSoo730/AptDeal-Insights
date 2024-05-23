<script setup>
import TheFooter from "@/components/TheFooter.vue";
import TheHeader from "@/components/TheHeader.vue";
import TheNavBar from "@/components/TheNavBar.vue";
import TheSearch from "@/components/TheSearch.vue";
import { onMounted } from 'vue';
import { useCounterStore } from '@/stores/counter';

const store = useCounterStore();

onMounted(() => {
  store.restoreLoginState();
});
</script>

<template>
  <div class="flex flex-col min-h-screen ">
    <TheHeader />
    <TheNavBar />
    <TheSearch />
    <main class="container mx-auto px-4 py-8">
      <router-view></router-view>
    </main>
    <TheFooter />
  </div>
</template>