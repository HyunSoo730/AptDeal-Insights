<template>
  <nav class="bg-gradient-to-r from-blue-500 via-teal-400 to-purple-500 shadow-xl mt-4">
    <div class="container mx-auto py-4 px-4">
      <ul class="flex justify-center space-x-12">
        <li>
          <router-link
            to="/"
            class="text-white hover:text-teal-200 text-xl font-semibold transition-colors duration-300"
          >
            매매
          </router-link>
        </li>
        <li>
          <router-link
            to="/lease"
            class="text-white hover:text-teal-200 text-xl font-semibold transition-colors duration-300"
          >
            전월세
          </router-link>
        </li>
        <li>
          <router-link
            to="/new-construction"
            class="text-white hover:text-teal-200 text-xl font-semibold transition-colors duration-300"
          >
            신축분양
          </router-link>
        </li>
        <li>
          <router-link
            to="/population-trend"
            class="text-white hover:text-teal-200 text-xl font-semibold transition-colors duration-300"
          >
            인구흐름
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
nav {
  background: linear-gradient(90deg, #1e3a8a, #38b2ac, #805ad5);
}
</style>
