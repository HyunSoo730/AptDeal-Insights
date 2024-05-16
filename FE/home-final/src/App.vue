<script setup>
import TheHeader from "@/components/TheHeader.vue";
import TheNavBar from "@/components/TheNavBar.vue";
import TheFooter from "@/components/TheFooter.vue";
import TheSearch from "@/components/TheSearch.vue";
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <TheHeader />
    <TheNavBar />
    <TheSearch />
    <router-view></router-view>
    <TheFooter />
  </div>
</template>