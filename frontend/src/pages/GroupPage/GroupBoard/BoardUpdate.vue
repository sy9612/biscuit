<template>
  <div>
    <div>
      <div class="content-center brand">
        <h3 style="font-size: 30px; margin-top:30px;margin-bottom:10px;font-weight:bold; text-align: center;">스터디 게시글 수정</h3>
        <div>
          <br><br>
          <center>
          <div class="UpdateWrap" style="width: 80%">
            <form>
              <table class="tbAdd" width="100%">
                <colgroup>
                  <col width="20%" />
                  <col width="80%" />
                </colgroup>
                <tr>
                  <th>제목</th>
                  <td>
                    <!-- v-model 양뱡향데이터전송으로 상세 데이터 넣어준다 -->
                    <input type="text" v-model="title" ref="title" />
                  </td>
                </tr>
                <tr>
                  <th>내용</th>
                  <td><textarea v-model="contents" ref="contents"  @input="counting()" ></textarea></td>
                </tr>
              </table>
            </form>
          </div>

          <div class="btnWrap">
            <b-button @click="fnList" class="btnList m-1">취소</b-button>
            <b-button @click="fnModProc" class="btnReset m-1">확인</b-button>
          </div>
          </center>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  props: {
    detail : {},
  },
  data() {
    return {
      temptitle: '',
      gId : this.$props['detail'].gId,
      bId: this.$props['detail'].bId,
      email: this.$props['detail'].email,
      title: this.$props['detail'].title,
      contents: this.$props['detail'].contents,
      noticeFlag: this.$props['detail'].noticeFlag,
      date: this.$props['detail'].date,
      category: this.$props['detail'].category,
      count : 0,
    };
  },
  created() {
     this.count = this.$props['detail'].contents.length;
  },
  mounted() {
    //최초 로딩 시 실행

  },
  methods: {
    fnList() {
      this.$emit('updatebtn',0);
      return;
    },
    fnModProc() {
      this.temptitle = this.title.replace(/ /g, '');
      if (!this.temptitle || !this.temptitle.trim()) {
        alert('제목을 입력해 주세요');
        this.$refs.title.focus(); //방식으로 선택자를 찾는다.
        return;
      }
      if (!this.contents || !this.contents.trim()) {
        alert('내용을 입력해 주세요');
        this.$refs.contents.focus(); //방식으로 선택자를 찾는다.
        return;
      }

      this.form = {
        title: this.title,
        contents: this.contents.length>5000? this.contents.substr(0,5000):this.contents,
        noticeFlag: this.noticeFlag,
        category: this.category,
        bId: this.bId,
        email: this.email,
        gId : this.gId,
      };

      this.$axios
        .put(`${SERVER_URL}/gboard/update`, this.form)
        .then((res) => {
          if (res.data.success) {
            alert('수정되었습니다.');
            // this.fnView();
            this.fnList();
            return;
          } else {
            alert('실행중 실패했습니다.\n다시 이용해 주세요');
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    counting(){
      this.count = this.contents.length;
      if(this.count >5000){
        this.contents = this.contents.substr(0,5000);
      }
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
}
.tbAdd td input {
  width: 100%;
  min-height: 30px;
  box-sizing: border-box;
  padding: 0 10px;
}
.tbAdd td textarea {
  width: 100%;
  min-height: 300px;
  padding: 10px;
  box-sizing: border-box;
}
.btnWrap {
  text-align: center;
  margin: 20px 0 0 0;
}
</style>
