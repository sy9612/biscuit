<template>
  <div>
    <n-button type="default" v-b-modal.modal-1 id="button" style="border-radius: 10rem; width:90px;">회원탈퇴</n-button>

    <b-modal ref="my-modal" id="modal-1" title="회원탈퇴" hide-footer>
      <p class="my-4">
        회원을 탈퇴하시면, 그동안 쌓여왔던 모든 자료와 데이터가 삭제
        되며 참여하고 있던 모든 스터디 공간에 접근 할 수 <br>없게 됩니다.
        그럼에도 꼭 탈퇴하고 싶으시다면 
        탈퇴사유를 남겨주시길 부탁드립니다.
      </p>

      <div>
        <b-form-textarea
          id="textarea"
          v-model="reason"
          placeholder="탈퇴사유"
          rows="3"
          max-rows="6"
        ></b-form-textarea>
        <p style="margin-top:20px">
          &nbsp;비밀번호를 입력 해 주세요
        </p>
        <div style="margin-top:-10px">
          <b-form-input
            type="password"
            v-model="mem.password"
            placeholder="비밀번호"
          ></b-form-input>
        </div>

        <!-- <pre class="mt-3 mb-0">{{ text }}</pre> -->
        <div style="margin-top:20px;">
          <b-button
            variant="outline-primary"
            style="margin-left:0px;border-radius:10rem"
            @click="deleteUser()"
            >탈퇴하기</b-button
          >
          <b-button variant="danger" @click="hideModal" style="margin-top:10px;margin-left:10px;border-radius:10rem">취소</b-button>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import { Button, FormGroupInput } from '@/components';
export default {
  data() {
    return {
      reason: '',
      mem: {
        email: '',
        password: '',
      },
      
    };
  },
 components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },

  methods: {
    hideModal() {
      this.$refs['my-modal'].hide();
    },
    deleteUser() {
      // console.log('delete token : ' + localStorage.getItem('token'));
      axios
        .post(`${SERVER_URL}/user/delete`,this.mem, {
          headers: {
            "x-access-token": localStorage.getItem('token')
          }
        })
        .then((response) => {
          if (response.data.success === 'success') {
            alert('회원탈퇴에 성공하셨습니다.');
            this.logout();
          } else {
            alert('비밀번호를 정확히 입력해주세요.');
          }
        })
        .catch(function(error) {
          alert("그룹장인 경우 탈퇴가 불가능합니다.");
          console.log(error);
        });
    },
    logout() {
      if (localStorage.getItem('token') !== null) {
        localStorage.removeItem('token');
        localStorage.removeItem('id');
        localStorage.removeItem('name');
        localStorage.removeItem('admin');
      }
      this.$emit('logout');
    },

  },

    created() {
    this.mem.email = localStorage.getItem('email');
  },

};
</script>

<style>

#button {
  width: 100px;
  height: 40px;
  font-size: 10px;
}
</style>
