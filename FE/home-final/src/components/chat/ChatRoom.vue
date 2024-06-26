<template>
  <div class="chat-container">
    <div class="header">
      <h2 class="room-name">채팅방: {{ roomName }}</h2>
      <button class="leave-button" @click="leaveRoom">채팅방 나가기</button>
    </div>
    <div class="messages" ref="messageContainer">
      <div v-for="(item, idx) in recvList" :key="idx" :class="[item.type === 'CHAT' ? 'message' : 'system-message',
      {
        'my-message': item.sender === userNickname && item.type === 'CHAT',
        'other-message': item.sender !== userNickname && item.type === 'CHAT'
      }]">
        <template v-if="item.type === 'CHAT'">
          <h3 class="sender">{{ item.sender }}</h3>
          <p class="content">{{ item.content }}</p>
        </template>
        <template v-else>
          <p class="content">{{ item.content }}</p>
        </template>
      </div>
    </div>
    <button v-if="showScrollButton" class="scroll-button" @click="scrollToBottom">새 메시지 보기</button>
    <div class="input-container">
      <input v-model="message" placeholder="메시지 입력" @keyup.enter="sendMessage">
      <button class="send-button" @click="sendMessage">전송</button>
    </div>
  </div>
</template>



<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { useCounterStore } from '@/stores/counter';
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'ChatRoom',
  props: ['roomName'],
  setup(props) {
    const counterStore = useCounterStore();
    const message = ref("");
    const recvList = ref([]);
    const stompClient = ref(null);
    const connected = ref(false);
    const showScrollButton = ref(false);
    const messageContainer = ref(null);
    const router = useRouter();

    const userNickname = counterStore.user.nickname;

    const sendMessage = () => {
      if (userNickname !== '' && message.value !== '' && props.roomName !== '') {
        send('CHAT', message.value);
        message.value = '';
      }
    };

    const send = (type, content) => {
      console.log("Send message:" + content);
      if (stompClient.value && stompClient.value.connected) {
        const msg = {
          sender: userNickname,
          content: content,
          type: type
        };
        stompClient.value.send(`/app/chat.sendMessage/${props.roomName}`, JSON.stringify(msg), {});
      }
    };

    const connect = () => {
      const serverURL = "/ws";
      let socket = new SockJS(serverURL);
      stompClient.value = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      stompClient.value.connect(
        {},
        frame => {
          connected.value = true;
          console.log('소켓 연결 성공', frame);
          subscribeToRoom(props.roomName);
          send('JOIN', `${userNickname} 님이 입장하셨습니다.`);
        },
        error => {
          console.log('소켓 연결 실패', error);
          connected.value = false;
        }
      );
    };

    const subscribeToRoom = (roomName) => {
      stompClient.value.subscribe(`/topic/${roomName}`, res => {
        console.log('구독으로 받은 메시지 입니다.', res.body);
        recvList.value.push(JSON.parse(res.body));
        if (isAtBottom()) {
          scrollToBottom();
        } else {
          showScrollButton.value = true;
        }
      });
    };

    const leaveRoom = () => {
      if (stompClient.value && props.roomName) {
        send('LEAVE', `${userNickname} 님이 퇴장하셨습니다.`);
        stompClient.value.unsubscribe(`/topic/${props.roomName}`);
      }
      router.push({ name: 'ChatRoomList' });
    };

    const scrollToBottom = () => {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight;
      showScrollButton.value = false;
    };

    const isAtBottom = () => {
      return messageContainer.value.scrollHeight - messageContainer.value.scrollTop === messageContainer.value.clientHeight;
    };

    onMounted(connect);

    return {
      message,
      recvList,
      sendMessage,
      leaveRoom,
      userNickname,
      showScrollButton,
      scrollToBottom,
      messageContainer
    };
  }
}
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&display=swap');

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 80%;
  max-width: 900px;
  margin: 0 auto;
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  font-family: 'Noto Sans KR', sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #4CAF50;
  color: white;
  font-family: 'Noto Sans KR', sans-serif;
}

.room-name {
  margin: 0;
  font-family: 'Noto Sans KR', sans-serif;
}

.leave-button {
  background-color: #ff4d4d;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Noto Sans KR', sans-serif;
}

.leave-button:hover {
  background-color: #ff1a1a;
}

.messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  font-family: 'Noto Sans KR', sans-serif;
}

.message {
  margin: 10px 0;
  padding: 10px;
  border-radius: 10px;
  max-width: 70%;
  display: flex;
  flex-direction: column;
  font-family: 'Noto Sans KR', sans-serif;
}

.my-message {
  align-self: flex-end;
  background-color: #DCF8C6;
}

.other-message {
  align-self: flex-start;
  background-color: #FFFFFF;
  border: 1px solid #ccc;
}

.system-message {
  align-self: center;
  background-color: transparent;
  color: #999;
  font-size: 0.9rem;
  margin: 10px 0;
  font-family: 'Noto Sans KR', sans-serif;
}

.sender {
  font-weight: bold;
  margin-bottom: 5px;
  font-family: 'Noto Sans KR', sans-serif;
}

.content {
  margin: 0;
  font-family: 'Noto Sans KR', sans-serif;
}

.input-container {
  display: flex;
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #ccc;
  font-family: 'Noto Sans KR', sans-serif;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
  font-family: 'Noto Sans KR', sans-serif;
}

.send-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Noto Sans KR', sans-serif;
}

.send-button:hover {
  background-color: #45a049;
}

.scroll-button {
  position: absolute;
  bottom: 80px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #ccc;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 20px;
  cursor: pointer;
  font-size: 0.9rem;
  font-family: 'Noto Sans KR', sans-serif;
}

.scroll-button:hover {
  background-color: #999;
}
</style>
