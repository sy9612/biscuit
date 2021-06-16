<template>
<div id="group-page">
    <h4 id="title">스터디 페이지</h4>

    <div id="group-picture">
      <div class="mb-2">
        <b-avatar
          style="margin-left:5px;margin-top:25px"
          src="https://placekitten.com/300/300"
          size="10rem"
        ></b-avatar>
      </div>
    </div>
    <div id="group-profile">
      <p style="text-align:left;font-size:40px;margin-left:30px">
        그룹 이름 : {{ group.groupName }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        그룹 설명 : {{ group.groupDesc }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        카테고리 : {{ group.category }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        지역 : {{ group.region }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        state : {{ state }}
      </p>
    </div>
    <div id="tab">
        <button class="navButton" style="margin-top: 50px" @click.prevent="">스터디 일정</button><br/>
        <button class="navButton" style="margin-top: 50px" @click.prevent="">스터디 공지사항</button><br/>
        <button class="navButton" style="margin-top: 50px" @click.prevent="">스터디 참여인원</button><br/>
        <div v-if="state == 3">
        <button  class="navButton" style="margin-top: 50px" @click.prevent="loadApplyList()">신청 현황
           <p v-if="applyCount != 0">{{applyCount}}</p>
           </button><br/>
           <button  class="navButton" style="margin-top: 50px" @click.prevent=""> 그룹 관리
           </button><br/>
        </div>
        <button class="navButton" style="margin-top: 50px" @click.prevent="joinMeeting()">화상 스터디 참여</button><br/>
    </div>

    <div id="mypage-contents">
      <!-- <UpdateUser id="update" style="margin-top:0px;margin-left:0px" /> -->
      <component 
      :is="componentLoading()"
      :gId="gId"
      ></component>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import ApplyList from './ApplyList';

export default {
  data() {
    return {
      group: Object,
      gId: this.$route.query.gId,
      active : 0,
      state : Number,
      applyCount : Number,
    };
  },
  components:{
    ApplyList,

  },
    computed: {
    ...mapState(['loginStatus']),
  },
  created() {
    this.getGroup();
    this.getState();
    this.getApplyCount();
  },
  methods: {
    getState: function(){
      axios
        .get(`${SERVER_URL}/group/member/apply/state`,
        {
          params:{
            gId : this.gId,
            nickname : this.loginStatus.nickname
          }
        })
        .then((res) => {
          this.state = res.data.state;
        })
          .catch((err) => {
          console.log(err);
        });
    },
    getGroup: function() {
      axios
        .get(`${SERVER_URL}/group/list/`, {
          params: {
            word: this.gId,
            type: 'gId',
          },
        })
        .then((res) => {
          this.group = res.data.list[0];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getApplyCount: function() {
      axios
        .get(`${SERVER_URL}/group/member/apply/group/count`, {
          params: {
            gId: this.gId,
          },
        })
        .then((res) => {
          this.applyCount = res.data.applyCount;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    componentLoading(){
          switch(this.active){
              case 0 :
                  return '';
              case 1 :
                  return 'ApplyList';
              case 2 :
                  return 'UpdateUser';    
          }
      },
      loadApplyList(){
        this.active = 1;
      },
      joinMeeting(){
        var VUE_RTC_LOCAL_SERVER_URL = `http://localhost:9001/demos/dashboard/`;
        // var VUE_RTC_SERVER_URL = `http://i4a407.p.ssafy.io:9001/demos/dashboard/`;
        // var nickname = localStorage.getItem('nickname');
        window.open(`${VUE_RTC_LOCAL_SERVER_URL}?gId=${this.gId}&nickname=${this.loginStatus.nickname}`, "_blank");
      }
  },
};
</script>
<style>
#group-page {
  background-color: rgba(226, 202, 202, 0.356);
  height: 1000px;
}
#title {
  text-align: left;
  margin-left: 19px;
}

#update {
  margin: 100px 0px 40px 200px;
}
#group-picture {
  float: left;
  width: 20%;
  height: 20%;
  /* background-color: dimgrey; */
  background-color: white; 
}
#group-profile {
  margin-left: 10px;
  clear: right;
  float: right;
  width: 79%;
  height: 20%;
  /* background-color: rgb(116, 116, 189); */
  background-color: white; 
}
#profile-content {
  margin-top: 50px;
  margin-bottom: 20px;
  font-size: 100px;
}
#tab {
  margin-top: 10px;
  float: left;
  width: 20%;
  height: 80%;
  /* background-color: darkgreen; */
  background-color: white; 
}
#mypage-contents {
  margin-top: 10px;
  clear: right;
  float: right;
  width: 79%;
  height: 80%;
  /* background-color: darkslateblue; */
  background-color: white; 
}
.navButton{
    border: 0;
    outline: 0;
    background-color: white;
    margin-right: 100px;
}
</style>
