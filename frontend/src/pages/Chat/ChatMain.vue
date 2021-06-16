<template>
<div class="mt-5">
  <div class="section">
    <div class="container">
      <div class="button-container ">
        <div class="d-flex justify-content-around" v-if="mode == 0">
          <div class="col" style="font-weight : bold;">
            <h2 style="margin-top:30px;">채팅방 목록</h2>
            <chat-room-list :mode="mode" @modeOne="modeOne()"/>
          </div>
          <div class="col">
            <h2 style="margin-top:30px;">멤버 목록</h2>
            <chat-friend-list :mode="mode" @modeOne="modeOne()"/>
          </div>
        </div>
        <chat-room v-if="mode == 1" @modeZero="modeZero()"/>
        <!-- <h3 v-on:click="loadChatRoomList();" type="button">방 목록</h3>
        <h3 v-on:click="loadChatFriendList()" type="button">멤버 목록</h3> -->
      </div>
      <!-- <component :is="componentLoading()" v-bind:mode="mode"></component> -->
    </div>
  </div>
</div>
</template>

<script>

import ChatRoomList from './ChatRoomList';
import ChatFriendList from './ChatFriendList';

import { mapState } from 'vuex';
import ChatRoom from './ChatRoom.vue';
export default {
  name: 'chatmain',
  bodyClass: 'chatmain-page',
  data() {
    return {
      user: {
        email: '',
        nickname: '',
        picture: '',
        region: '',
        phone: '',
      },
      // active: 0,
      mode: 0,
    };
  },
  created() {
    this.user.region = localStorage.getItem('region');
    this.user.email = localStorage.getItem('email');
    this.user.nickname = localStorage.getItem('nickname');
    this.user.phone = localStorage.getItem('phone');
  },
  computed: {
    ...mapState(['loginStatus']),
  },
  components: {
    ChatRoomList,
    ChatFriendList,
    ChatRoom,
  },
  methods: {
    modeOne: function() {
      this.mode = 1
    },
    modeZero: function() {
      this.mode = 0
    },
    addRoom: function() {
      // console.log("방 개설")
      this.mode = 1
      this.mode = 0
    }
    // componentLoading() {
    //   // console.log("component change");
    //   switch (this.active) {
    //     case 0:
    //       return 'ChatRoomList';
    //     case 1:
    //       return 'ChatFriendList';
    //   }
    // },
    
    // loadChatRoomList() {
    //   this.active = 2;
    //   this.active = 0;
    //   this.mode = 0;
    // },
    // loadChatFriendList() {
    //   this.active = 1;
    // },
  }
}
</script>