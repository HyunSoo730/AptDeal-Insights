// src/stores/mapStore.js
import { defineStore } from "pinia";

export const useMapStore = defineStore("map", {
  state: () => ({
    lat: null,
    lng: null,
  }),
  actions: {
    setCoordinates(lat, lng) {
      this.lat = lat;
      this.lng = lng;
    },
  },
});
