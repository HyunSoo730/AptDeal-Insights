// aptSaleApi.js
import axios from "axios";

const aptSaleApi = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true,
});

export function getAptNamesByPrefix(prefix, page = 1, size = 10) {
  return aptSaleApi.get("/apt-sales/apt-names", {
    params: { prefix, page, size },
  });
}

export function sample(dongCode) {
  console.log(dongCode);
  return aptSaleApi.get(`/apt-sales/apartments`, {
    params: { dongCode },
  });
}
