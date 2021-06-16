<template>
  <div 
  class="section section-signup"
    style="background-image: url('img/bg11.jpg'); background-size: cover; background-position: top center; min-height: 950px;"
  >
    <div id="app">
      <div style="margin-top:0px">
        <h2 class="findpw_title">비밀번호 찾기</h2>
        <center>
          <div class="findpw_cnt">
            <div>
              <p style="font-weight:bold">
                본인인증을 통해 아이디(이메일) 확인 및<br />
                비밀번호를 변경하실 수 있습니다.
              </p>
            </div>
            <form>
              <div v-if="emailVeify">
                <div>
                  <b-form-input
                    type="text"
                    id="cert-id"
                    placeholder="아이디 (이메일)"
                    style="background-color:white"
                    v-model="mem.email"
                  />
                </div>
                <div class="cert-wrap">
                  <b-form-input
                    type="text"
                  
                    id="certinum"
                    placeholder="인증번호를 입력하세요"
                    style="width:270px; margin-right:20px;; background-color:white"
                    v-model="code"
                  />
                  <input
                    type="button"
                    id="certinum_btn"
                    value="인증번호 받기"
                    style="border-radius: 10rem; background-color:#2c2c2c"
                    @click.prevent="sendEmail()"
                  />
                </div>
                <div>
                  <button
                    type="button"
                    class="btn btn-primary"
                    id="findpw_btn"
                    @click.prevent="checkCode()"
                    style="border-radius: 10rem; background-color:#2c2c2c"
                  >
                    확인
                  </button>
                  
                </div>
              </div>
            </form>
          </div>
        </center>
      </div>
    </div>

    <div v-if="!emailVeify">
      <div>
        <div>
          <br />
          <center>
          <b-form-input
            type="password"
            id="cert-id"
            style=" width: 300px ; background-color:white"
            placeholder="새 비밀번호를 입력하세요"
            v-model="mem.newpassword"
          />
          <br />
          <b-form-input
            type="password"
            id="cert-id"
            style="margin-top: px; margin-bottom: px; width: 300px;background-color:white"
            placeholder="새 비밀번호를 한 번 더 입력하세요"
            v-model="verify_newpassword"
            @keypress.enter="updatePw()"
          />
          
          <br />
          &nbsp;
          <button class="btn btn-primary"  style="border-radius: 10rem; background-color:#2c2c2c" id="findpw_btn" @click="updatePw()">확인</button>
          </center>
        </div>
      </div>
    </div>
    <br><br><br><br><br><br><br><br><br><br>
  </div>
</template>

<script>
import axios from 'axios';
// import UpdatePw from './MyPage/updatecomponent/UpdatePw.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {},
  data() {
    return {
      emailVeify: true,
      code: '',
      verify_newpassword: '',
      mem: {
        password: '',
        newpassword: '',
        email: '',
      },
    };
  },
  methods: {
    sendEmail() {
      // console.log(this.email);
      axios
        .post(`${SERVER_URL}/service/mail`, this.mem.email)
        .then((response) => {
          if (response.data.success === 'success') {
            alert('이메일로 코드를 전송하였습니다');
            // console.log('성공');
          } else if (response.data.success === 'error') {
            alert('이메일 형식에 맞추어 다시 입력 해 주세요.');
            // console.log('실패');
          } else {
            // console.log('실패2');
          }
        })
        .catch(function(error) {
          console.log(error);
          alert('이메일 형식에 맞추어 다시 입력 해 주세요.');
        });
    },

    checkCode() {
      // console.log(this.code + '  gd');
      axios
        .post(`${SERVER_URL}/service/verifyCode`, this.code)
        .then((response) => {
          if (response.data.success === 'success') {
            alert('인증 성공');
            this.emailVeify = false;
          } else {
            alert('인증 실패');
          }
        })
        .catch(function(error) {
          console.log(error);
          alert('오류');
        });
    },

    updatePw() {
      if (this.mem.newpassword === this.verify_newpassword) {
        if(this.verifyValidPw(this.mem.newpassword)){
        axios
          .put(`${SERVER_URL}/user/pwupdate`, this.mem)
          .then((response) => {
            if (response.data.success === 'success') {
              alert('변경 완료되었습니다');
              this.isHidden = true;
              this.$router.replace('/login');
            } else {
              alert('에러 발생!');
            }
          })
          .catch(function(error) {
            console.log(error);
            alert('오류');
          });
        }
      } else {
        alert('비밀번호 확인이 일치하지 않습니다.');
      }
    },

    verifyValidPw(str) {
      // console.log('확인작업');
      var pw = str;
      var num = pw.search(/[0-9]/g);
       var eng = pw.search(/[a-z]/gi);
      var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
      // console.log("영문" + eng);
      if(eng < 0 || num < 0 || spe < 0){
        alert('영문,숫자,특수문자 포함 8자리 ~ 20자리 이내로 입력해주세요.');
        return false;
      }
      else if (pw.length < 8 || pw.length > 20) {
        alert('8자리 ~ 20자리 이내로 입력해주세요.');
        return false;
      }
      else if (pw.search(/₩s/) != -1) {
        alert('비밀번호는 공백업이 입력해주세요.');
        return false;
      }

      return true;
    },


  },
};
</script>

<style>
.findpw_title {
  width: 100%;
  margin-bottom: 34px;
  font-weight: 600;
  font-size: 34px;
  text-align: center;
}

.findpw_cnt {
  padding-top: 18px;
  border-top: 4px solid #000;
  border-top-width: 4px;
  border-top-style: solid;
  border-top-color: rgb(0, 0, 0);
  width: 400px;
}

#cert-id {
  width: 100%;
  height: 40px;
  margin-bottom: 10px;
}

.certi-wrap {
  margin-top: 12px;
}

#certinum {
  width: 70%;
  height: 40px;
  display: inline;
}

#certinum_btn {
  width: 120px;
  height: 40px;
  border: none;
  background-color: #474747;
  font-size: 1em;
  color: white;
  display: inline;
  margin-left: -10px;
  box-sizing: border-box;
}

#findpw_btn {
  width: 100px;
  height: 45px;
  border-radius: 30px;
  color: #fff;
  font-size: 16px;
  text-align: center;
  margin: 12px 0 10px;
}
</style>
