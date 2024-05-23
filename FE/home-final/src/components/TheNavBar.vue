<template>
  <nav class="bg-white shadow-lg mt-4">
    <div class="container mx-auto py-4">
      <ul class="flex justify-center space-x-8">

        <li><router-link to="/" class="text-gray-700 hover:text-black text-lg">매매</router-link></li>
        <li><router-link to="/lease" class="text-gray-700 hover:text-black text-lg">전월세</router-link></li>
        <li><router-link to="/new-construction" class="text-gray-700 hover:text-black text-lg">신축분양</router-link></li>
        <li><router-link to="/population-trend" class="text-gray-700 hover:text-black text-lg">인구흐름</router-link></li>


      </ul>
    </div>
  </nav>
</template>

<script setup>
</script>

<style scoped>
nav {
  border-bottom: 2px solid #e2e8f0;
}

nav ul li a {
  padding: 8px 12px;
  border-radius: 8px;
  transition: background-color 0.3s, color 0.3s;
}

nav ul li a:hover {
  background-color: #e2e8f0;
  color: #1a202c;
}
</style>
