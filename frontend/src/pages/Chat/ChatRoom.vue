<template>
  <div class="container" id="app" v-cloak>
    <div>
      <h2>{{ room.roomName }}</h2>
    </div>

    <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
    <div >
      <drop-down  :title="`참여 인원 : ` +members.length  " class="d-flex">
        <div v-for="member in members" v-bind:key="member.crmId">

        <div class="dropdown-item" style="margin-right:5px">{{member.nickname}}</div>
        <!-- <div v-if="member.isJoin==1" class="dropdown-item" style="margin-right:5px">{{member.nickname}}</div>
        <div v-else class="dropdown-item" style="background:#9b9b9b; margin-right:5px">{{member.nickname}}</div> -->
      </div>
      </drop-down>
      
    </div>
    <br />
    <div id="row">
      <div class="items" >
    <ul class="list-group" id="chatList" onscroll>
      <li class="list-group-item" v-for="message in messages" v-bind:key="message.id"  v-if="message.type=='TALK' || message.type=='JOIN'">
        <div v-if="message.type=='TALK'" class="d-flex flex-column">
          <inner v-if="message.nickname==nickname" style="display:block; width: fit-content; text-align:right; max-width:80%;" class="align-self-end">
            <div style="background:white; border-radius: 10px 10px 10px 10px; padding: 10px; text-align:left">{{ message.nickname }} - {{ message.message }}</div>
          </inner>
          <inner v-else style="display:block; width: fit-content; text-align:left; max-width:80%;">
            <div style="background:white; border-radius: 10px 10px 10px 10px; padding: 10px; text-align:left" >{{ message.nickname }} - {{ message.message }}
            </div>
            </inner>
          </div>
        <div v-if="message.type=='JOIN'" style="background:white; font-weight:600;  border-radius: 10px 10px 10px 10px; padding: 5px">
          <inner > [알림] {{message.message}}</inner>

          </div>
      </li>
    </ul>
      </div>
    </div>
    <!-- <div class="input-group">
      <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage" />
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
      </div>
    </div> -->

    <br /><br />
    <div>

      <b-input-group>
        <b-form-input style="border-radius:10px" type="text" v-model="message" v-on:keyup.enter="sendMessage()" />
        <b-input-group-append>
          <b-button text="Button" variant="primary" class="btnSearch mt-0" style="border-radius:10px; font-weight: bold"
            v-on:click="sendMessage()">
            전송
          </b-button>
        </b-input-group-append>
      </b-input-group>
      <b-button @click="modeZero()">뒤로</b-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import { DropDown } from '@/components';
const CHAT_SERVER_URL = process.env.VUE_APP_CHAT_SERVER_URL;
export default {
  components:{
    DropDown
  },
  data() {
    return {
      roomId: '',
      room: {},
      email: '',
      messages: [],
      message: null,
      reconnect: '',
      nickname: '',
      members: [],
      bottom_flag: true,
      pre_diffHeight : 0,
      notJoinMembers: 0,
      
    };
  },
  created() {
    this.reconnect = 0;
    this.roomId = localStorage.getItem('wschat.roomId');
    this.email = localStorage.getItem('email');
    this.nickname = localStorage.getItem('nickname');
    this.findRoom();
    this.loadMember();
    this.loadMessages();
    this.connect();
  },
  updated(){
    var objDiv = document.getElementById("chatList");
    if(this.bottom_flag){
      objDiv.scrollTop = objDiv.scrollHeight;
    }
  },
  methods: {
    modeZero: function() {
      this.$emit('modeZero')
    },
    loadMessages: function() {
      axios
        .get(`${CHAT_SERVER_URL}/chat/messages`, {
          params: {
            roomId: this.roomId,
          },
        })
        .then((res) => {
          this.messages = res.data.data;
        });

    },
    calNotJoin: function(){
      this.notJoinMembers = this.members.length;
      // console.log(this.members.length);
      for(const index in this.members){
        // console.log("isJoin" + this.members[index].isJoin)

        if(this.members[index].isJoin == '0'){
          this.notJoinMembers--;
        }
      }
    },
    findRoom: function() {
      axios
        .get(`${CHAT_SERVER_URL}/chat/search`, {
          params: {
            roomId: this.roomId,
          },
        })
        .then((res) => {
          this.room = res.data.data;
        });
    },
    sendMessage: function() {
      this.ws.send(
        '/pub/chat/message',
        {},
        JSON.stringify({
          type: 'TALK',
          roomId: this.roomId,
          email: this.email,
          nickname: this.nickname,
          message: this.message,
        })
      );

      // // console.log('###sendMsg end');
      this.message = '';
    },
    recvMessage: function(recv) {
      // // console.log('###recv msg : ' + recv.message);
      // this.messages.unshift({
      //   type: recv.type,
      //   email: recv.type == 'ENTER' ? '[알림]' : recv.email,
      //   message: recv.message,
      // });
      this.messages.push(recv)
    },
    // {"Access-Control-Allow-Credentials" : true}
    connect: function() {
      this.sock = new SockJS(`${CHAT_SERVER_URL}/ws-stomp`);
      this.ws = Stomp.over(this.sock);
      this.reconnect = 0;
      // pub/sub event
      this.ws.connect(
        {},
        (frame) => {
          // // console.log('###connect start');
          this.ws.subscribe(
            '/sub/chat/room/' + this.roomId,
            function(message) {
              var recv = JSON.parse(message.body);
              // // console.log('###receive start. recv : ' + recv);
              // // console.log('###frame : ' + frame);
              this.recvMessage(recv);
              // // console.log('###received.');
            }.bind(this)
          );
          // // console.log(
          //   '###send start. json msg : ' +
          //     JSON.stringify({ type: 'ENTER', roomId: this.roomId, email: this.email, nickname: this.nickname })
          // );
          this.ws.send(
            '/pub/chat/message',
            {},
            JSON.stringify({ type: 'ENTER', roomId: this.roomId, email: this.email, nickname: this.nickname, message: null })
          );
          //채팅창에 처음 접속했는지 확인
          // // console.log("########join check start")
          var memberFlag = 0;
          for(const index in this.members){
            // // console.log("###this.member[index].nickname : " + this.members[index].nickname)
            if(this.members[index].nickname == this.nickname){
              // // console.log("###isJoin : "+this.members[index].isJoin)
              if(this.members[index].isJoin == 0){
                memberFlag = 1;
                // // console.log("###join")
              }
              break;
            }
          }

          if(memberFlag == 1){
            this.ws.send(
              '/pub/chat/message',
              {},
              JSON.stringify({type: 'JOIN', roomId: this.roomId, email: this.email, nickname: this.nickname, message:null})
            )
          }
          this.joinRoom();
          // // console.log('###send end');
        },
        function(error) {
          if (this.reconnect++ <= 5) {
            setTimeout(function() {
              // // console.log('##connection reconnect' + error);
              this.sock = new SockJS('/ws-stomp');
              this.ws = Stomp.over(this.sock);
              this.connect();
            }, 10 * 1000);
          }
        }
      );
    },
    loadMember: function(){
      axios
        .get(`${CHAT_SERVER_URL}/chat/member`, 
        {
          params:{
            roomId: this.roomId,
          }
        })
        .then((res) =>{
          this.members = res.data.data;
          this.calNotJoin();
        })
        .catch((err) =>{
          console.log(err);
        })
    },
    joinRoom: function(){
      this.form = {
        roomId: this.roomId,
        nickname: this.nickname,
      };
      axios
        .put(`${CHAT_SERVER_URL}/chat/join`, this.form)
        .catch((err) =>{
          console.log(err);
        })
    },
  },
  mounted() {},
};
</script>
<style scoped>
body {
  margin:50px auto;
  width: 600px;
}
#row {
  /* white-space: nowrap; */
  /* 가로스크롤시 중요한 속성 */
  /* overflow-x: auto; */
  /* overflow-y: hidden; */
  /* padding: 10px 10px 5px; */
  background: #f7be98;
  /* width: 600px; */
}

/* #row .items {
  display: inline-block;
  margin-left: 10px;
  width: 1100px;
  max-width: 100%;
} */

/* #row .items:first-child {
  margin-left: 0;
} */

/* #row .items p {
  margin-bottom: 8px;
  text-indent: 7px;
} */

#row .items ul {
  border-radius: 3px;
  border: 1px solid #b5b5b5;
  height: 500px;
  overflow-y: scroll;
  padding: 3px 3px 8px;
  background: #f7be98;
  /* text-align: left; */
}

#row .items ul li {
  border: 0px;
  background: #f7be98;
  /* padding: 0px; */
}

 /* #row .items ul li a {} */

/*
#row .items ul li:first-child a {
  margin-top: 3px;
}

#row .items ul li.on a {
  border: 1px solid #c9cccf;
  border-radius: 3px;
  font-weight: bold;
  background-color: #efefef;
} */
</style>