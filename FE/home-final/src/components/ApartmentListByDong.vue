<template>
  <div>
    <h2>아파트 목록</h2>
    <ul>
      <li v-for="apartment in apartments" :key="apartment.aptCode">
        {{apartment}}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      apartments: [],
    };
  },
  created() {
    const dongCode = this.$route.params.dongCode;
    this.fetchApartments(dongCode);
  },
  methods: {
    async fetchApartments(dongCode) {
      try {
        const response = await axios.get(`/apartments?dongcode=${dongCode}`);
        this.apartments = response.data;
      } catch (error) {
        console.error("Failed to fetch apartments:", error);
      }
    },
  },
};
</script>