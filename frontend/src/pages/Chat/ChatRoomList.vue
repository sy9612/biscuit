<template>
  <div class="container" id="app" v-cloak>
    <div style="margin-top:107px">
      <!-- <div class="row">
        <div>
          <h2>{{ nickname }}</h2>
        </div>
        <div class="col-md-12">
          <h3>채팅방 리스트</h3>
        </div>
      </div> -->
      <p v-if="chatrooms.length==0">참여중인 채팅방이 없습니다.</p>
      <div class="input-group" v-if="chatrooms.length>0">
        <b-input-group>
          <b-form-input style="border-radius:10px" type="text" v-model="searchName"  placeholder="채팅방을 검색해주세요"/>
          
        </b-input-group>
      </div>
      
      <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
      
      <ul class="list-group" v-if="chatrooms.length>0">
        <li
          class="list-group-item list-group-item-action"
          v-for="item in chatrooms"
          v-if="item.roomName.includes(searchName)"
          :key="item.roomId"
          @click="enterRoom(item.roomId)"
        >
          {{ item.roomName }}
        </li>
      </ul>
    </div>
    <!-- <div>
      <component :is="componentLoading()"></component>
    </div> -->
  </div>
</template>
<script>
import axios from 'axios';
// import ChatRoom from './ChatRoom';
import { mapState } from 'vuex';
const CHAT_SERVER_URL = process.env.VUE_APP_CHAT_SERVER_URL;
export default {
  components:{
    // ChatRoom,
  },
  data() {
    return {
      room_name: '',
      chatrooms: [],
      user: {
        email: '',
        nickname: '',
        picture: '',
        region: '',
        phone: '',
      },
      nickname: '',
      searchName: '',
      type: 'title',
      roomId: '',
    };
  },
  props:{
    mode: Number
  },
  created() {

    
    this.user.region = localStorage.getItem('region');
    this.user.email = localStorage.getItem('email');
    this.user.nickname = localStorage.getItem('nickname');
    this.user.phone = localStorage.getItem('phone');

    this.findAllRoom();
    this.nickname = localStorage.getItem('nickname');
  },
  
  computed: {
    ...mapState(['loginStatus']),
  },
  methods: {
    componentLoading(){
      return 'ChatRoom';
    },
    findAllRoom: function() {
      axios.get(`${CHAT_SERVER_URL}/chat/myroom`,
      {
        params:{
          email : this.user.email
        }
      }).then((res) => {
        // for (let i = 0; i < res.data.data.length; i++) {
        //   // console.log(res.data.data[i].roomName.includes('감자'))
        // }
        this.chatrooms = res.data.data;
      });
    },
    
    enterRoom: function(roomId) {
      if (this.nickname != '') {
        localStorage.setItem('wschat.roomId', roomId);
        // this.mode = 1;
        // this.roomId = roomId;
        this.$emit('modeOne')
        // this.$router.push({ path: 'chatroom', querey: { roomId: roomId } });
      }
    },
    
  },
};
</script>
