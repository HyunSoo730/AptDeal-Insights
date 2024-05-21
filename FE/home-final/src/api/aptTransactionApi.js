import axios from 'axios';

const aptTransactionApi = axios.create({
  baseURL: 'http://localhost:8080/api', // 백엔드 주소
  withCredentials: true, // 크로스 도메인 쿠키 전송을 허용
});

export function getTransactionsByDongcode(dongcode, years) {
  console.log(`Calling API: /apt-sales/area/${dongcode}/${years}`);
  return aptTransactionApi.get(`/apt-sales/area/${dongcode}/${years}`);
}

export function getTransactionsByAptCode(aptCode, years) {
  console.log(`Calling API: /apt-sales/apartment/${aptCode}/${years}`);
  return aptTransactionApi.get(`/apt-sales/apartment/${aptCode}/${years}`);
}
