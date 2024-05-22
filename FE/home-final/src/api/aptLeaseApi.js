import axios from 'axios';

const aptLeaseApi = axios.create({
  baseURL: 'http://localhost:8080/api/apt-sales', // 백엔드의 주소를 명시적으로 지정
  withCredentials: true, // 크로스 도메인 쿠키 전송을 허용
});

export function getLeaseListingsByRegionAndDong(searchCondition) {
  return aptLeaseApi.post('/rent-sales', searchCondition);
}

export function getRentSalesByApartmentAndYears(apartmentName, years) {
  return aptLeaseApi.get(`/apartment-rent-sales/${apartmentName}/${years}`);
}

export function getRentSalesByRegionCodeAndYears(regionCode, years) {
  return aptLeaseApi.get(`/region-rent-sales/${regionCode}/${years}`);
}
