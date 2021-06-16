<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" :style="`background-image:url('${group.img}')`"> </parallax>
      <div class="container">

        <div class="row">
          <div class="col-8"></div>
          <div class="col-2" v-if="state == 3" style="margin-bottom:-10px">
            <div style="cursor:pointer;position:relative;top:70px;left: 50px">
              <i
                v-on:click="updateGroup(group.gId)"
                style="width:200%; height:300%;margin-left:-120px;margin-bottom:-100px;padding-left:-100px"
                class="now-ui-icons ui-1_settings-gear-63"
              >
                스터디 수정
              </i>
            </div>
          </div>
        </div>
        <h3 style="margin-top:50px">{{ group.groupName }}</h3>
        <p class="category" style="font:bold">{{ group.category }} 스터디</p>
        <!-- <div v-html="group.groupDesc.replace(/(?:\r\n|\r|\n)/g, '<br />')" class="description"></div> -->

        <br />
        <div>
          <p style="font-size: 14px;margin-top:10px">스터디 시작일 : {{ group.sdate }}</p>
          <p style="font-size: 14px;">스터디 인원 제한 : {{ group.max }}</p>
          <p style="font-size: 14px;">지역 : {{ group.region }}</p>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="button-container">
          <!-- button -->
          <!-- <a v-on:click="loadHome()" class="btn btn-primary btn-round btn-lg">홈</a> -->
          <a v-on:click="loadSchedule()" class="btn btn-primary btn-round btn-lg">일정</a>
          <a v-on:click="loadSBoardList()" class="btn btn-primary btn-round btn-lg">게시판</a>
          <a v-on:click="joinMeeting()" class="btn btn-primary btn-round btn-lg">화상 채팅</a>
          <span>
            <a
              type="primary"
              v-on:click="loadMemberList()"
              class="btn btn-primary btn-round btn-lg"
            >
              <span>
                스터디 관리
              </span>
            </a>

            <span 
              v-if="applyCount != 0 && state == 3"
              class="badge badge-warning badge-pill"
              style="position: relative; right:45px"
              >{{ applyCount }}</span
            >
            <!-- <span v-if="applyCount != 0" class="badge badge-warning badge-pill" style="position: relative; right:45px; bottom:20px">1</span> -->
          </span>
        </div>
        <component :is="componentLoading()" :gId="gId" :state="state" :groupName="group.groupName" :max="group.max" @changemember="changeAct"></component>
        <group-board-list v-if="active==4" :gId ="gId"></group-board-list>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import GroupHome from './GroupHome.vue';
// import ApplyList from './ApplyList.vue';
import GroupSchedule from './GroupSchedule.vue';
import MemberList from './MemberList.vue';
import GroupBoardList from './GroupBoard/BoardList.vue';

import { mapState } from 'vuex';
export default {
  name: 'group-page',
  bodyClass: 'profile-page',
  data() {
    return {
      group: Object,
      gId: this.$route.query.gId,
      state: Number,
      applyCount: Number,
      active: 1,
    };
  },
  created() {
    this.getState();
    this.getGroup();
    this.getApplyCount();
  },
  computed: {
    ...mapState(['loginStatus']),
  },
  components: {
    GroupHome,
    // ApplyList,
    GroupSchedule,
    MemberList,
    GroupBoardList,
  },
  methods: {
    componentLoading() {
      switch (this.active) {
        case 1:
            return 'GroupSchedule';
        case 2:
          return 'ApplyList';
        case 3:
          return 'MemberList';
      }
    },

    loadSchedule() {
      this.active = 1;
    },
    loadApplyList() {
       this.active = 2;
    },
    loadMemberList() {
      this.active = 3;
    },
    loadSBoardList(){
      // getBoardList();
      this.active = 4;
    },
    getState: function() {
      axios
        .get(`${SERVER_URL}/group/member/apply/state`, {
          params: {
            gId: this.gId,
            nickname: this.loginStatus.nickname,
          },
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
          const sdate = res.data.list[0].sdate
          res.data.list[0].sdate = sdate.slice(0, 4) + '년 ' + sdate.slice(5, 7) + '월 ' + sdate.slice(8, 10) + '일'
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
    joinMeeting() {
      //var VUE_RTC_LOCAL_SERVER_URL = `http://localhost:9001/demos/dashboard/`;
      var VUE_RTC_SERVER_URL = `https://i4a407.p.ssafy.io:9001/demos/dashboard/`;
      var nickname = localStorage.getItem('nickname');
      //새창에 띄우는 코드
      window.open(
        `${VUE_RTC_SERVER_URL}?gId=${this.gId}&nickname=${this.loginStatus.nickname}`,
        'win0','status=no,toolbar=no' //,scrollbars=no'
      );
      // window.open(
      //   `${VUE_RTC_SERVER_URL}?gId=${this.gId}&nickname=${this.loginStatus.nickname}`,
      //   '_blank'
      // );
    },
    updateGroup: function(gId) {
        this.$router.push({path: './GroupUpdate', query: { gId: gId}})
    },
    changeAct(num){
      if(num==3){
        this.loadMemberList();
        this.getApplyCount();
        this.active = 3;
        this.componentLoading();

      }else if(num == 5){
        this.getState();
        this.loadMemberList();
        this.getApplyCount();
        this.active = 3;
        this.componentLoading();
      }
    }
  },
};
</script>
<style></style>
