<template>
  <nav class="bg-teal-600 shadow-lg mt-4">
    <div class="container mx-auto py-4">
      <ul class="flex justify-center space-x-12">
        <li><router-link to="/" class="text-white hover:text-teal-200 text-lg">매매</router-link></li>
        <li><router-link to="/lease" class="text-white hover:text-teal-200 text-lg">전월세</router-link></li>
        <li><router-link to="/new-construction" class="text-white hover:text-teal-200 text-lg">신축분양</router-link></li>
        <li><router-link to="/population-trend" class="text-white hover:text-teal-200 text-lg">인구흐름</router-link></li>
      </ul>
    </div>
  </nav>
</template>

<script setup>
</script>