import axios from 'axios';

const aptSchoolApi = axios.create({
  baseURL: 'http://localhost:8080/api', // 백엔드 주소
  withCredentials: true, // 크로스 도메인 쿠키 전송을 허용
});

export function getAptSchoolInfo(aptCode, aptName) {
  return aptSchoolApi.get(`/apt-school/${aptCode}/${aptName}`);
}
