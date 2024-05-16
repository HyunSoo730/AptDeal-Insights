<script setup>
import { ref } from 'vue';

const posts = ref([
  {
    id: 1,
    title: '첫 번째 게시물',
    content: '첫 번째 게시물 내용입니다.',
    author: '작성자1',
    date: '2023-05-17',
  },
  {
    id: 2,
    title: '두 번째 게시물',
    content: '두 번째 게시물 내용입니다.',
    author: '작성자2',
    date: '2023-05-18',
  },
  // 더 많은 게시물 추가
]);
</script>

<template>
  <div class="container mx-auto py-12">
    <h1 class="text-3xl font-bold mb-8">커뮤니티</h1>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
      <div v-for="post in posts" :key="post.id" class="bg-white shadow-md rounded-lg p-6">
        <h2 class="text-xl font-bold mb-4">{{ post.title }}</h2>
        <p class="text-gray-700 mb-4">{{ post.content }}</p>
        <div class="flex justify-between items-center">
          <p class="text-gray-600">작성자: {{ post.author }}</p>
          <p class="text-gray-600">{{ post.date }}</p>
        </div>
      </div>
    </div>
  </div>
</template>