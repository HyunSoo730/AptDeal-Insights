<template>
  <div class="review-form bg-white shadow-md rounded-lg p-6">
    <h3 class="text-xl font-semibold mb-4">리뷰 작성</h3>
    <div class="mb-4">
      <label class="block text-gray-700 mb-2">작성자</label>
      <p class="text-gray-800">{{ user.nickname }}</p>
    </div>
    <div class="mb-4">
      <textarea
        v-model="content"
        placeholder="리뷰 내용을 입력하세요"
        class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
      ></textarea>
    </div>
    <div class="mb-4">
      <select
        v-model.number="rating"
        class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
      >
        <option value="">평점 선택</option>
        <option v-for="star in 5" :key="star" :value="star">{{ star }}점</option>
      </select>
    </div>
    <button
      @click="submitReview"
      class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 transition-colors duration-200 w-full"
    >
      작성완료
    </button>
  </div>
</template>

<script>
import axios from 'axios';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useCounterStore } from '@/stores/counter';

export default {
  props: {
    apartment: {
      type: Object,
      required: true,
    },
    user: {
      type: Object,
      required: true,
    },
    onReviewSubmitted: {
      type: Function,
      required: true,
    },
  },
  data() {
    return {
      content: '',
      rating: '',
    };
  },
  setup() {
    const counterStore = useCounterStore();
    const router = useRouter();

    onMounted(async () => {
      if (counterStore.isLoggedIn) {
        if (!counterStore.user) {
          try {
            const token = localStorage.getItem('token');
            const response = await axios.get('/api/user', {
              headers: { Authorization: `${token}` },
            });
            counterStore.user = response.data;
          } catch (error) {
            console.error(error);
            router.push('/');
          }
        }
      } else {
        router.push('/');
      }
    });

    return {
      counterStore,
    };
  },
  methods: {
    async submitReview() {
      try {
        const reviewData = {
          memberId: this.counterStore.user.id,
          apartmentCode: this.apartment.aptCode,
          content: this.content,
          rating: this.rating,
          member: { nickname: this.counterStore.user.nickname },
        };

        await axios.post('/api/api/apt-review/post', reviewData);
        alert('리뷰가 성공적으로 작성되었습니다.');
        this.$emit('review-submitted', reviewData);
      } catch (error) {
        console.error('리뷰 작성 중 오류가 발생했습니다:', error);
        alert('리뷰 작성 중 오류가 발생했습니다.');
      }
    },
  },
};
</script>

<style scoped>
.review-form {
  margin-top: 20px;
}

textarea {
  resize: vertical;
}
</style>
