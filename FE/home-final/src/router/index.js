import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '@/pages/MainPage.vue';
import MyPage from '@/pages/MyPage.vue';
import ApartmentDetails from '@/pages/ApartmentDetails.vue';
import CommunityPage from '@/pages/CommunityPage.vue';

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage,
  },
  {
    path: '/apartment/:id',
    name: 'ApartmentDetails',
    component: ApartmentDetails,
  },
  {
    path : "/community",
    name : "communityPage",
    component: CommunityPage,
  }
];


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
