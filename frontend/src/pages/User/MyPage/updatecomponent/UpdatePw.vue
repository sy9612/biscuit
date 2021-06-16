<template>
  <div>
    <fg-input
      type="password"
      style="margin-top: 10px;width:300px"
      placeholder="현재 비밀번호를 입력하세요"
      v-model="mem.password"
    />

    <fg-input
      type="password"
      style="margin-top: 15px;width:300px"
      placeholder="새 비밀번호를 입력하세요"
      v-model="mem.newpassword"
    />

    <fg-input
      type="password"
      style="margin-top: 15px; margin-bottom: 15px; width:300px"
      placeholder="새 비밀번호를 한 번 더 입력하세요"
      v-model="verify_newpassword"
    />

    <n-button type="primary" @click="cancel()" style="border-radius: 10rem;"
      >변경취소</n-button
    >
    &nbsp;
    <n-button type="primary" @click="updatePw()" style="border-radius: 10rem;"
      >확인</n-button
    >
  </div>
</template>
<script>
import axios from 'axios';
import { Button, FormGroupInput } from '@/components';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data() {
    return {
      mem: {
        password: '',
        newpassword: '',
        email: '',
      },

      verify_newpassword: '',
    };
  },
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },

  methods: {
    cancel() {
      this.$emit('cancel');
    },

    updatePw() {
      if (this.mem.newpassword === this.verify_newpassword) {
        if (this.verifyValidPw(this.mem.newpassword) === false) {
        // console.log('gg');
      }else {
          axios
            .put(`${SERVER_URL}/user/pwupdate2`, this.mem, {
              headers: {
                'x-access-token': localStorage.getItem('token'),
              },
            })
            .then((response) => {
              if (response.data.success === 'success') {
                alert('변경 완료되었습니다');
                this.isHidden = true;
                this.cancel();
              } else {
                alert('현재 비밀번호를 틀리게 입력하셨습니다!');
              }
            })
            .catch(function(error) {
              console.log(error);
              alert('오류');
            });
        }
      } else {
        alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다!');
      }
    },

    verifyValidPw(str) {
      // console.log('확인작업');
      var pw = str;
      var num = pw.search(/[0-9]/g);
      var eng = pw.search(/[a-z]/gi);
      var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
      // console.log('영문' + eng);
      if (eng < 0 || num < 0 || spe < 0) {
        alert('영문,숫자,특수문자 포함 8자리 ~ 20자리 이내로 입력해주세요.');
        return false;
      } else if (pw.length < 8 || pw.length > 20) {
        alert('8자리 ~ 20자리 이내로 입력해주세요.');
        return false;
      } else if (pw.search(/₩s/) != -1) {
        alert('비밀번호는 공백업이 입력해주세요.');
        return false;
      }

      return true;
    },
  },
  mounted() {
    this.mem.email = localStorage.getItem('email');
  },
};
</script>

<style></style>
