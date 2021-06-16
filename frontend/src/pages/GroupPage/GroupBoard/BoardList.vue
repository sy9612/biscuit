<template>
  <div>
      <div class="content-center brand">
        <h2 style="margin-top:30px;" class="text-center" v-if="active==1">스터디 게시판</h2>

        <div class="container" v-if="active==1">
          <b-input-group>
            <template #prepend>
              <b-select name="type" v-model="type">
                <b-select-option value="title">제목</b-select-option>
                <b-select-option value="name">작성자</b-select-option>
                <b-select-option value="contents">내용</b-select-option>
              </b-select>
            </template>
            <b-form-input
              type="text"
              v-model="word"
              @keyup.enter="fnSearch"
            />
            <b-input-group-append>
              <b-button
                @click="fnSearch"
                text="Button"
                variant="primary"
                class="btnSearch mt-0"
                >검색</b-button>
            </b-input-group-append>
          </b-input-group>
          <div class="listWrap"><br>
            <b-table
              id="my-table"
              :items="list"
              :per-page="perPage"
              :fields="column"
              :current-page="currentPage"
              @row-clicked="rowClick"
              hover
            ></b-table>
            <div >
              <b-pagination
                v-model="currentPage"
                :total-rows="this.list.length"
                :per-page="perPage"
                aria-controls="my-table"
                class="pagination pagination-primary"
                align="center"
                
              ></b-pagination>
            <div class="btnRightWrap">
              <b-button @click="fnAdd" class="btnAdd m-1" style="background-color: #f96332" v-if="this.loginStatus.nickname">글쓰기</b-button>
            </div>
          </div>
          </div>
        </div>

        <group-board-write v-if="active==2" :gId="this.child.gId" @readbtn="chageActive"></group-board-write>
        <group-board-read v-if="active==3" :info="child" @readbtn="chageActive" ></group-board-read>


      </div>
    </div>
  
</template>

<script>
import axios from 'axios';
import {mapState} from 'vuex'
import GroupBoardWrite from './BoardWrite.vue';
import GroupBoardRead from './BoardRead.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  components: { GroupBoardWrite,GroupBoardRead},
  props : ['gId'],
  data() {
    //변수생성
    return {
      column: [
        // 'Bid', 'Title', 'Date'
        {
          key: 'bId',
          label: '순번',
        },
        {
          key: 'title',
          label: '제목',
        },
        {
          key: 'nickname',
          label: '작성자',
        },
        {
          key: 'date',
          label: '작성일',
        },
      ],
      perPage: 10,
      form: '',
      body: '', //리스트 페이지 데이터전송
      // board_code: 'news', //게시판코드
      list: '', //리스트 데이터
      type: 'title',
      word: '',
      currentPage: this.$route.query.page ? this.$route.query.page : 1,
      category: '',
      paginavigation: function() {
        //페이징 처리 for문 커스텀
        var pageNumber = [];
        var start_page = this.paging.start_page;
        var end_page = this.paging.end_page;
        for (var i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
      active : 1,
      child : {
        gId : -1,
        bId : -1,
        // email: '',
        // nickname: '',
        // title: '',
        // contents: '',
        // noticeFlag: '',
        // date: '',
        // category: '',
      },
     
    };
  },
   computed: {
    ...mapState([
      'loginStatus'
    ]),
  },
  mounted() {
    //페이지 시작하면은 자동 함수 실행
    this.fnGetList();
    this.gId = this.$props.gId;
    this.child.gId = this.gId;
  },
  methods: {
    chageActive(num){
      this.currentPage = 1;
      this.fnGetList();
      this.active = num;
    },
    fnGetList() {
      this.form = {
        type: this.type,
        word: this.word,
        currentPage: '',
        category: '',
        gId : this.gId,
      };

      axios
        .get(`${SERVER_URL}/gboard/read`, { params: this.form })
        .then((res) => {
          let today = new Date();
          let year = today.getFullYear();
          let month = ("0" + (today.getMonth()+1)).slice(-2);
          let day = ("0" + today.getDate()).slice(-2);
          today = year + '-' + month + '-' + day;

          for (let i = 0; i < res.data.list.length; i++) {
            const datetime = res.data.list[i].date;
            if(datetime.split(' ')[0] == today) {
              res.data.list[i].date = datetime.split(' ')[1].slice(0, 5)
            } else {
              res.data.list[i].date = datetime.split(' ')[0]
            }
          }

          this.list = res.data.list.sort((a, b) => {
            return b.bId - a.bId;
          });
          // // console.log(this.list.length);
        });
    },

    fnAdd() {
      // this.$router.push('./BoardWrite');
      this.active = 2;
    },

    fnView(num) {
      // this.body.num = num;
      //this.$router.push({ path: './gBoardRead', query: { bId: num } }); //추가한 상세페이지 라우터
      this.child.bId = num;
      this.active=3;
    },
    rowClick(record) {
      // 'record' will be the row data from items
      // `index` will be the visible row number (available in the v-model 'shownItems')
      // // console.log(record.bId);
      this.fnView(record.bId);
    },
    fnSearch() {
      //검색
      // // console.log(this.type);
      // // console.log(this.word);
      this.fnGetList();
    },
    fnPage(n) {
      //페이징 이
      if (this.page != n) {
        this.page = n;
        this.fnGetList();
      }
    },
  },
};
</script>

<style scoped>
.searchWrap {
  width: 80%;
  border-radius: 5px;
  text-align: center;
  padding: 20px 0;
  margin-bottom: 40px;
}
.searchWrap input {
  width: 60%;
  height: 36px;
  border-radius: 3px;
  padding: 0 10px;
  border: 1px solid #888;
}
.searchWrap .btnSearch {
  display: inline-block;
  height: 36px;
}
.tbList th {
  border-top: 1px solid #888;
}
.tbList th,
.tbList td {
  border-bottom: 1px solid #eee;
  padding: 5px 0;
}
.tbList td.txt_left {
  text-align: left;
}
.btnRightWrap {
  text-align: right;
  margin: 10px 0 0 0;
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

@font-face {
    font-family: 'GongGothicMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/GongGothicMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>
