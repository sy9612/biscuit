<template>
  <navbar
    class="pb-0"
    position="fixed"
    type="primary"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
  >
    <div style="position:relative; bottom:10px">
      <router-link v-popover:popover1 class="navbar-brand pb-0 mb-0" to="/">
        <img class="n-logo" src="img/bisWhite.png" height="30px" alt="" />
      </router-link>
      <el-popover
        ref="popover1"
        popper-class="popover"
        placement="bottom"
        width="200"
        trigger="hover"
      >
        <div class="popover-body">
          서울 4반 7팀
        </div>
      </el-popover>
    </div>
    <template slot="navbar-menu">
      <router-link class="navbar-brand ml-3" to="/grouplist">
        <i
          class="now-ui-icons arrows-1_cloud-download-93"
          style="font-size: 15px"
        >
          스터디</i
        >
      </router-link>

      <router-link class="navbar-brand ml-3" to="/noticeboard">
        <i
          class="now-ui-icons design_app"
          style="font-size: 15px"
        > 공지사항</i>
      </router-link>

      <router-link class="navbar-brand ml-3" to="/boardlist">
        <i
          class="now-ui-icons business_chart-pie-36"
          style="font-size: 15px"
        >
          게시판</i
        >
      </router-link>

      <div class="ml-3" style="position:relative; bottom:6px;">
        <router-link class="navbar-brand" to="/profile" v-if="loginStatus.email">
        <img
          v-if="this.token"
          :src="loginStatus.picture"
          alt=""
          class="rounded-circle"
          style="width:40px; height:40px"
          />

          <span
            style="font-size: 15px; "
          > {{loginStatus.nickname}}</span>
        </router-link>

      </div>
      <drop-down class="ml-3" v-if="this.token !== ''">
        <b-icon-bell
          slot="title"
          class="dropdown-toggle"
          data-toggle="dropdown"
          font-scale="1.3"
          style="margin-top:10px;margin-right:15px;margin-left:-5px;color:white"
          @click="readNotification"
        />

        <div v-for="(item, idx) in items" v-bind:key="idx">
          <div class="dropdown-item" v-if="item.isRead == 1" id="readNoti">
            <button
              style="background-color: white; border:0;outline:0; font-size:10px;margin-left:-20px"
              @click="goNoti(item)"
            >
              {{ item.message }}
            </button>
            <i
              class="now-ui-icons ui-1_simple-remove"
              style="margin-left:5px ;margin-top:-3px"
              @click.prevent="deleteNoti(item)"
            />
          </div>

          <div class="dropdown-item" v-if="item.isRead == 0" id="notReadNoti">
            <button
              style="background-color:white ; border:0;outline:0;font-weight:bold;font-size:10px;;margin-left:-20px"
              @click="goNoti(item)"
            >
              {{ item.message }}
            </button>
            <i
              class="now-ui-icons ui-1_simple-remove"
              style="margin-left:5px ;margin-top:-3px"
              @click.prevent="deleteNoti(item)"
            />
          </div>
        </div>
      </drop-down>
      <span
        v-if="notiCnt > 0"
        class="badge badge-warning badge-pill"
        style="margin-left:-20px;height:18px;width:17px;margin-right:10px;font-size:8px"
      >
        <span style="margin-left:-3px;">{{ notiCnt }}</span>
      </span>

      <li class="nav-item mb-2">
        <a
          class="nav-link btn btn-primary"
          v-on:click="goToLogin()"
          target="_blank"
          v-if="this.token === ''"
        >
          <i class="now-ui-icons arrows-1_share-66"></i>
          &nbsp;<p style="font-size : 120%">로그인</p>
        </a>
        <a
          class="nav-link btn btn-primary"
          v-on:click="onClickLogout()"
          target="_blank"
          v-else
        >
          <i class="now-ui-icons arrows-1_share-66"></i>
          &nbsp;<p style="font-size : 120%">로그아웃</p>
        </a>
      </li>
    </template>
  </navbar>
</template>

<script>
import { DropDown, Navbar } from '@/components';
import { Popover } from 'element-ui';
import axios from 'axios';
import { mapState } from 'vuex';
import Button from '../components/Button.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  name: 'main-navbar',
  data() {
    return {
      id: '',
      name: '',
      admin: '',
      type: '',
      token: '',
      items: [],
      notiCnt: '0',
    };
  },
  props: {
    transparent: Boolean,
    colorOnScroll: Number,
  },
  computed: {
    ...mapState(['loginStatus']),
  },
  components: {
    DropDown,
    Navbar,
    // Button,
    [Popover.name]: Popover,
  },
  mounted() {
    if (this.loginStatus.email) {
      this.form = { receiveEmail: localStorage.getItem('email') };
      axios
        .get(`${SERVER_URL}/notification/read`, {
          params: this.form,
        })
        .then((res) => {
          this.items = res.data.list;
          for (var i in this.items) {
            if (this.items[i].isRead == 0) {
              this.notiCnt++;
            }
          }
        });
    }
  },
  methods: {
    onClickLogout() {
      // // console.log('onclicklogout확인' + this.token + 'gd');
      localStorage.removeItem('token');
      localStorage.removeItem('email');
      localStorage.removeItem('nickname');
      localStorage.removeItem('admin');
      localStorage.removeItem('phone');
      localStorage.removeItem('region');
      this.$emit('logout');
    },
    goToLogin() {
      this.$router.replace(`/login`);
    },
    readNotification() {
      this.form = { receiveEmail: localStorage.getItem('email') };
      axios
        .get(`${SERVER_URL}/notification/read`, {
          params: this.form,
        })
        .then((res) => {
          this.items = res.data.list;
        });
    },
    goNoti(item) {
      this.form = { isRead: 1, nId: item.nId };
      axios
        .put(`${SERVER_URL}/notification/update`, this.form, {
          headers: {
            'x-access-token': localStorage.getItem('token'),
          },
        })
        .then((response) => {
          if (response.data.success === 'success') {
            //// console.log('정보 수정에 성공하셨습니다.');
             this.form = { receiveEmail: localStorage.getItem('email') };
            axios
              .get(`${SERVER_URL}/notification/read`, {
                params: this.form,
              })
              .then((res) => {
                if (response.data.success === 'success') {
                  this.notiCnt = 0;
                  this.items = res.data.list;
                  //// console.log('정보 수정에 성공하셨습니다.2222');
                  for (var i in this.items) {
                    if (this.items[i].isRead == 0) {
                      this.notiCnt++;
                    }
                  }
                }
              })
              .catch(function(error) {
                console.log(error);
                //// console.log('정보 수정에 실패하셨습니다.zzzz');
              });
          }})
          .catch(function(error) {
            console.log(error);
          });
      this.$router.replace(item.notiUrl);
      this.$emit('clickNotify');
      //window.location.reload();
    },
    deleteNoti(item) {
      this.delForm = { nId: item.nId };
       this.form = { receiveEmail: localStorage.getItem('email') };
      axios
        .delete(`${SERVER_URL}/notification/delete`, {
          params: this.delForm,
        })
        .then((res) => {
          if (res.data.success === 'success') {
            //// console.log('삭제하였습니다.');
            axios
              .get(`${SERVER_URL}/notification/read`, {
                params: this.form,
              })
              .then((res) => {
                this.notiCnt = 0;
                this.items = res.data.list;
                for (var i in this.items) {
                  if (this.items[i].isRead == 0) {
                    this.notiCnt++;
                  }
                }
              });
          } else {
            // console.log('실패');
          }
        });
    },
  },
  created() {
    if (localStorage.getItem('token')) {
      this.token = localStorage.getItem('token');
      this.email = localStorage.getItem('email');
      this.nickname = localStorage.getItem('nickname');
      this.admin = localStorage.getItem('admin');
    }
  },
};
</script>

<style>
#readNoti {
  /* background-color: rgb(240, 240, 240); */
  opacity: 0.5;
}
span{
  font-family: NanumSquare;
}
@font-face {
  font-family: 'NanumSquare';
  font-weight: 700;
  src: url('https://cdn.rawgit.com/moonspam/NanumSquare/master/NanumSquareR.eot');
  src: url('https://cdn.rawgit.com/moonspam/NanumSquare/master/NanumSquareR.eot?#iefix')
      format('embedded-opentype'),
    url('https://cdn.rawgit.com/moonspam/NanumSquare/master/NanumSquareR.woff') format('woff'),
    url('https://cdn.rawgit.com/moonspam/NanumSquare/master/NanumSquareR.ttf') format('truetype');
}
</style>
