<template>
  <div>
    <div>
       
      <div class="content-center brand">
        
        <div class="container" v-if="mode==0">
          <h3 style="font-size: 30px; margin-top:30px;margin-bottom:10px;font-weight:bold; text-align: center;">스터디 게시판</h3>
          <br><br>
          <div class="AddWrap">
            <form>
              <table class="tbAdd" width="100%">
                <colgroup>
                  <col width="20%" />
                  <col width="80%" />
                </colgroup>
                <tr>
                  <!-- <th>제목</th> -->
                  <td v-bind="title">{{ title }}</td>
                </tr>
                <tr>
                  <!-- <th>작성자</th> -->
                  <td><img :src="picture" class="rounded-circle" style="margin-top:15px; width:65px; height:65px" alt="" />{{ nickname }}</td>
                </tr>
                <tr>
                  <!-- <th>내용</th> -->
                  <td class="txt_cont" v-bind="contents" v-html="contents.replace(/(?:\r\n|\r|\n)/g, '<br />')"></td>
                </tr>
              </table>
            </form>
          </div>

          <div class="btnWrap d-flex justify-content-center">
            <b-button @click="fnList" class="btn btnList m-1">목록</b-button>
            <div v-if="loginStatus.email == email">
              <b-button v-if="email" @click="fnUpdate" class="btn-info btnUpdate m-1">수정</b-button>
              <b-button v-if="email" @click="fnDelete" class="btn-danger btnDelete m-1">삭제</b-button>
            </div>
          </div>
          <!-- <ReplyWrite :bId="bId"  v-if="this.loginStatus.nickname"/> -->
          <group-reply-write :info="info" @wirtereply="changeMode" v-if="this.loginStatus.nickname" />
          <div>
            <!-- <ReplyList v-for="(reply,index) in showList" :reply="reply" :key="index" /> -->
            <group-reply-list v-for="(items,index) in showList" :items="items" :key="index" @listbtn="changeMode" ></group-reply-list>
            <b-pagination  class="pagination pagination-primary" v-model="replyPage" pills :total-rows="pageCnt" per-page="10" align="center" @page-click="getList(page)"></b-pagination>
          </div>
        </div>
        <div class="container" v-if="mode==1">
          <group-board-update :detail="forupdate" @updatebtn="changeMode" ></group-board-update>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import axios from 'axios';
import GroupReplyWrite from './GroupReply/ReplyWrite.vue';
import GroupReplyList from './GroupReply/ReplyList.vue';
import GroupBoardUpdate from './BoardUpdate.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  props :{
    info : {},
  },
  components: {
    GroupReplyWrite,
    GroupReplyList,
    GroupBoardUpdate,
  },
  data() {
    return {
      picture: '',
      form: '',
      bId: '',
      email: '',
      nickname: '',
      title: '',
      contents: '',
      noticeFlag: '',
      date: '',
      category: '',
      
      //for reply
      list : [],
      replyPage : '',
      pageCnt : 1,
      currentPage : 1,

      mode : 0,
      forupdate : [],
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
    this.bId = this.$props['info'].bId;
    this.gId = this.$props['info'].gId;
  },
  mounted() {
    this.fnGetView();
    this.getList();
  },
  methods: {
    changeMode(num){
      // // console.log("IN CHANGE MODE");
      this.fnGetView();
      this.getList();
      this.pageCnt = 1;
      // this.mode = 5;
      this.mode = num;
    },
    // changePage: function() {
    //   this.showList = this.list.slice(10*(this.replyPage-1), 10*this.replyPage)
    //   // // console.log(this.replyPage)
    //   // // console.log(temporaryList)
    // },
    fnGetView() {
      this.$axios
        .get(`${SERVER_URL}/gboard/read`, {
          params: { 
            type: 'bId', 
            word: this.bId,
            gId : this.gId 
          },
        })
        .then((res) => {
          // console.log(res.data);
          this.title = res.data.list[0].title;
          this.contents = res.data.list[0].contents;
          this.email = res.data.list[0].email;
          this.nickname = res.data.list[0].nickname;
          this.noticeFlag = res.data.list[0].noticeFlag;
          this.date = res.data.list[0].date;
          this.category = res.data.list[0].category;
          axios.get(`${SERVER_URL}/user/profile`, {params: {email: this.email}})
          .then(resp => {
            this.picture = resp.data.User.picture;
          })
          .catch(err => {
            console.log(err);
          })
        })
        .catch((err) => {
          console.log(err);
      });
    },
    fnList() {
      this.$emit('readbtn',1);
      return;
    },
    fnUpdate() {
      var temp = {
        gId : this.gId,
        bId: this.bId,
        email: this.email,
        nickname: this.nicknmae,
        title: this.title,
        contents: this.contents,
        noticeFlag: this.noticeFlag,
        date: this.date,
        category: this.category,
      };
      this.forupdate = temp;
      this.mode = 1;
    },
    fnDelete() {
      if (confirm('정말로 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/gboard/delete`, {
            headers: {
              bId: this.bId,
            },
          })
          .then((res) => {
            if (res.data.success === 'success') {
              alert('게시글이 삭제 되었습니다.');
              this.fnList();
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
      // // console.log("REPLY GET LIST");
      this.$axios
        .get(`${SERVER_URL}/greply/list`, {
          params: {
            page: 1, 
            bId: this.bId,
          },
        })
        .then((res) => {
          this.list = res.data["list"];     
          this.pageCnt = this.list.length;
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


.pagination {
  margin: 20px 0 0 0;
  text-align: center;
}
.first,
.prev,
.next,
.last {
  border: 1px solid #666;
  margin: 0 5px;
}
.pagination span {
  display: inline-block;
  padding: 0 5px;
  color: #333;
}
.pagination a {
  text-decoration: none;
  display: inline-blcok;
  padding: 0 5px;
  color: #666;
}
.pagination .page-item.active>.page-link{
  background-color: #f96332 !important;
}
/* 
.pagination .page-item.active > .page-link, .pagination .page-item.active > .page-link:focus, .pagination .page-item.active > .page-link:hover{
  background-color:#f96332;
} */

</style>
