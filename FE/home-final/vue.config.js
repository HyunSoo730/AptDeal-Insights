// vue.config.js
module.exports = {
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8080", // Spring 백엔드의 도메인과 포트를 지정
        changeOrigin: true,
      },
    },
  },
};
