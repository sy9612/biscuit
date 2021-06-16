<template>
  <div>
    <div v-if="isLoading === false" class="loading">
      <b-spinner
        class="my-5"
        style="width: 10rem; height: 10rem; border: 1em solid currentColor; border-right-color: transparent;"
        label="Large Spinner"
      ></b-spinner>
      <h2>로딩 중 ...</h2>
    </div>
    <div v-else>
        <div class="row">
        <div class="col-md-6">
      <h2 style="margin-top:30px;" class="text-center">신청자 목록</h2>
      <ul class="list-group" v-if="applys.length != 0">
        <li
          class="list-group-item list-group-item-action"
          v-for="(item, index) in applys"
          v-bind:key="item.mId"
        >
          <div 
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              {{ item.nickname }}
            </span>
            <span>
              신청합니다
            </span>
            <div v-if="state == 3">
              <b-button @click="accept(item.mId, item.email, index)">
                승인
              </b-button>
              <b-button @click="deny(item.mId, item.email, index)">
                거절
              </b-button>
            </div>
          </div>
        </li>
      </ul>
      <p v-if="applys.length == 0">아직 스터디 신청자가 없습니다.</p>
      </div>
      <div class="col-md-6">
      <h2 style="margin-top:30px;" class="text-center">스터디원 목록</h2>
      <ul class="list-group">
        <li
          class="list-group-item list-group-item-action"
          v-for="(item, index) in members"
          v-bind:key="item.mId"
        >
          <div 
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <img v-if="item.permission == 3" src="img/crown.png" alt="group master" width="7%" height="7%"/>
              {{ item.nickname }} 
              <b-button v-if="item.nickname == loginStatus.nickname && item.permission != 3" style="margin-left : 220px" @click="deleteUser">
                스터디 탈퇴
              </b-button>             
            </span>            
            <div v-if="item.permission != 3 && state == 3"> 
              <b-button @click="[updateState(item.mId, 3), updateState(mId, 1)]">
                그룹장 위임
              </b-button>
              <b-button @click="kick(item.mId, index)">
                추방
              </b-button>
            </div>
          </div>
        </li>
      </ul>
      <form action="" method="post" @submit.prevent="deleteGroup(gId)">
        <b-button type="submit" pill variant="danger">스터디 삭제하기</b-button>
      </form>
      </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

import { mapState } from 'vuex';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      isLoading: false,
      applys: Object,
      members: Object,
       groupPagePath : "/grouppage?gId=" + this.gId,
       mId: Number,
    };
  },
  created() {
    this.applyList();
    this.memberList();
  },
  
  computed: {
    ...mapState(['loginStatus']),
  },
  props: {
    gId: String,
    state: Number,
    groupName: String,
    max: Number,
  },
  methods: {
    deleteGroup: function(gId) {
      axios.delete(`${SERVER_URL}/group/delete/`, {headers: {gId: gId}})
        .then(res => {
          alert('스터디가 삭제되었습니다.')
          //임시방편
          this.$router.push({ path: './grouplist' });
          // window.location.reload();
          // // 모달창 닫기
          // let targetModal = document.querySelector('#group-'+this.idx)
          // // console.log(targetModal)
          // // 리스트에서 해당 스터디
        })
        .catch(err => {
          console.log(err)
        })
    },
    memberList: function() {
      axios
        .get(`${SERVER_URL}/group/member/list`, {
          params: {
            gId: this.gId,
          },
        })
        .then((res) => {
            if(res.data.success == 'success'){

            for(const item in res.data.list){
                if(res.data.list[item].nickname == this.loginStatus.nickname){
                    this.mId = res.data.list[item].mId;
                }
            }
          this.members = res.data.list;
            }
        });
      this.isLoading = true;
    },
    updateState: function(mid, state) {
      axios
        .put(`${SERVER_URL}/group/member/permission`, {
          mId: mid,
          permission : state
        })
        .then((res) => {
          if (res.data.success == 'success') {
            //  this.$router.go(this.$router.currentRoute);
            this.$emit('changemember',5);
          }
        });
    },
    kick: function(mId, index) {
      axios
        .put(`${SERVER_URL}/group/member/kick`, {
          mId: mId,
        })
        .then((res) => {
          if (res.data.success == 'success') {
              this.members.splice(index,1);
          }
        });
    },
    applyList: function() {
      axios
        .get(`${SERVER_URL}/group/member/apply/group/list`, {
          params: {
            gId: this.gId,
          },
        })
        .then((res) => {
            
          if (res.data.success == 'success') {
          this.applys = res.data.list;
          }
        });
      this.isLoading = true;
    },
    accept: function(mid, email,index) {
      if(this.members.length >= this.max){
        alert('모집 제한 인원을 초과했습니다.')
      }
      else{
      axios
        .put(`${SERVER_URL}/group/member/accept`, {
          mId: mid,
        })
        .then((res) => {
          if (res.data.success == 'success') {
            this.members.push(this.applys[index]);
            this.applys.splice(index,1);
          }
        });
      

      axios
        .post(`${SERVER_URL}/notification/create`, {
          receiveEmail: email,
          sendEmail: this.loginStatus.email,
          isRead: 0,
          type: 'accept',
          contentId: this.gId,
          message: '[' + this.groupName + '] 스터디 가입이 승인되었습니다!',
          notiUrl: '/GroupPage?gId=' + this.gId,
        })
        .then((res) => {
          if (res.data.success) {
            // alert('등록되었습니다.');
            this.$emit('changemember',3);
          }
        })
        .catch((err) => {
          console.log(err);
        });
      }
    },
    deny: function(mid, email, index) {
      axios
        .put(`${SERVER_URL}/group/member/deny`, {
          mId: mid,
        })
        .then((res) => {
          if (res.data.success == 'success') {
              this.applys.splice(index,1); }
        });

      axios
        .post(`${SERVER_URL}/notification/create`, {
          receiveEmail: email,
          sendEmail: this.loginStatus.email,
          isRead: 0,
          type: 'deny',
          contentId: this.gId,
          message: '[' + this.groupName + '] 스터디 가입이 거절되었습니다',
          //notiUrl: '/GroupPage?gId=' + this.gId,
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteUser: function(){
      if(confirm("해당 스터디에서 탈퇴하시겠습니까?")== true){
      axios.delete(`${SERVER_URL}/group/member/cancel`,
        {data: {gId: this.gId, nickname: this.loginStatus.nickname}})
         .then(res => {
           this.$router.push('/grouplist');
         })
         .catch(err => {
           console.log(err)
         })
      }
    },
  },
};
</script>
