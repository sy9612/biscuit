<template>
  <div>
    <div class="">

        <div class="page-header clear-filter" filter-color="orange" style=" min-height:45vh" >
      <parallax
        class="page-header-image"

        style="background-image:url('img/bg5.jpg'); height:600px"
      >
      </parallax>
          
      <div class="content-center brand">
        <img class="n-logo" src="img/bisWhite.png" alt="" style="margin-top:100px" />
        <h2 class="h2-seo" style="font-weight:bold">공지사항</h2>

        <div class="row">
          <div class="col-md-1"></div>
          <div class="col-md-10">
          <b-input-group>
            <template #prepend>
              <b-select name="type" style="border-radius:10px" v-model="type">
                <b-select-option value="title">제목</b-select-option>
                <b-select-option value="name">작성자</b-select-option>
                <b-select-option value="contents">내용</b-select-option>
              </b-select>
            </template>
            <b-form-input
            style="border-radius:10px; color:white; background-color:#11111155"
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
                style="border-radius:10px; font-weight: bold"

                >
                검색<i class="now-ui-icons ui-1_zoom-bold" style="margin-left:10px"></i></b-button>
            </b-input-group-append>
          </b-input-group>
          </div>
          </div>
        </div>
      </div>
        <div class="content-center brand">
          <div class="container">
            <div class="listWrap" style="height:700px"><br>
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
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {mapState} from 'vuex'
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    //변수생성
    return {
      column: [
        // 'Bid', 'Title', 'Date'
        {
          key: 'bid',
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
      noticeFlag: 1,
      paginavigation: function() {
        //페이징 처리 for문 커스텀
        var pageNumber = [];
        var start_page = this.paging.start_page;
        var end_page = this.paging.end_page;
        for (var i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
    };
  },
   computed: {
    ...mapState([
      'loginStatus'
    ]),
  },
  created() {
    //페이지 시작하면은 자동 함수 실행
    this.fnGetList();
  },
  methods: {
    fnGetList() {
      this.form = {
        type: this.type,
        word: this.word,
        currentPage: '',
        noticeFlag: 1,
      };

      axios
        .get(`${SERVER_URL}/board/read`, { params: this.form })
        .then((res) => {
          let today = new Date()
          let year = today.getFullYear()
          let month = ("0" + (today.getMonth()+1)).slice(-2)
          let day = ("0" + today.getDate()).slice(-2)
          today = year + '-' + month + '-' + day
          for (let i = 0; i < res.data.list.length; i++) {
            const datetime = res.data.list[i].date;
            if(datetime.split(' ')[0] == today) {
              res.data.list[i].date = datetime.split(' ')[1].slice(0, 5)
            } else {
              res.data.list[i].date = datetime.split(' ')[0]
            }
          }

          this.list = res.data.list.sort((a, b) => {
            return b.bid - a.bid;
          });
          // console.log(this.list.length);
        });
    },

    fnAdd() {
      this.$router.push('./BoardWrite');
    },

    fnView(num) {
      // this.body.num = num;
      this.$router.push({ path: './BoardRead', query: { bId: num } }); //추가한 상세페이지 라우터
    },
    rowClick(record) {
      // 'record' will be the row data from items
      // `index` will be the visible row number (available in the v-model 'shownItems')

      this.fnView(record.bid);
    },
    fnSearch() {
      //검색
      // console.log(this.type);
      // console.log(this.word);
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
</style>
