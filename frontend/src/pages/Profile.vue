<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/bg5.jpg')"> </parallax>
      <div class="container">
        <div class="photo-container">
          <img :src="loginStatus.picture" alt="" />
        </div>
        <h3 class="title">{{loginStatus.nickname}}</h3>
        <p class="category">{{loginStatus.phone}}</p>
        <div class="content">
           <div class="social-description">
            <h2>{{this.boardCnt}}</h2>
            <p>내 게시글</p>
          </div>
          
          <div class="social-description">
            <h2>{{this.replyCnt}}</h2>
            <p>내 댓글</p>
          </div>
          <div class="social-description">
            <h2>{{this.loginStatus.myStudyNum}}</h2>
            <p>가입한 스터디</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="button-container">
          <a v-on:click="loadStudy()" v-on:studyNum='studyNum()' class="btn btn-primary btn-round btn-lg" >나의 스터디</a>
          <a v-on:click="loadSchedule()" class="btn btn-primary btn-round btn-lg">나의 일정</a>
          <a v-on:click="loadUpdate()" class="btn btn-primary btn-round btn-lg" >개인 정보 수정</a>
          <a v-on:click="loadChatMain()" class="btn btn-primary btn-round btn-lg" >채팅</a>
          
        </div>
        <component :is="componentLoading()"></component>
  </div>
    </div>
    </div>
  
</template>
<script>
import { Tabs, TabPane,Button, FormGroupInput } from '@/components';
import MyStudy from './User/MyPage/MyStudy';
import MySchedule from './User/MyPage/MySchedule';
import UpdateUser from './User/MyPage/UpdateUser';
import ChatMain from './Chat/ChatMain';
 import {mapState} from 'vuex'
 import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'profile',
  bodyClass: 'profile-page',
  data() {
    return {
      user: {
        email: '',
        nickname: '',
        picture: '',
        region: '',
        phone: '',
      },
      active:0,
      joinedStudyNum:0,
      list:[],
      boardCnt:0,
      replyCnt:0,
    };
  },
  created() {
    this.user.region = localStorage.getItem('region');
    this.user.email = localStorage.getItem('email');
    this.user.nickname = localStorage.getItem('nickname');
    this.user.phone = localStorage.getItem('phone');

    // this.form = {
    //     type: "name",
    //     word: this.user.nickname,
    //     currentPage: '',
    //     category: '',
    //   };

    //   axios
    //     .get(`${SERVER_URL}/board/read`, { params: this.form })
    //     .then((res) => {
    //       // console.log(res)
    //       this.list = res.data.list.sort((a, b) => {
    //         return b.bid - a.bid;
    //       });
    //       // console.log("하위" + this.list.length);
    //     });
    this.form = { email: this.user.email };
    axios
        .get(`${SERVER_URL}/board/countboard`, {
          params: this.form,
        })
        .then((res) => {
          if (res.data.success === 'success'){
            this.boardCnt = res.data.count;
          }else{
            // console.log("에러뜸");
          }
        })
        .catch(function(error) {
          console.log(error);
        });
      
    axios
        .get(`${SERVER_URL}/reply/countreply`, {
          params: this.form,
        })
        .then((res) => {
          if (res.data.success === 'success'){
            this.replyCnt = res.data.count;
          }else{
            // console.log("에러뜸");
          }
        })
        .catch(function(error) {
          console.log(error);
        });


  },
  computed: {
      ...mapState([
        'loginStatus','myStudyNum'
      ]),
    },
  components: {
    Tabs,
    TabPane,MySchedule,UpdateUser,MyStudy,ChatMain,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  methods: {
    componentLoading(){
          switch(this.active){
              case 0 :
                  return 'MyStudy';
              case 1 :
                  return 'MySchedule';
              case 2 :
                  return 'UpdateUser';    
              case 3 : 
                  return 'ChatMain';
          }
      },

      loadStudy(){
           this.active = 0;
      },
      loadSchedule(){
           this.active = 1;
      },
      loadUpdate(){
          this.active = 2;
      },      
      loadChatMain(){
        this.active = 3;
      },
      studyNum(data){
        // console.log("데이터들들" + data);
        this.joinedStudyNum = data;
      }
  }
};
</script>
<style></style>
