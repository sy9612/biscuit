<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/bg11.jpg')"> </parallax>
      <div class="container">
        <h1 class="m-5">스터디 수정</h1>
        <b-form @submit.prevent="onEdit">
          <div class="d-flex">
            <p class="col-3">스터디 이름:</p>
            <fg-input
              class="no-border input-lg col-9"
              id="input-1"
              v-model="form.groupName"
              placeholder="스터디그룹 이름을 작명해주세요."
              required
            ></fg-input>
          </div>

          <div class="d-flex">
            <p class="col-3">스터디 인원제한:</p>
            <fg-input
              class="no-border input-lg col-9"
              id="input-2"
              v-model="form.max"
              placeholder="스터디 인원제한을 설정해주세요."
              required
            ></fg-input>
          </div>

          <div class="d-flex">
            <p class="col-3">지역:</p>
            <fg-input
              class="no-border input-lg col-9"
              id="input-3"
              v-model="form.region"
              :options="region"
              required
            ></fg-input>
          </div>

          <div class="d-flex">
            <p class="col-3">카테고리:</p>
            <div class="col-9">
              <b-form-radio-group
                v-model="form.category"
                :options="options"
                name="radio-validation"
              >
                <b-form-invalid-feedback>Please select one</b-form-invalid-feedback>
              </b-form-radio-group>
            </div>
          </div>

          <div class="d-flex">
            <p class="col-3">스터디원 모집 종료일:</p>
            <div class="col-9">
              <!-- <n-switch
                v-model="form.onoff"
                style="width:500px"
                on-text="ON"
                off-text="OFF"
              ></n-switch> -->
              <input class="col-8 no-border" v-model="edate" type="date" />
            </div>
          </div>

          <div>
            <p class="col-3">스터디 이미지:</p>
            <div class="col-9">
              <input type="file" id="img" />
            </div>
          </div>

          <div class="d-flex">
            <p class="col-3">스터디 설명:</p>
            <b-form-textarea
              id="input-6"
              v-model="form.groupDesc"
              placeholder="스터디그룹에 대해 설명해주세요."
              class="col-9"
              style="height: 500px"
            ></b-form-textarea>
          </div>
          <b-button class="mt-5" type="submit" variant="primary">Submit</b-button>
        </b-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { Switch, Button, Radio, FormGroupInput } from '@/components';
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
      params: {
        type: 'gId',
        word: this.$route.query.gId,
      },
      gId: this.$route.query.gId,
      form: Object,
      edate: Date,
      groupPagePath: '/grouppage?gId=' + this.$route.query.gId,
      region: [
        { text: '지역을 선택해주세요.', value: null },
        '온라인',
        '서울',
        '대전',
        '광주',
        '구미',
      ],
      category: [
        { text: '카테고리를 선택해주세요.', value: null },
        '한국사',
        '프로그래머',
        '농부',
        '어부',
        '광부',
      ],
      memberCount: Number,
    };
  },
  components: {
    [Button.name]: Button,
    [Radio.name]: Radio,
    [FormGroupInput.name]: FormGroupInput,
    [Switch.name]: Switch,
    [Option.name]: Option,
  },
  created() {
    this.loadInfo();
    this.getMemberCount();
  },
  updated() {
    // console.log('updated.edate : ' + this.edate);
  },
  mounted() {
    // this.edate = this.convertDate(this.form.edate)
    // console.log('edate : ' + this.edate);
  },
  methods: {
    onEdit: function() {
      // // console.log("===update")
      const item = {
        max: this.form.max,
        edate: this.edate,
        groupName: this.form.groupName,
        groupDesc: this.form.groupDesc,
        img: this.form.img,
        category: this.form.category,
        region: this.form.region,
        onoff: this.form.onoff,
        groupFlag: this.form.groupFlag,
        cycle: this.form.cycle,
        gId: this.form.gId,
      };
      if (this.memberCount > item.max) {
        // console.log("max: " + item.max + " mcount: " +this.memberCount);
        alert("인원제한은 현재인원보다 많아야합니다")
        }
      else{
        const frm = new FormData();
      var img = document.getElementById('img');
      if (img.files.length != 0) {
          frm.append('file', img.files[0]);
          axios
            .post(`${SERVER_URL}/file/upload/`, frm)
            .then((res) => {
              item['img'] = SERVER_URL + '/file/read/' + res.data.message;
              // console.log(item);

              //DB수정 (데이터 용량 관리 차원에서 수정 권유)
              axios
                .put(`${SERVER_URL}/group/update/`, item)
                .then((res) => {
                  // console.log(res);
                  this.$router.push({ path: this.groupPagePath });
                })
                .catch((err) => {
                  console.log(err);
                });
            })
            .catch((err) => {
              console.log(err);
            });
        
      } else {
        //DB에 저장 (수정권유)
        axios
          .put(`${SERVER_URL}/group/update/`, item)
          .then((res) => {
            // console.log(res);
            this.$router.push({ path: this.groupPagePath });
          })
          .catch((err) => {
            console.log(err);
          });
      }
      }
    },
    getMemberCount: function() {
      axios
        .get(`${SERVER_URL}/group/member/count`, {
          params: {
            gId: this.gId,
          },
        })
        .then((res) => {
          this.memberCount = res.data.memberCount;
          // console.log('memberCount : ' + this.memberCount);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    convertDate(date) {
      var moment = require('moment');
      return moment(date).format('YYYY-MM-DD');
    },
    loadInfo: function() {
      // console.log('loadInfo');
      axios
        .get(`${SERVER_URL}/group/list/`, { params: this.params })
        .then((res) => {
          // console.log(res.data.list[0]);
          this.form = res.data.list[0];
          this.edate = this.convertDate(this.form.edate);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped></style>
