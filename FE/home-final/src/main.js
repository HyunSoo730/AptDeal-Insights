import { createApp } from "vue";
import { createPinia } from "pinia";
import { useKakao } from "vue3-kakao-maps/@utils";
import "./index.css";

import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale } from 'chart.js';

// Register chart.js components globally
ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale);

import App from "./App.vue";
import router from "./router";
useKakao("49c41bd6197ebea05e29425dd6780f25");

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
