<template>
  <nav class="bg-gray-100 shadow">
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

<style scoped>
.navbar {
  border-bottom: 1px solid #e2e8f0; /* Tailwind's border-gray-300 */
}
</style>
