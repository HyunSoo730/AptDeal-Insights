import { defineStore } from 'pinia';

export const useMapStore = defineStore('map', {
  state: () => ({
    lat: null,
    lng: null,
    selectedAptCode: null,
    dongcode: null, // dongcode 추가
  }),
  actions: {
    setCoordinates(lat, lng) {
      this.lat = lat;
      this.lng = lng;
    },
    setSelectedAptCode(aptCode) {
      this.selectedAptCode = aptCode;
    },
    setDongcode(dongcode) { // dongcode 설정 액션 추가
      this.dongcode = dongcode;
    }
  },
});
