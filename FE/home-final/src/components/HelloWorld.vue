<template>
  <div>
    <h1>{{ message }}</h1>
    <button @click="fetchData">Fetch Data</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      message: '',
    };
  },
  methods: {
    fetchData() {
      axios.get('/api/hello')
        .then(response => {
          this.message = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
  },
};
</script>