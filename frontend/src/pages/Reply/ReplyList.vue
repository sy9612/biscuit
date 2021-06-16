<template>
  <b-row class="mb-1">
    <b-col>
      <div border-variant="info" class="row" no-body
        style="margin-bottom:10px; box-shadow: 5px 5px 5px #cccccc; min-height:80px; border-radius:5px;">
        <div class=""> <img :src="user.picture" class="rounded-circle"
            style="margin-top:15px; margin-right:15px; margin-bottom:15px; width:65px; height:65px" alt="" /> </div>
        <div class="col" style="padding:0px">
          <div class="text-left">
            <div class="d-flex align-items-center" style="margin-top:10px; padding:0">
              <strong style="margin-right:10px">{{items.nickname}}</strong>
              <small>({{items.date}})</small>
              <b-col class="text-right" v-show="user.email===items.email">
                <b-button style="margin:0px;" @click="modifyClick" variant="link">수정</b-button>
                <b-button style="margin:0px;" @click="deleteReplyConfirm" variant="link">삭제</b-button>
              </b-col>
            </div>
          </div>
          <!-- </template> -->
          <hr style="margin:0px;">
          <div class="text-left">
            <div id="viewcomment" v-show="isView" v-html="items.contents.replace(/(?:\r\n|\r|\n)/g, '<br>')"
              style=" margin-top:10px; margin-right:30px; margin-left:0px; margin-bottom:10px; font-size:10pt">
              {{items.contents}}
            </div>
            <div id="modifyinput" v-show="!isView">
              <b-form @submit="onSubmit">
                <b-row class="mb-3 mt-2">
                  <b-col cols="11">
                    <b-form-textarea id="modicontents" v-model="modicontents" placeholder="댓글을 입력하세요." rows="2">
                    </b-form-textarea>
                  </b-col>
                  <b-col class="p-0">
                    <b-button type="submit" variant="dark">수정</b-button>
                  </b-col>
                </b-row>
              </b-form>
            </div>
          </div>
        </div>
      </div>
    </b-col>
  </b-row>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "ReplyList",
  props : { reply : {} },
  data() {
    return {
      isView : true,
      modicontents : "",
      user : {
        nickname : localStorage.getItem("nickname"),
        email : localStorage.getItem("email"),  //"ssafy@ssafy.com", //로그인 되어있는 유저 이메일 -> 현재 로그인 되어있는 유저로 바꿔야함
        picture: '',
      },
      // items : [],
    }
  },
  props: ['items'],
  computed: {
    // itmes: function() {
    //   return this.$props.reply;
    // }
  },
  created() {
    axios.get(`${SERVER_URL}/user/profile`, {params: {email: this.items.email}})
      .then(res => {
        // // console.log(res.data.User.picture)
        this.user.picture = res.data.User.picture
      })
      .catch(err => {
        console.log(err)
      })
    // // console.log(this.$props.reply)
    // this.items = this.$props.reply;
  },
  methods: {
    modifyClick(){
      this.modicontents = this.items.contents;
      this.isView = !this.isView;
    },
    onSubmit(event) {
      event.preventDefault();
      // console.log("수정 : "+this.user.email +" / "+this.modicontents+" / "+this.rId);

      if(this.modicontents == '' || this.modicontents.trim() ==""){
         alert('작성된 댓글 내용이 존재하지 않습니다.');
      }else{
        this.$axios
        .put(`${SERVER_URL}/reply/update`, {
          email : this.user.email,
          contents : this.modicontents.length>300? this.modicontents.substr(0,300): this.modicontents,
          rId : this.items.rId
        })
        .then((res) => {
          if (res.data.success) {
            alert('수정되었습니다.');
            this.$router.go(this.$router.currentRoute);
          } else {
            alert('실행중 실패했습니다.\n다시 이용해 주세요');
          }
        })
        .catch((err) => {
          console.log(err);
        });
      }
    },
    deleteReplyConfirm() {
      if(confirm("정말로 삭제하시겠습니까?")){
        this.deleteReply();
      }
    },
    deleteReply(){
      // console.log("삭제 메소드");
      this.$axios
      .delete(`${SERVER_URL}/reply/delete`, {params: {rId : this.items.rId}})
      .then((res) => {
        if (res.data.success) {
          alert('삭제되었습니다.');
          this.$router.go(this.$router.currentRoute);
        } else {
          alert('실행중 실패했습니다.\n다시 이용해 주세요');
        }
      })
      .catch((err) => {
        console.log(err);
      })
    }
  },
};
</script>

<style>
#viewcomment {
  margin-left: 30px;
  margin-bottom: 10px;
}
</style>