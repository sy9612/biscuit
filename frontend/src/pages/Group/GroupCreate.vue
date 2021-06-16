<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/bg11.jpg')">
      </parallax>
      <div class="container"
        style="background-color:#11111133; margin-top:100px; padding-bottom:100px; margin-bottom:100px; border-radius:10px">
        <h2 class="md-4">스터디 생성</h2>
        <b-form @submit.prevent="onSubmit">
          <div class="row">
            <h5 class="col-4" style="text-align:right">스터디 분류</h5>
            <b-form-radio-group class="col-6" v-model="form.category" :options="options" name="radio-validation">
              <b-form-invalid-feedback>Please select one</b-form-invalid-feedback>
            </b-form-radio-group>
          </div>
          <div class="row">
            <h5 class="col-4" style="text-align:right">스터디 이름</h5>
            <fg-input class="no-border input-md col-6" id="input-1"
              style="background-color:white; border-radius:30px;  margin-left:15px" v-model="form.groupName"
              placeholder="스터디그룹 이름을 작성해주세요." required></fg-input>
          </div>
          <div class="row">
            <h5 class="col-4" style="text-align:right">스터디 인원</h5>
            <fg-input class="no-border input-md col-6" id="input-2" type="number"
              style="background-color:white; border-radius:30px;  margin-left:15px" v-model="form.max"
              placeholder="스터디 인원제한을 설정해주세요." required></fg-input>
          </div>
          <div class="row">
            <h5 class="col-4" style="text-align:right;">스터디 지역</h5>
            <fg-input class="no-border input-md col-6" id="input-3"
              style="background-color:white; border-radius:30px;  margin-left:15px" v-model="form.region" required>
            </fg-input>
          </div>
          <div class="row">
            <h5 class="col-4" style="text-align:right">스터디 모집 마감일</h5>
            <input :min="today" class="no-border input col-4"
              style="border-radius:30px; margin-left:15px; color:white; background-color:#88888855;"
              v-model="form.edate" type="date" name="" id="">
          </div>
          <div class="row">
            <h5 class="col-4" style="text-align:right">스터디 이미지</h5>
            <input type="file" id="img" style="margin-left:30px">
          </div>
          <div class="row">
            <h5 class="col-md-4" style="text-align:right">스터디 설명</h5>
            <b-form-textarea id="input-6" v-model="form.groupDesc" placeholder="스터디그룹에 대해 설명해주세요." class="col-md-6"
              style="min-height:250px; margin-left:15px; color:#222222; background-color:white; padding:10px; font-size:110%">
            </b-form-textarea>
          </div>
          <div class="row"></div>
          <b-button type="submit" variant="primary">스터디 만들기</b-button>
        </b-form>
      </div>
    </div>
  </div>
</template>

<style>
.form-control, .el-date-picker .el-input .el-input__inner, .form-group .el-input__inner{
  color:#ffffff;
}

</style>

<script>
  import axios from 'axios'
  import {Switch, Button, Radio, FormGroupInput}from '@/components'
  const SERVER_URL = process.env.VUE_APP_SERVER_URL;

  export default {
    data() {
      return {
        options: [
          { text: '취업', value: '취업' },
          { text: '수능', value: '수능' },
          { text: '자격증', value: '자격증' },
          { text: '공무원', value: '공무원' },
          { text: '취미', value: '취미' },
          { text: '기타', value: '기타' },
        ],
        form: {
          max: '',
          edate: '',
          email: '',
          nickname: '',
          groupName: '',
          groupDesc: '',
          category: '',
          region: '',
          onoff: ''
        },
        today : '',
        // region:[{text:'지역을 선택해주세요.',value:null},'온라인','서울','대전','광주','구미'],
        // category:[{text:'카테고리를 선택해주세요.',value:null},'취업','자격증','공무원','','취미','기타']
      }
    },
    components: {
      [Button.name]: Button,
      [Radio.name]: Radio,
      [FormGroupInput.name]: FormGroupInput,
      [Switch.name]: Switch,
      [Option.name]: Option,
    },
    created() {
      this.setToday();
    },
    methods: {
      onSubmit: function() {
        const item = {
          max: this.form.max,
          edate: this.form.edate,
          email: localStorage.getItem("email"),
          nickname: localStorage.getItem("nickname"),
          groupName: this.form.groupName,
          groupDesc: this.form.groupDesc.toString(),
          category: this.form.category,
          region: this.form.region
        }
        const frm = new FormData()
        var img = document.getElementById("img")
        if (img.files.length != 0) {
          frm.append('file', img.files[0])
          axios.post(`${SERVER_URL}/file/upload/`, frm)
            .then(res => {
              // console.log(res.data.message)
              // item.push({img: SERVER_URL + "/file/read/" + res.data.message})
              item["img"] = SERVER_URL + "/file/read/" + res.data.message
              
              // DB에 저장
              axios.post(`${SERVER_URL}/group/create/`, item)
                .then(res => {
                  // console.log(res)
                  this.$router.push({ path: './grouplist'});
                })
                .catch(err => {
                  console.log(err)
                })
            })
            .catch(err => {
              console.log(err)
            })
        } else {
          // DB에 저장
          axios.post(`${SERVER_URL}/group/create/`, item)
            .then(res => {
              // console.log(res)
              this.$router.push({ path: './grouplist'});
            })
            .catch(err => {
              console.log(err)
            })
        }
      },
      setToday(){
        var date = new Date(); 
        var year = date.getFullYear(); 
        var month = new String(date.getMonth()+1); 
        var day = new String(date.getDate()); 

        // 한자리수일 경우 0을 채워준다. 
        if(month.length == 1){ 
          month = "0" + month; 
        } 
        if(day.length == 1){ 
          day = "0" + day; 
        } 
        this.today = year + '-' + month + '-' + day;
      }
    }
  }
</script>


<style scoped>
.row{
  margin-top:15px;
}

h5{
  margin-top:5px;
}

input{
  color:white;
}

.form-control{
  color:white;
}
</style>


