<template>
  <div class="page-header clear-filter" filter-color="orange">
    <div
      class="page-header-image"
      style="background-image: url('img/login.jpg')"
    ></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain>
            <div slot="header" class="logo-container">
              <img v-lazy="'img/bislogo.png'" alt="" />
            </div>
            
              <fg-input
                class="no-border input-lg"
                addon-left-icon="now-ui-icons users_single-02"
                v-model="user.email"
                ref="id"
                placeholder="아이디"
                v-on:keyup.enter="checkLogin"
              >
              </fg-input>

              <fg-input
                class="no-border input-lg"
                addon-left-icon="now-ui-icons objects_key-25"
                v-model="user.password"
                ref="password"
                placeholder="비밀번호"
                type="password"
                v-on:keyup.enter="checkLogin"
              >
              </fg-input>
              <!-- <template slot="raw-content" id="function_button"> -->
                <div class="card-footer " style="margin-right:px;margin-top:0px;margin-left:-2px">
                  <a
                    class="btn btn-primary btn-round btn-lg btn-block"
                    v-on:click="checkLogin"
                    >로그인</a
                  >
                </div>
                <div class="pull-left">
                  <h6>
                    <a v-on:click="signup()" class="link footer-link"
                      >새로운 계정 만들기</a
                    >
                  </h6>
                </div>
                <div class="pull-right">
                  <h6>
                    <a v-on:click="findPw()" class="link footer-link"
                      >비밀번호 찾기</a
                    >
                  </h6>
                </div>
              <!-- </template> -->
            
          </card>
        </div>
      </div>
    </div>
    <main-footer></main-footer>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from '@/components';
import MainFooter from '@/layout/MainFooter';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  name: 'login-page',
  data: function() {
    return {
      //   isLogin: false,
      user: {
        email: '',
        password: '',
      },
      message: '',
    };
  },
  bodyClass: 'login-page',
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  methods: {
    checkLogin() {
      // let err = true;
      // let msg = '';

      // // console.log("로그인체크");
      // !this.user.email &&
      //   ((msg = '아이디를 입력해주세요'), (err = false), this.$refs.id.focus());
      // err &&
      //   !this.user.password &&
      //   ((msg = '비밀번호를 입력해주세요'),
      //   (err = false),
      //   this.$refs.password.focus());

      // if (!err) alert(msg);
      // else
      this.login();
    },
    login() {
      // LOGIN 액션 실행
      // 서버와 통신(axios)을 해 토큰값을 얻어야 하므로 Actions를 호출.
      axios
        .post(`${SERVER_URL}/user/login`, this.user)
        .then((response) => {
          localStorage.setItem('token', response.data['x-access-token']);
          localStorage.setItem('email', response.data.user.email);
          localStorage.setItem('nickname', response.data.user.nickname);
          localStorage.setItem('admin', response.data.user.admin);
          localStorage.setItem('phone', response.data.user.phone);
          localStorage.setItem('region', response.data.user.region);
          localStorage.setItem('picture', response.data.user.picture);
          // localStorage.setItem('admin', response.data.admin);
          // this.$store.dispatch('login', true);
          this.$router.replace(`/`);
          window.location.reload();
        })
        .catch(function(error) {
          alert('아이디 혹은 비밀번호를 다시 확인 해 주세요');
          console.log(error);
        });
    },
    signup() {
      this.$router.push('/join');
    },
    // findId: function() {
    //   this.$router.push('/findid');
    // },
    findPw() {
      this.$router.push('/findpw');
    },
  },
};
</script>

<style>
.pull-left,
.pull-right{
  cursor: pointer;
}
</style>
