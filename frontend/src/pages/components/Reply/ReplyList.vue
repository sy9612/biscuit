<template>
      <b-row class="mb-1">
        <b-col>
          <b-card border-variant="info" class="mb-2" no-body>
            <!-- <template> -->
              <b-row class="m-1">
                <b-col class="text-left ml-3" 
                  ><strong>{{items.nickname}}</strong> <small>({{items.date}})</small>
                </b-col>
                <b-col class="text-right mr-3" v-show="user.email===items.email" >
                  <b-button @click="modifyClick" variant="link">수정</b-button>
                  <b-button @click="deleteReplyConfirm" variant="link">삭제</b-button>
                </b-col>
              </b-row>
            <!-- </template> -->
            <b-card-body class="text-left">
              <div id="viewcomment" style="margin-left:30px" v-show="isView">
                {{items.contents}}
              </div>

              <div id="modifyinput" v-show="!isView">
                <b-form @submit="onSubmit">
                  <b-row class="mb-3 mt-2">
                    <b-col cols="11">
                      <b-form-textarea
                        id="modicontents"
                        v-model="modicontents"
                        placeholder="댓글을 입력하세요."
                        rows="2"
                      ></b-form-textarea>
                    </b-col>
                    <b-col><b-button type="submit" variant="dark">수정</b-button> </b-col> 
                  </b-row>
                </b-form>
              </div>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
</template>

<script>
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
        email : localStorage.getItem("email")  //"ssafy@ssafy.com", //로그인 되어있는 유저 이메일 -> 현재 로그인 되어있는 유저로 바꿔야함
      },
      items2 : [],
    }
  },
  computed: {
    items: function() {
      // // console.log('props가 바꼈어요!')
      const dummyItems = Object
      dummyItems.rId = this.$props.reply.rId;
      dummyItems.bId = this.$props.reply.bId;
      dummyItems.email = this.$props.reply.email;
      dummyItems.nickname = this.$props.reply.nickname;
      dummyItems.contents = this.$props.reply.contents;
      dummyItems.date = this.$props.reply.date;
      return dummyItems
    }
  },
  created() {
    this.items2 = this.$props.reply;
  },
  methods: {
    modifyClick(){
      this.isView = !this.isView;
    },
    onSubmit(event) {
      event.preventDefault();
      // console.log("수정 : "+this.user.email +" / "+this.modicontents+" / "+this.rId);
      this.$axios
      .put(`${SERVER_URL}/reply/update`, {
        email : this.user.email,
        contents : this.modicontents,
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