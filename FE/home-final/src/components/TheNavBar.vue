<template>
  <nav class="bg-white shadow-lg mt-4">
    <div class="container mx-auto py-4">
      <ul class="flex justify-center space-x-8">
        <li>
          <a href="#" class="text-gray-800 hover:text-blue-600 font-semibold text-lg">매매</a>
        </li>
        <li>
          <a href="#" class="text-gray-800 hover:text-blue-600 font-semibold text-lg">전월세</a>
        </li>
        <li>
          <a href="#" class="text-gray-800 hover:text-blue-600 font-semibold text-lg">신축분양</a>
        </li>
        <li>
          <a href="#" class="text-gray-800 hover:text-blue-600 font-semibold text-lg">인구흐름</a>
        </li>
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
