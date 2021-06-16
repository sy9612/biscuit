<template>
 <div class="page-header clear-filter" filter-color="orange">
       <div
      class="page-header-image"
      style="background-image: url('img/login.jpg')"
    ></div>
  <center id="mypage" style="margin-top:100px;">
    <h4 id="title">마이페이지</h4>
    <div id="user-picture">
      <div class="mb-2">
        <b-avatar
          style="margin-left:5px;margin-top:25px"
          src="https://placekitten.com/300/300"
          size="10rem"
        ></b-avatar>
      </div>
    </div>
    <div id="user-profile">
      <p style="text-align:left;font-size:40px;margin-left:30px">
        {{ user.nickname }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        닉네임 : {{ user.nickname }}
      </p>
      <p style="text-align:left;font-size:14px;margin-left:30px">
        전화번호 : {{ user.phone }}
      </p>
    </div>
    <div id="tab">
        <button class="navButton" style="margin-top: 50px" @click.prevent="loadStudy()">나의 스터디</button><br/>
        <button class="navButton" style="margin-top: 200px" @click.prevent="loadSchedule()">나의 일정</button><br/>
        <button class="navButton" style="margin-top: 200px" @click.prevent="loadUpdate()">개인 정보 수정</button><br/>
        <button class="navButton" style="margin-top: 200px" @click.prevent="loadChatRoomList()">채팅</button>
    </div>

    <div id="mypage-contents">
      <!-- <UpdateUser id="update" style="margin-top:0px;margin-left:0px" /> -->
      <component :is="componentLoading()"></component>
    </div>
  </center>
  </div>
</template>

<script>
import MyStudy from '@/pages/User/MyPage/MyStudy';
import MySchedule from '@/pages/User/MyPage/MySchedule';
import UpdateUser from '@/pages/User/MyPage/UpdateUser';
import ChatRoomList from '@/pages/Chat/ChatRoomList';

export default {
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
    };
  },
  components: {
    UpdateUser,MySchedule,MyStudy,ChatRoomList
  },
  created() {
    this.user.region = localStorage.getItem('region');
    this.user.email = localStorage.getItem('email');
    this.user.nickname = localStorage.getItem('nickname');
    this.user.phone = localStorage.getItem('phone');
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
                  return 'ChatRoomList';
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
      loadChatRoomList(){
        this.active = 3;
      }
  },
};
</script>
<style>
#mypage {
  /* background-color: rgba(226, 202, 202, 0.356); */
  height: 1000px;
  width:1200px;
}
#title {
  text-align: left;
  margin-left: 19px;
}

#update {
  margin: 100px 0px 40px 200px;
}
#user-picture {
  float: left;
  width: 20%;
  height: 20%;
  /* background-color: dimgrey; */
  /* background-color: white;  */
}
#user-profile {
  margin-left: 10px;
  clear: right;
  float: right;
  width: 79%;
  height: 20%;
  /* background-color: rgb(116, 116, 189); */
  /* background-color: white;  */
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
  /* background-color: white;  */
}
#mypage-contents {
  margin-top: 10px;
  clear: right;
  float: right;
  width: 79%;
  height: 80%;
  /* background-color: darkslateblue; */
  /* background-color: white;  */
}
.navButton{
    border: 0;
    outline: 0;
    /* background-color: white; */
    margin-right: 100px;
}
</style>
