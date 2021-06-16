<template>
  <div>
    <div style="margin-bottom: 30px">
      <!-- <div>
        <b-button
          v-if="mode == 0"
          v-on:click="switchAddMode()"
          class="btn btn-primary"
          type="button"
        >
          방 개설
        </b-button>
      </div> -->
      <div class="d-flex">

        <div v-for="item in addList" v-bind:key="item">
          <b-button v-on:click="deleteMember(item)" class="btn btn-round py-1 px-2">{{
            item.nickname
          }}</b-button>
        </div>
      </div>
      <b-input-group>
        <b-form-input
          style="border-radius:10px"
          type="text"
          v-model="roomName"
          v-on:keyup.enter="createRoom()"
          placeholder="채팅방 이름을 입력해주세요"
        />
        <b-input-group-append>
          <b-button
            text="Button"
            variant="primary"
            class="btnSearch mt-0"
            style="border-radius:10px; font-weight: bold"
            v-on:click="createRoom()"
          >
            생성
          </b-button>
        </b-input-group-append>
      </b-input-group>
    </div>
    <b-input-group>
      <b-form-input
        style="border-radius:10px"
        type="text"
        v-model="searchName"
        placeholder="이름을 검색해주세요"
      />
    </b-input-group>
    <ul class="list-group">
      <!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
      <li
        class="list-group-item list-group-item-action"
        v-for="item in friends"
        v-if="item.nickname.includes(searchName)"
        v-bind:key="item.nickname"
      >
        <div class="d-flex justify-content-between align-items-center">
          <span>
            {{ item.nickname }}
          </span>
          <input
            v-if="item.nickname != user.nickname"
            type="checkbox"
            v-model="addList"
            :value="item"
          />
          <!-- <label for="mike">Mike</label> -->
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
import axios from 'axios';

import { mapState } from 'vuex';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const CHAT_SERVER_URL = process.env.VUE_APP_CHAT_SERVER_URL;

export default {
  data() {
    return {
      friends: [],
      user: {
        email: '',
        nickname: '',
        picture: '',
        region: '',
        phone: '',
      },
      mode: 0,
      addList: [],
      searchName: '',
      roomName: '',
    };
  },
  created() {
    this.user.region = localStorage.getItem('region');
    this.user.email = localStorage.getItem('email');
    this.user.nickname = localStorage.getItem('nickname');
    this.user.phone = localStorage.getItem('phone');
    this.findAllFriends();
  },

  computed: {
    ...mapState(['loginStatus']),
  },
  methods: {
    findAllFriends: function() {
      axios
        .get(`${SERVER_URL}/group/member/friends`, {
          params: {
            email: this.user.email,
          },
        })
        .then((res) => {
          this.friends = res.data.list;
        });
    },
    // switchAddMode: function() {
    //   this.mode = 1;
    // },
    deleteMember: function(item) {
      this.addList.splice(this.addList.indexOf(item), 1);
    },
    inviteMember: function(roomId) {
      if (this.addList == null) {
        alert('초대할 인원을 선택해주세요');
        return;
      } else {
        this.form = {
          email: this.user.email,
          nickname: this.user.nickname,
          roomId: roomId,
        };
        axios
          .post(`${CHAT_SERVER_URL}/chat/member`, this.form)
          .then((res) => {
            if (res.data.success == 'fail') {
              alert('채팅 멤버를 불러오는 데 실패했습니다.');
              return 0;
            }
          })
          .catch((res) => {
            alert(res + '채팅 멤버를 불러오는 데 에러가 발생했습니다.');
            return -1;
          });
        for (const item in this.addList) {
          this.form = {
            email: this.addList[item].email,
            nickname: this.addList[item].nickname,
            roomId: roomId,
          };
          axios
            .post(`${CHAT_SERVER_URL}/chat/member`, this.form)
            .then((res) => {
              if (res.data.success == 'fail') {
                alert('채팅 멤버를 불러오는 데 실패했습니다.');
                return 0;
              } 
            })
            .catch((res) => {
              alert(res + '채팅 멤버를 불러오는 데 에러가 발생했습니다.');
              return -1;
            });
        }
        return 1;
      }
    },
    createRoom: function() {
      this.form = {
        roomId: null,
        roomName: this.roomName,
      };
      if ('' === this.roomName) {
        alert('방 제목을 입력해 주십시요.');
        return;
      } else {
        axios
          .post(`${CHAT_SERVER_URL}/chat/room`, this.form)
          .then((res) => {
            const ret = this.inviteMember(res.data.data.roomId);
            if (ret == 1) {
              alert(res.data.data.roomName + '방 개설에 성공하였습니다.');
              // this.$emit('addRoom', res.data.data.roomId)
              localStorage.setItem('wschat.roomId', res.data.data.roomId);
              this.$emit('modeOne')
              this.room_name = '';
              // this.findAllRoom();
            } else {
              // console.log('실패');
              return;
            }
          })
          .catch((res) => {
            alert(res + '채팅방 개설에 실패하였습니다.');
          });
      }
    },
  },
};
</script>
