<template>
    <div class="review-form">
        <h3>리뷰 작성</h3>
        {{ user }}
        <textarea v-model="content" placeholder="리뷰 내용을 입력하세요"></textarea>
        <select v-model.number="rating">
            <option value="">평점 선택</option>
            <option v-for="star in 5" :key="star" :value="star">{{ star }}점</option>
        </select>
        <button @click="submitReview">작성완료</button>
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
                // 이미 사용자 정보가 스토어에 로드되어 있는지 확인합니다.
                if (!counterStore.user) {
                    try {
                        const token = localStorage.getItem('token');
                        console.log(token);
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
                };
                console.log(reviewData);

                await axios.post('/api/api/apt-review/post', reviewData);
                alert('리뷰가 성공적으로 작성되었습니다.');
                this.$emit('review-submitted');
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
    width: 100%;
    height: 100px;
    margin-bottom: 10px;
}
</style>
