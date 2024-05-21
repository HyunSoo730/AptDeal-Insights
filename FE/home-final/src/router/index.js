import { createRouter, createWebHistory } from "vue-router";
import MainPage from "@/pages/MainPage.vue";
import MyPage from "@/pages/MyPage.vue";
import ApartmentDetails from "@/components/ApartmentDetails.vue";
import CommunityPage from "@/pages/CommunityPage.vue";
import Kakao from "@/components/Kakao.vue";
import ApartmentListByDong from "@/components/ApartmentListByDong.vue";
import ApartmentMap from "@/components/ApartmentMap.vue";
import Wishlist from "@/pages/Wishlist.vue";
import RegisterSale from "@/components/RegisterSale.vue";

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
      lat: Number(route.params.lat),
      lng: Number(route.params.lng),
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
  { path: "/registerSale", name: "RegisterSale", component: RegisterSale, props: true },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
