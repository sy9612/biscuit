<template>
  <div>
    <b-form @submit.prevent="write">
      <b-row class="mb-3 mt-2">
        <b-col cols="11">
          <b-form-textarea
              id="contents"
              v-model="comments.contents"
              placeholder="댓글을 입력하세요."
              rows="3"
              max-rows="6"
              ></b-form-textarea>
        </b-col>
        <b-col>
          <b-button type="submit" class="btnAdd col-md-2 col-sm-4">등록</b-button>
        </b-col> 
      </b-row>
    </b-form>
  </div>
</template>


<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;


export default {
  name: "replywrite",
  props : ['bId'],
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
  },
  methods: {
    write() {
      this.$axios
        .post(`${SERVER_URL}/reply/create`, {
          email : this.comments.email,
          nickname : this.comments.nickname,
          contents : this.comments.contents,
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
    },
  },
};
</script>