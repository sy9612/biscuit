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

  props : {
    info : {},
  },
  data() {
      return {
        comments : {
          contents : "",
          nickname: localStorage.getItem("nickname"),
          email : localStorage.getItem("email")//"a@a.com" //작성자 이메일(현재 로그인 되어있는 유저 이메일로 바꿔야됨)
        },
        count : 0,
        // list : [],
        // rpage : "1",
        // pagination : [],
      }
    },
  created() {
  },
  mounted() {
    this.bId = this.$props.info.bId;
    this.gId = this.$props.info.gId;
  },
  methods: {
    write() {
      if(this.comments.contents == '' || this.comments.contents.trim() ==""){
         alert('작성된 댓글 내용이 존재하지 않습니다.');
      }else{
        this.$axios
        .post(`${SERVER_URL}/greply/create`, {
          email : this.comments.email,
          nickname : this.comments.nickname,
          contents : this.comments.contents.length>300?this.comments.contents.substr(0,300):this.comments.contents,
          bId : this.bId
        })
        .then((res) => {
          if (res.data.success) {
            alert('등록되었습니다.');
            // this.$router.go(this.$router.currentRoute);
            this.comments.contents = '';
            this.$emit('wirtereply',0);
            return;
          } else {
            alert('실행중 실패했습니다.\n다시 이용해 주세요');
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