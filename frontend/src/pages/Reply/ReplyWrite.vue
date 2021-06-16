<template>
  <div>
    <b-form @submit.prevent="write">
      <div class="mb-3 mt-2 d-flex">
        <b-col cols="11">
          <b-form-textarea
              id="contents"
              v-model="comments.contents"
              placeholder="댓글을 입력하세요."
              rows="3"
              max-rows="6"
              @input="counting()"
              ></b-form-textarea>
        </b-col>
        <b-col class="p-0">
          <b-button type="submit" class="btnAdd">등록</b-button>
        </b-col> 
      </div>
    </b-form>
  </div>
</template>


<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;


export default {
  name: "replywrite",
  props : ['bId','boardEmail'],
  data() {
      return {
        comments : {
          contents : "",
          nickname: localStorage.getItem("nickname"),
          email : localStorage.getItem("email")//"a@a.com" //작성자 이메일(현재 로그인 되어있는 유저 이메일로 바꿔야됨)
        },
        // list : [],
        // rpage : "1",
        // pagination : [],
      }
    },
  created() {
  },
  mounted() {
    this.bId = this.$props.bId;
    this.boardEmail = this.$props.boardEmail;
  },
  methods: {
    write() {
      if(this.comments.contents == '' || this.comments.contents.trim() ==""){
         alert('작성된 댓글 내용이 존재하지 않습니다.');
      }else{
        this.$axios
        .post(`${SERVER_URL}/reply/create`, {
          email : this.comments.email,
          nickname : this.comments.nickname,
          contents : this.comments.contents.length>300?this.comments.contents.substr(0,300):this.comments.contents,
          bId : this.bId
        })
        .then((res) => {
          if (res.data.success) {
            // console.log("bId >>> "+this.bId);
            // alert('등록되었습니다.');
            this.$router.go(this.$router.currentRoute);
          } else {
            alert('실행중 실패했습니다.\n다시 이용해 주세요');
          }
        })
        .catch((err) => {
          console.log(err);
        });

        this.$axios
        .post(`${SERVER_URL}/notification/create`, {
          receiveEmail : this.boardEmail,
          sendEmail :  this.comments.email,
          isRead : 0,
          type : 'reply',
          contentId : this.bId,
          message : this.comments.nickname + '님이 당신의 게시글에 댓글을 달았습니다.',
          notiUrl : '/BoardRead?bId=' + this.bId,
        })
        .then((res) => {
          if (res.data.success) {
            // console.log("receiveEmail >>> "+this.boardEmail);
            // alert('등록되었습니다.');
          } else {
            // console.log('알림 전송 실패');
          }
        })
        .catch((err) => {
          console.log(err);
        });
      }
    },
    counting(){
      this.count = this.comments.contents.length;
      if(this.count >300){
        this.comments.contents = this.comments.contents.substr(0,300);
      }
    },
  },
};
</script>