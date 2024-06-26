// src/api/aptSaleApi.js
import axios from "axios";

const aptSaleApi = axios.create({
  baseURL: "http://localhost:8080/api", // 백엔드의 주소를 명시적으로 지정
  withCredentials: true, // 크로스 도메인 쿠키 전송을 허용
});

// export function getAptNamesByPrefix(prefix, page = 1, size = 10) {
//   return aptSaleApi.get("/apt-sales/apt-names", {
//     params: { prefix, page, size },
//   });
// }

// export function getAptSaleDetails(aptCode) {
//   return aptSaleApi.get(`/apt-sales/details/${aptCode}`);
// }
// export function sample(dongCode) {
//   console.log(dongCode);
//   return aptSaleApi.get(`/apt-sales/apartments`, {
//     params: { dongCode },
//   });
// }
