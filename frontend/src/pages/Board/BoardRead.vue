<template>
  <div class="container mt-5 pt-5">
    <div>
      <table class="tbAdd" width="100%">
        <colgroup>
          <col width="20%" />
          <col width="80%" />
        </colgroup>
        <tr>

          <td><h5 style="font-weight : 1200;">{{ title }}</h5></td>
        </tr>
        <tr>

          <td><img :src="picture" class="rounded-circle" style="margin-top:15px; width:65px; height:65px" alt="" />{{ nickname }}</td>
        </tr>
        <tr>

          <td class="txt_cont" v-html="contents.replace(/(?:\r\n|\r|\n)/g, '<br />')"></td>
        </tr>
      </table>
    </div>

    <div class="btnWrap d-flex justify-content-center">
      <b-button href="javascript:history.back()" class="btn btnList m-1">목록</b-button>
      <div v-if="loginStatus.email == email">
        <b-button v-if="email" @click="fnUpdate" class="btn-info btnUpdate m-1"
          >수정</b-button
        >
        <b-button v-if="email" @click="fnDelete" class="btn-danger btnDelete m-1"
          >삭제</b-button
        >
      </div>
    </div>
    <ReplyWrite :bId="bId" :boardEmail="email" v-if="this.loginStatus.nickname"/>
    <ReplyList v-for="(items,index) in showList" :items="items" :key="index" />
    <b-pagination class="pagination pagination-primary" value="3" v-model="replyPage" :total-rows="pageCnt" per-page="10" align="center" ></b-pagination>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import axios from 'axios';
import ReplyWrite from '../Reply/ReplyWrite.vue';
import ReplyList from '../Reply/ReplyList.vue';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  components: {
    ReplyWrite,
    ReplyList,
  },
  data() {
    return {
      picture: '',
      form: '',
      bId: this.$route.query.bId,
      email: '',
      nickname: '',
      title: '',
      contents: '',
      noticeFlag: '',
      date: '',
      category: '',
            
      list : [],
      replyPage : '',
      pageCnt : '',
    };
  },
  computed: {
    ...mapState([
      'loginStatus'
    ]),
    showList: function() {
      return this.list.slice(10*(this.replyPage-1), 10*this.replyPage)
    },
  },
  created() {
    this.fnGetView();
    this.getList();
  },
  methods: {
    fnGetView() {
      this.$axios
        .get(`${SERVER_URL}/board/read`, {
          params: { type: 'bId', word: this.$route.query.bId },
        })
        .then((res) => {
          this.title = res.data.list[0].title;
          this.contents = res.data.list[0].contents;
          this.email = res.data.list[0].email;
          this.nickname = res.data.list[0].nickname;
          this.noticeFlag = res.data.list[0].noticeFlag;
          this.date = res.data.list[0].date;
          this.category = res.data.list[0].category;
          axios.get(`${SERVER_URL}/user/profile`, {params: {email: this.email}})
            .then(resp => {
              this.picture = resp.data.User.picture
            })
            .catch(err => {
              console.log(err)
            })

        })
        .catch((err) => {
          console.log(err);
        });
    },
    fnUpdate() {
      this.form = {
        bId: this.bId,
        email: this.email,
        nickname: this.nicknmae,
        title: this.title,
        contents: this.contents,
        noticeFlag: this.noticeFlag,
        date: this.date,
        category: this.category,
      };
      this.$router.push({ path: './BoardUpdate', query: this.form });
    },
    fnDelete() {
      if (confirm('정말로 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/board/delete`, {
            headers: {
              bId: this.bId,
            },
          })
          .then((res) => {
            if (res.data.success === 'success') {
              alert('게시글이 삭제 되었습니다.');
              history.back();
            } else {
              alert('게시글 삭제에 실패하셨습니다.');
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
    getList() {
      this.$axios
        .get(`${SERVER_URL}/reply/list`, {
          params: {page: 1, bId: this.$route.query.bId},
        })
        .then((res) => {
          this.list = res.data["list"];
          this.pageCnt = this.list.length
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
};
</script>

<style scoped>
.tbAdd {
  border-top: 1px solid #888;
}
.tbAdd th,
.tbAdd td {
  border-bottom: 1px solid #eee;
  padding: 5px 0;
}
.tbAdd td {
  padding: 10px 10px;
  box-sizing: border-box;
  text-align: left;
}
.tbAdd td.txt_cont {
  height: 300px;
  vertical-align: top;
}
.btnWrap {
  text-align: center;
  margin: 20px 0 0 0;
}
</style>
