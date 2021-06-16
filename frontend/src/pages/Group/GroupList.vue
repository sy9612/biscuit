<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/header.jpg')">
      </parallax>
      <div class="content-center brand">
        <img class="n-logo" src="img/bisWhite.png" alt="" />
        <h2 class="h2-seo">스터디 그룹 페이지 입니다</h2>
        <h3>참여 가능한 {{groups.length}}개의 스터디가 검색되었습니다</h3>
        <div class="searchWrap">
          <b-input-group>
            <template #prepend>
              <b-input-group>
                <b-select name="type" style="border-radius:30px" v-model="params.type">
                  <b-select-option value="groupName">스터디 이름</b-select-option>
                  <b-select-option value="groupDesc">스터디 설명</b-select-option>
                  <b-select-option value="category">카테고리</b-select-option>
                  <b-select-option value="region">지역</b-select-option>
                </b-select>
              </b-input-group>
            </template>
            <b-form-input type="text" style="border-radius:10px; color:white; background-color:#11111155"
              v-model="params.word" @keypress.enter="searchGroup"></b-form-input>
            <b-input-group-append>
              <b-button class="mt-0" style="border-radius:10px" text="Button" variant="primary" @click="searchGroup">
                검색<i class="now-ui-icons ui-1_zoom-bold" style="margin-left:10px"></i></b-button>
            </b-input-group-append>
          </b-input-group>
        </div>
        <div><i class="now-ui-icons arrows-1_minimal-down"></i></div>
      </div>
    </div>


    <div v-if="loginStatus.nickname">
      <div class="col-md-8">
      </div>
      <div class="container">
        <div class="row">
          <div role="alert" class="alert alert-info col-md-12 col-sm-12"
            style="border-radius:30px; height:80px; margin-top:20px;">
            <div v-if="existMyGroups" style="margin-bottom:20px">
              <div class="alert-icon" style="font-size:180%; margin-top:10px; margin-left:20px"><i
                  class="now-ui-icons travel_info" style="margin-right:10px"></i>{{loginStatus.nickname}}님의 스터디 목록입니다
              </div>
              <div class="d-flex justify-content-end">
                <b-button v-show="loginStatus.email" class="btn d-flex "
                  style="background-color:white; padding: 10px 10px 10px 10px; font-weight:bold; font-size:120%; color:#2ca8ff; border-radius:20px"
                  @click="goCreate"><i class="now-ui-icons ui-1_simple-add"></i><span style="">스터디 만들기</span></b-button>
              </div>
            </div>
            <div v-if="!existMyGroups" style="margin-bottom:20px">
              <div class="alert-icon"><i class="now-ui-icons travel_info"></i>아직까지 가입한 스터디가 없습니다</div>
              <div class="d-flex justify-content-end">
                <b-button v-show="loginStatus.email" class="d-flex"
                  style="background-color:white; font-weight:bold; color:#f96332; font-size:120%; border-radius:20px"
                  @click="goCreate"><i class="now-ui-icons ui-1_simple-add"></i>스터디 만들기</b-button>
              </div>
            </div>
          </div>
          <!-- <div v-if="!existMyGroups" role="alert" class="alert alert-danger" style="border-radius:10px height:100px">
          </div> -->
        </div>
        <div class="row">
          <b-col class="col-12 col-sm-12 col-md-6 col-lg-4" v-for="(group, idx) in myGroups.slice().reverse()" :key="idx" :group="group">
            <b-card @click="goGroupPage(group)" class="my-3"
              style="min-height:400px; max-height:400px; min-width:350px; overflow:hidden">
              <div style="max-height:200px; min-height:200px; width:100%; overflow:hidden; ">
              <b-card-img :src="group.img" alt="Image" top></b-card-img>
            </div>
            <b-card-text style="padding:10px; overflow:hidden">

              <div>
                <div
                  style="font-size:23px; font-weight:bold; color:#222; margin-right:5px; margin-bottom:10px; white-space: nowrap; overflow: hidden; text-overflow:ellipsis;">
                  {{group.groupName}}</div>
              </div>
              <h5 style="font-size:14px;  color:#000000; margin-right:5px"><i style="margin-right:5px"
                  class="now-ui-icons business_badge"></i> {{group.nickname}} <img v-if="group.nickname == loginStatus.nickname" src="./crown-solid.svg" style="width:15px;" alt=""></h5>
              <span class="badge badge-info"
                style="font-size:12px; font-weight:bold; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons users_single-02"></i> {{ group.max }}</span>
              <!-- <b>온라인 여부: {{ group.onoff }}</b><br> -->
              <!-- <b>모집기간: {{ group.edate }}</b><br> group.nickname-->
              <span class="badge badge-success" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_pin"></i> {{ group.region }}</span>
              <span class="badge badge-primary" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_bookmark"></i> {{ group.category }}</span>
              <span class="badge badge-danger" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons ui-1_calendar-60" id="dday"></i> ~{{ group.edate }}</span>
              <!-- <b>모임 주기: {{ group.cycle }}</b> -->
            </b-card-text>
            <template #footer>
              <!-- <small class="text-muted">Last updated 3 mins ago</small> -->
            </template>

            </b-card>
          </b-col>

        </div>
      </div>
    </div>


    <hr>
    <div class="container">

      <div class="row">

        <div role="alert" class="alert alert-primary col-md-12 col-sm-12"
          style="border-radius:30px; height:80px; margin-top:20px;">
          <div style="margin-bottom:20px">
            <div class="alert-icon" style="font-size:180%; margin-top:10px; margin-left:20px"><i
                style="margin-right:10px" class="now-ui-icons loader_refresh"></i>새로운 스터디를 찾아보세요 </div>
            <div class="d-flex justify-content-end">
              <b-button v-if="loginStatus.email" class="d-flex align-items-center"
                style="background-color:white; font-size:120%; font-weight:bold; color:#f96332; border-radius:20px"
                @click="goCreate"><i class="now-ui-icons ui-1_simple-add"></i>스터디 만들기</b-button>
              <!-- <b-button v-show="loginStatus.email" class="d-flex align-items-center" style="background-color:white; font-size:120%; font-weight:bold; color:#f96332; border-radius:20px" @click="goCreate"><i class="now-ui-icons ui-1_simple-add" ></i>스터디 만들기</b-button> -->
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="container">
      <div class="row">

        <div class="col-12 col-sm-6 col-md-4 col-lg-4" v-for="(group, idx) in groups.slice().reverse()" :key="idx"
          :group="group">
          <!-- @click="goDetail(group)" -->
          <!-- v-b-modal.group-13 -->

          <b-card data-aos="flip-left"
            @click="$bvModal.show(`group-${idx}`), getMemberCount(group.gId), getPermission(group.gId), getPicture(group.email)" class="my-3"
            style="min-height:400px; min-width:300px; max-height:400px;">
            <div style="max-height:200px; min-height:200px; width:100%; overflow:hidden; ">
              <b-card-img :src="group.img" alt="Image" top></b-card-img>
            </div>
            <b-card-text style="padding:10px; overflow:hidden">

              <div>
                <div
                  style="font-size:23px; font-weight:bold; color:#222; margin-right:5px; margin-bottom:10px; white-space: nowrap; overflow: hidden; text-overflow:ellipsis;">
                  {{group.groupName}}</div>
              </div>
              <h5 style="font-size:14px;  color:#000000; margin-right:5px"><i style="margin-right:5px"
                  class="now-ui-icons business_badge"></i> {{group.nickname}} <img v-if="group.nickname == loginStatus.nickname" src="./crown-solid.svg" style="width:15px;" alt=""></h5>
              <span class="badge badge-info"
                style="font-size:12px; font-weight:bold; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons users_single-02"></i> {{ group.max }}</span>
              <!-- <b>온라인 여부: {{ group.onoff }}</b><br> -->
              <!-- <b>모집기간: {{ group.edate }}</b><br> group.nickname-->
              <span class="badge badge-success" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_pin"></i> {{ group.region }}</span>
              <span class="badge badge-primary" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_bookmark"></i> {{ group.category }}</span>
              <span class="badge badge-danger" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons ui-1_calendar-60" id="dday"></i> ~{{ group.edate }}</span>
              <!-- <b>모임 주기: {{ group.cycle }}</b> -->
            </b-card-text>
            <template #footer>
              <!-- <small class="text-muted">Last updated 3 mins ago</small> -->
            </template>
          </b-card>
          <b-modal :id="'group-'+idx" hide-footer size="lg" :title="''+group.groupName">
            <!-- hide-header -->
            <div>
              <div class="bannerImg jumbotron-image clear-filter" filter-color="orange" style="position:relative">
                <div class="img">
                  <img :src="group.img" alt="" class="filter1 clear-filter" filter-color="orange"
                    style="width:100%; overflow:hidden">
                  <div class="content2">
                    <h5>{{group.groupName}}</h5>
                  </div>
                </div>
              </div>
              <div class="content-center brand">
                <div class="jumbotron text-white jumbotron-image shadow" style="padding-top:20px">
                  <div id="badge" style="margin-bottom:20px">
                    <span class="badge badge-info"
                      style="font-size:12px; font-weight:bold; color:#FFFFFF; margin-right:5px"><i
                        class="now-ui-icons users_single-02"></i> {{ group.max }}</span>
                    <span class="badge badge-success" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                        class="now-ui-icons location_pin"></i> {{ group.region }}</span>
                    <span class="badge badge-primary" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                        class="now-ui-icons location_bookmark"></i> {{ group.category }}</span>
                    <span class="badge badge-danger" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                        class="now-ui-icons ui-1_calendar-60" id="dday"></i> {{ group.edate }}</span>
                  </div>
                  <div class="row">

                    <div class="col-md-4">
                      <div class="photo-container rounded-circle"
                        style="height:200px; width:200px; overflow:hidden; margin:auto">
                        <img class="" :src="picture" alt="" id="pic" style="min-height:200px;">
                      </div>
                    </div>
                    <div class="col-md-8">


                      <div class="container">



                        <h4>스터디장: {{group.nickname}}</h4>
                        <h5>모집인원: {{memberCount}} / {{ group.max }}명</h5>
                        <p>온라인 여부: {{ group.onoff }}</p>
                        <p>모집기간: {{ group.edate }}</p>
                        <p>지역: {{ group.region }}</p>

                        <div style="float:right">
                          <b-button id="none" @click="applyGroup(group.gId,group.email,group.groupName)" pill
                            variant="primary">스터디 참가하기</b-button>
                          <b-button id="wait" @click="removeApply(group.gId)" pill variant="secondary">스터디 참가 신청 취소하기
                          </b-button>
                          <b-button id="mine" pill variant="primary">당신의 스터디 그룹입니다</b-button>
                          <b-button id="ban" pill variant="danger">당신은 이 스터디에서 추방되었습니다</b-button>
                          <b-button v-if="!loginStatus.token" @click="goLogIn()" pill variant="secondary">스터디에 참여하려면
                            로그인 해주세요</b-button>
                        </div>

                      </div>
                    </div>

                  </div>
                  <hr style="height:50px">
                  <h5 style="font-weight:bold; margin-left:10px">스터디 설명</h5>
                  <div v-html="group.groupDesc.replace(/(?:\r\n|\r|\n)/g, '<br />')"
                    style="background-color:#efefef; padding: 20px 20px 20px 20px; color:black; border-radius:10px">
                  </div>
                </div>
              </div>

              <hr>

              <div v-if="group.nickname == loginStatus.nickname" class="d-flex justify-content-end">
                <form action="" method="post" @submit.prevent="updateGroup(group.gId)">
                  <b-button type="submit" pill variant="warning">스터디 정보 수정하기</b-button>
                </form>
                <form action="" method="post" @submit.prevent="deleteGroup(group.gId)">
                  <b-button type="submit" pill variant="danger">스터디 삭제하기</b-button>
                </form>
              </div>
            </div>
          </b-modal>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import axios from 'axios'
  
  const SERVER_URL = process.env.VUE_APP_SERVER_URL


  import VueMoment from 'vue-moment'
 


  export default {
    name: "Group",
    components: {
    },
    data() {
      return {
        picture: '',
        params: {
          type: 'groupName',
          word: '',
        },
        groups: [],
        myGroups: [],
        permission: '',
        existMyGroups: false,
        memberCount: Number,
      }
    },
    created() {
      this.myGroupList();
      this.groupList();

    },
    computed: {
      ...mapState([
        'loginStatus',
      ]),
    },
    methods: {
      searchGroup: function() {
        axios.get(`${SERVER_URL}/group/list/`, {params: this.params})
          .then(res => {
                        for (let i = 0; i < res.data.list.length; i++) {
              res.data.list[i].edate = res.data.list[i].edate.split(' ')[0];
            }
            this.groups = res.data.list;
         })
          .catch(err => {
            console.log(err)
          })
      },
      getMemberCount: function(gId) {
        // console.log("getMemberCount. gId : " + gId)
      axios
        .get(`${SERVER_URL}/group/member/count`, {
          params: {
            gId: gId,
          },
        })
        .then((res) => {
          this.memberCount = res.data.memberCount;
          // console.log("memberCount : " + this.memberCount)
        })
        .catch((err) => {
          console.log(err);
        });
    },
      getPermission: function(gId) {
        axios.get(`${SERVER_URL}/group/member/apply/state`, {params: {gId: gId, nickname: this.loginStatus.nickname}})
          .then(res => {
            const none = document.querySelector("#none")
            const wait = document.querySelector("#wait")
            const mine = document.querySelector("#mine")
            const ban = document.querySelector("#ban")
            if (this.loginStatus.token == null) {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "none"
              ban.style.display = "none"
            } else if (res.data.state == 0 || res.data.state == 4) {
              none.style.display = "none"
              wait.style.display = "block"
              mine.style.display = "none"
              ban.style.display = "none"
            } else if (res.data.state == 1 || res.data.state == 3) {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "block"
              ban.style.display = "none"
            } else if (res.data.state == 5) {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "none"
              ban.style.display = "block"
            } else {
              none.style.display = "block"
              wait.style.display = "none"
              mine.style.display = "none"
              ban.style.display = "none"

            }
          })
          .catch(err => {
            console.log(err)
            const none = document.querySelector("#none")
            const wait = document.querySelector("#wait")
            const mine = document.querySelector("#mine")
            const ban = document.querySelector("#ban")
            none.style.display = "none"
            wait.style.display = "none"
            mine.style.display = "none"
            ban.style.display = "none"
          })
      },
      getPicture(email) {
        axios.get(`${SERVER_URL}/user/profile`, {params: {email: email}})
          .then(res => {
            const picture = res.data.User.picture
            if(picture == null) {
              this.picture = "https://images.unsplash.com/photo-1519400197429-404ae1a1e184?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80"
            } else {
              this.picture = picture
            }

         })
         .catch(err => {
           console.log(err)
         })

      },
      removeApply: function(gId) {
        axios.delete(`${SERVER_URL}/group/member/cancel`,
        {data: {gId: gId, nickname: this.loginStatus.nickname}})
         .then(res => {
           const none = document.querySelector("#none")
           const wait = document.querySelector("#wait")
           none.style.display = "block"
           wait.style.display = "none"
         })
         .catch(err => {
           console.log(err)
         })
      },
      applyGroup: function(gId,email,groupname) {
        axios.post(`${SERVER_URL}/group/member/apply`,
        {gId: gId, email: this.loginStatus.email, nickname: this.loginStatus.nickname})
          .then(res => {
            const none = document.querySelector("#none")
            const wait = document.querySelector("#wait")
            none.style.display = "none"
            wait.style.display = "block"
          })
          .catch(err => {
            console.log(err)
          })

        this.$axios
          .post(`${SERVER_URL}/notification/create`, {
            receiveEmail : email,
            sendEmail :  this.loginStatus.email,
            isRead : 0,
            type : 'apply',
            contentId : gId,
            message : this.loginStatus.nickname + ' 님이 [' + groupname + ']에 참가 신청을 하였습니다.',
            notiUrl : '/GroupPage?gId=' + gId,
          })
          // .then((res) => {
          //   if (res.data.success) {
          //     // console.log("receiveEmail >>> "+email);
          //     // alert('등록되었습니다.');
          //   } else {
          //     // console.log('알림 전송 실패');
          //   }
          // })
          .catch((err) => {
            console.log(err);
          });
      },
      goCreate: function() {
        // const data = [item]
        this.$router.push({path: './GroupCreate'})
      },
      goDetail: function(group) {
        this.$router.push({path: './GroupDetail', query: {gId: group.gId}})
      },
      updateGroup: function(gId) {
        this.$router.push({path: './GroupUpdate', query: { gId: gId}})
      },
      deleteGroup: function(gId) {
        axios.delete(`${SERVER_URL}/group/delete/`, {headers: {gId: gId}})
          .then(res => {
            //임시방편
            window.location.reload();
            // 모달창 닫기
            // let targetModal = document.querySelector('#group-'+this.idx)
            // // console.log(targetModal)
            // 리스트에서 해당 스터디
          })
          .catch(err => {
            console.log(err)
          })
      },
      goGroupPage: function(myGroup) {
        this.$router.push({ path: './GroupPage', query: { gId: myGroup.gId } });
      },
      myGroupList: function() {
        axios.get(`${SERVER_URL}/group/member/apply/user/list`, {
            params: {
              email: this.loginStatus.email,
            },
          })
          .then((res) => {
            if (res.data.list.length != 0) {
              this.existMyGroups = true
            }
            for (let i = 0; i < res.data.list.length; i++) {
              res.data.list[i].edate = res.data.list[i].edate.split(' ')[0];
            }
            this.myGroups = res.data.list;
          })
          .catch((err) => {
            console.log(err);
          });
      },
      groupList: function() {
        axios
          .get(`${SERVER_URL}/group/list/`, { params: this.params })
          .then((res) => {
            for (let i = 0; i < res.data.list.length; i++) {
              res.data.list[i].edate = res.data.list[i].edate.split(' ')[0];
            }
            this.groups = res.data.list;
          })
          .catch((err) => {
            console.log(err);
          });

    
      },
      goLogIn: function(){
        this.$router.push({ path: './login' });
      },
    }
  }

  function counter(dates){
    var dday2=dates;
//var dday = new Date("Apr 7,2018,09:00:00").getTime(); //디데이
		var dday = new Date("Jun 10,2020,23:59:59").getTime(); //디데이
		setInterval(function(){
			var now = new Date(); //현재 날짜 가져오기
			var distance = dday - now;
			var d = Math.floor(distance / (1000 * 60 * 60 * 24));
			var h = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
			var m = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
			var s = Math.floor((distance % (1000 * 60)) / 1000);
			var view = '';
			if(s < 10){
				s = '0'+s;
			}
			if (distance<0) {
				//$('#dday-view').html('마감되었습니다.');
			//	$('#dday-div').hide();
			} else {
				if (d>0) {
					view = view+d+'일 ';
				}
				if (h>0) {
					view = view+h+'시간 ';
				}
				if (m>0) {
					view = view+m+'분 ';
				}
				$('#dday-view').html(view);
        document.getElementById('dday').value = view;
			}
		}, 1000);
	}
	counter();
</script>

<style scoped>
.jumbotron{
  background-image: url(https://images.unsplash.com/photo-1552152974-19b9caf99137?fit=crop&w=1350&q=80) ;
}

.modal-body{
  padding: 0px 0px 0px 0px;
}

.bannerImg{
  width: 100%;
  height: 300px;
  overflow: hidden;
}

.entry{
  width:100%;
  height:300px;
  margin-bottom: 200px;
}

h1.title{
  text-shadow: 1px 1px 2px #666666;
  color:white;
  padding-top:107px;
  text-align: center;
}

.card-img, .card-img-top{
  height: auto;
  width: 100%;
  align-content: center;
  overflow:hidden;
}

div.card-body{
  padding: 0px 0px 0px 0px;
}
.card-title{
  padding: 5px 5px 5px 5px;
}

.close:after{
  padding: 0px 0px 0px 0px;
}

  .img .content2{
     position: absolute;
     top:50%;
     left:50%;
     transform: translate(-50%, -50%);                                                                   
     font-size:2rem;
     color: white;
     z-index: 2;
     width:100%;
     text-align: center;
     text-shadow: 2px 2px 4px #333;
  }

  .filter1{filter:brightness(0.4);}

.crown{
  color: orange;
  width: 30px;
  height: 30px;
  font-size: 15px;
}

.crown:after{
  content: "\f521";
  font-family: "Font Awesome\ 5 Free";
}

</style>
