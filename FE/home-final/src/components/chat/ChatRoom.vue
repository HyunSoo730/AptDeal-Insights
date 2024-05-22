<template>
    <div class="chat-container">
        <button class="leave-button" @click="leaveRoom">채팅방 나가기</button>
        <h2>채팅방: {{ roomName }}</h2>
        <div class="messages" ref="messageContainer">
            <div v-for="(item, idx) in recvList" :key="idx"
                :class="['message', { 'my-message': item.sender === userNickname, 'other-message': item.sender !== userNickname }]">
                <h3>{{ item.sender }}</h3>
                <p>{{ item.content }}</p>
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
.chat-container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    width: 80%;
    /* Increase the width */
    max-width: 1200px;
    /* Set a maximum width */
    margin: 0 auto;
    border: 1px solid #ccc;
    border-radius: 10px;
    overflow: hidden;
    position: relative;
}

.leave-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: #ff4d4d;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
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
}

.message {
    margin: 10px 0;
    padding: 10px;
    border-radius: 10px;
    max-width: 70%;
    /* Increase the max width of messages */
    display: flex;
    flex-direction: column;
}

.my-message {
    align-self: flex-end;
    background-color: #DCF8C6;
    /* Light green for own messages */
}

.other-message {
    align-self: flex-start;
    background-color: #FFFFFF;
    /* White for other messages */
    border: 1px solid #ccc;
}

.input-container {
    display: flex;
    padding: 10px;
    background-color: #fff;
    border-top: 1px solid #ccc;
}

input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-right: 10px;
}

.send-button {
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.send-button:hover {
    background-color: #45a049;
}

.scroll-button {
    position: absolute;
    bottom: 60px;
    right: 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.scroll-button:hover {
    background-color: #45a049;
}
</style>
