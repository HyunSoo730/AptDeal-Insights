import { createRouter, createWebHistory } from "vue-router";
import MainPage from "@/pages/MainPage.vue";
import MyPage from "@/pages/MyPage.vue";
import CommunityPage from "@/pages/CommunityPage.vue";
import Kakao from "@/components/Kakao.vue";
import ApartmentListByDong from "@/components/ApartmentListByDong.vue";
import ApartmentMap from "@/components/ApartmentMap.vue";
import Wishlist from "@/pages/Wishlist.vue";

const routes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPage,
  },
  {
    path: "/community",
    name: "communityPage",
    component: CommunityPage,
  },
  {
    path: "/map",
    name: "KakaoMap",
    component: Kakao,
    props: (route) => ({
      lat: Number(route.query.latitude),
      lng: Number(route.query.longitude),
      aptCode: route.query.aptCode,
      aptName: route.query.aptName,
      dongcode: route.query.dongcode
    }),
  },
  {
    path: "/apartments/:dongCode",
    name: "ApartmentListByDong",
    component: ApartmentListByDong,
  },
  {
    path: "/apartment-map",
    name: "ApartmentMap",
    component: ApartmentMap,
    props: true,
  },
  {
    path: "/wishlist",
    name: "Wishlist",
    component: Wishlist,
  },
  {
    path: "/asdasd",
    name : "chart",
    component: () => import("@/components/AptTransactionChart.vue"),
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
