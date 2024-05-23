<template>
  <section class="container mx-auto py-12">
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
      <div class="lg:col-span-2">
        <div class="bg-white shadow-lg rounded-lg p-8 mb-8">
          <h2 class="text-3xl font-bold mb-8 text-indigo-600">아파트 검색</h2>
          <div class="mb-6">
            <label class="block text-gray-700 font-bold mb-2" for="city">시/도 선택</label>
            <div class="relative">
              <select id="city" v-model="selectedCity" @change="handleCityChange"
                class="w-full p-4 rounded-t border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700 appearance-none">
                <option value="" disabled>시/도 선택</option>
                <option v-for="city in cities" :key="city.code" :value="city.code">
                  {{ city.name }}
                </option>
              </select>
              <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v10.59l3.3-3.3a1 1 0 011.4 1.42l-5 5a1 1 0 01-1.4 0l-5-5a1 1 0 011.4-1.42l3.3 3.3V4a1 1 0 011-1z"
                    clip-rule="evenodd" />
                </svg>
              </div>
            </div>
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 font-bold mb-2" for="gu">구/군 선택</label>
            <div class="relative">
              <select id="gu" v-model="selectedGu" @change="handleGuChange"
                class="w-full p-4 border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700 appearance-none">
                <option value="" disabled>구/군 선택</option>
                <option v-for="gu in gus" :key="gu.code" :value="gu.code">
                  {{ gu.name.split(" ")[1] }}
                </option>
              </select>
              <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v10.59l3.3-3.3a1 1 0 011.4 1.42l-5 5a1 1 0 01-1.4 0l-5-5a1 1 0 011.4-1.42l3.3 3.3V4a1 1 0 011-1z"
                    clip-rule="evenodd" />
                </svg>
              </div>
            </div>
          </div>
          <div class="mb-8">
            <label class="block text-gray-700 font-bold mb-2" for="dong">동 선택</label>
            <div class="relative">
              <select id="dong" v-model="selectedDong"
                class="w-full p-4 rounded-b border-2 border-gray-300 focus:outline-none focus:border-indigo-500 text-lg text-gray-700 appearance-none">
                <option value="" disabled>동 선택</option>
                <option v-for="dong in dongs" :key="dong.code" :value="dong.code">
                  {{ dong.name.split(" ").pop() }}
                </option>
              </select>
              <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none">
                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v10.59l3.3-3.3a1 1 0 011.4 1.42l-5 5a1 1 0 01-1.4 0l-5-5a1 1 0 011.4-1.42l3.3 3.3V4a1 1 0 011-1z"
                    clip-rule="evenodd" />
                </svg>
              </div>
            </div>
          </div>
          <button @click="searchApartments"
            class="bg-indigo-600 text-white px-8 py-4 rounded-lg text-lg w-full hover:bg-indigo-700 transition duration-300">
            검색
          </button>
        </div>
        <div class="bg-white shadow-lg rounded-lg p-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">VR로 내 집 구경하기</h2>
          <p class="text-gray-700 mb-6">VR 기기로 내 집을 미리 살펴볼 수 있습니다.</p>
        </div>
      </div>
      <div>
        <div class="bg-white shadow-lg rounded-lg p-8">
          <h2 class="text-2xl font-bold mb-6 text-indigo-600">커뮤니티</h2>
          <ul>
            <li v-for="post in communityPosts" :key="post.id" class="border-b-2 border-gray-100 py-4">
              <a :href="post.url" class="text-indigo-600 hover:text-indigo-800 transition duration-300">{{ post.title
                }}</a>
              <span class="text-gray-500 text-sm ml-4">{{ post.author }}</span>
            </li>
          </ul>
          <router-link to="/chatroomlist"
            class="mt-6 text-indigo-600 hover:text-indigo-800 transition duration-300 font-bold">커뮤니티 바로가기</router-link>
        </div>
        <div class="bg-white shadow-lg rounded-lg p-8">
          <h2 class="text-xl font-bold mb-4">AI한테 다 물어봐!</h2>
          <div class="chat-history mb-4">
            <div v-for="(message, index) in chatHistory" :key="index" :class="message.role">
              <p><strong>{{ message.role === 'user' ? '나' : 'AI 봇🤖' }}:</strong> {{ message.content }}</p>
            </div>
          </div>
          <div class="flex">
            <input v-model="userMessage" @keyup.enter="sendMessage" type="text"
              class="flex-grow p-2 border border-gray-300 rounded" placeholder="Type your message..." />
            <button @click="sendMessage" class="ml-2 p-2 bg-blue-500 text-white rounded">Send</button>
            <button @click="saveChat" class="ml-2 p-2 bg-green-500 text-white rounded">Save</button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { callOpenAI } from '@/api';
import { useCounterStore } from '@/stores/counter'; // Assuming this is where the user data is stored

const router = useRouter();
const store = useCounterStore();
const user = ref(null);
const memberId = ref(null);
const sessionId = ref(Date.now().toString()); // 세션 ID 생성

const selectedCity = ref("");
const selectedGu = ref("");
const selectedDong = ref("");

const cities = ref([]);
const gus = ref([]);
const dongs = ref([]);
const userMessage = ref('');
const chatHistory = ref([]);

onMounted(async () => {
  await fetchCities();
  try {
    const response = await axios.get("/api/user");
    user.value = response.data;
    memberId.value = user.value.id; // user.id를 memberId에 설정
  } catch (error) {
    console.error("Error fetching user data:", error);
    logout();
  }
});

const fetchCities = async () => {
  try {
    const response = await axios.get(
      "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000"
    );
    cities.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch cities:", error);
  }
};

const fetchGus = async (cityCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${cityCode.substr(0, 2)}*00000&is_ignore_zero=true`
    );
    gus.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch gus:", error);
  }
};

const fetchDongs = async (guCode) => {
  try {
    const response = await axios.get(
      `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${guCode.substr(0, 5)}*&is_ignore_zero=true`
    );
    dongs.value = response.data.regcodes;
  } catch (error) {
    console.error("Failed to fetch dongs:", error);
  }
};

const handleCityChange = () => {
  if (selectedCity.value) {
    fetchGus(selectedCity.value);
  } else {
    gus.value = [];
    selectedGu.value = "";
  }
};

const handleGuChange = () => {
  if (selectedGu.value) {
    fetchDongs(selectedGu.value);
  } else {
    dongs.value = [];
    selectedDong.value = "";
  }
};

const searchApartments = () => {
  if (selectedDong.value) {
    router.push({
      name: "ApartmentListByDong",
      params: { dongCode: selectedDong.value },
    });
  }
};

const sendMessage = async () => {
  if (userMessage.value.trim() === '') return;

  const userEntry = { role: 'user', content: userMessage.value };
  chatHistory.value.push(userEntry);

  const currentMessage = userMessage.value;  // 현재 메시지를 저장
  userMessage.value = '';  // 메시지 입력칸 비우기

  try {
    const response = await callOpenAI(currentMessage);
    chatHistory.value.push({ role: 'assistant', content: response });
  } catch (error) {
    chatHistory.value.push({ role: 'assistant', content: 'Failed to fetch response from OpenAI API' });
  }
};

const saveChat = async () => {
  console.log(memberId.value);
  try {
    await axios.post('/api/api/chat/save',
      chatHistory.value,  // 메시지를 바디에 전달
      {
        params: {
          memberId: memberId.value,  // memberId를 쿼리 파라미터로 전달
          sessionId: sessionId.value  // sessionId를 쿼리 파라미터로 전달
        }
      }
    );
  } catch (error) {
    console.error('Failed to save chat message:', error);
  }
};

const loadChatHistory = async (memberId, sessionId) => {
  try {
    const response = await axios.get(`/api/api/chat/${memberId}/${sessionId}`);
    chatHistory.value = response.data;
  } catch (error) {
    console.error('Failed to load chat history:', error);
  }
};
</script>

<style scoped>
.bg-gray-50 {
  background-color: #f9fafb;
}

.text-indigo-600 {
  color: #5a67d8;
}

.border-gray-300 {
  border-color: #d2d6dc;
}

.focus\:border-indigo-500:focus {
  border-color: #667eea;
}

.hover\:bg-indigo-50:hover {
  background-color: #ebf4ff;
}

.hover\:bg-indigo-600:hover {
  background-color: #4c51bf;
}

.bg-indigo-500 {
  background-color: #667eea;
}

.text-gray-600 {
  color: #718096;
}

.bg-gray-200 {
  background-color: #edf2f7;
}

.bg-green-500 {
  background-color: #48bb78;
}

.hover\:bg-green-600:hover {
  background-color: #38a169;
}

.dropdown-list {
  max-height: 14rem;
  /* 7 items * 2rem each */
  overflow-y: auto;
}

.chat-history {
  max-height: 500px;
  /* Increased height */
  overflow-y: auto;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #f9f9f9;
}

.user {
  text-align: right;
  color: blue;
}

.assistant {
  text-align: left;
  color: green;
}
</style>