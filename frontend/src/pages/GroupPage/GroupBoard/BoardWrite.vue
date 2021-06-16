<template>
  <div>
    <div style="height: 760px">
      <div class="content-center brand">
  
        <div class="container">
          <center class="h1-seo text-center" style="font-size: 30px; margin-top:30px;margin-bottom:10px;font-weight:bold ">스터디 게시글 작성</center>
          <!-- num 값에 따라 제목 변경 -->

          <div class="AddWrap">
            <form>
                <table class="tbAdd" width="100%">
                <colgroup>
                  <col width="20%"/>
                  <col width="80%"/>
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
                  <td><textarea v-model="contents" ref="contents" @input="counting()"></textarea></td>
                </tr>
              </table>
            </form>
          </div>

          <div class="btnWrap">
            <b-button @click="fnList" class="btnList m-1">목록</b-button>
            <b-button v-if="!num" @click="fnAddProc" type="submit" class="btnAdd m-1" >등록</b-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  props : ['gId'],
  data() {
    return {
      
      temptitle: '',
      board_code: 'news',
      title: '',
      contents: '',
      id: 'admin',
      body: this.$route.query,
      form: {},
      num: this.$route.query.num,
      count : 0,
    };
  },
  mounted() {
    //최초 로딩 시 실행
    // if (this.num) {
    //   // num 값이 있으면 상세 데이터 호출한다.
    //   this.fnGetView();
    // }
  },
  methods: {
    fnList() {
      this.$emit('readbtn',1);
      return;
    },
    fnAddProc() {
      this.form = {
        email: localStorage.getItem("email"),
        nickname: localStorage.getItem("nickname"),
        title: this.title,
        contents: this.contents.length>5000? this.contents.substr(0,5000):this.contents,
        gId : this.$props.gId,
      };
      if(this.form.title=='' || this.form.title.trim()==""){
        alert('작성된 게시글의 제목이 존재하지 않습니다.');
      }else if(this.form.contents==''|| this.form.contents.trim()==""){
        alert('작성된 게시글의 내용이 존재하지 않습니다.');
      }else{
        this.$axios
        .post(`${SERVER_URL}/gboard/create`, this.form)
        .then((res) => {
          if (res.data.success) {
            alert('등록되었습니다.');
            this.fnList();
          } else {
            alert('실행중 실패했습니다.\n다시 이용해 주세요');
          }
        })
        .catch((err) => {
          console.log(err);
        });
      }
      fnList();
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
