<template>
  <div>
    <h1 class="m-5">그룹 정보 수정</h1>
    <b-form @submit.prevent="onEdit">
      <b-form-group id="input-group-1" label="스터디 이름:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="form.groupName"
          placeholder="스터디그룹 이름을 작명해주세요."
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="그룹 인원제한:"
        label-for="input-2"
      >
        <b-form-input
          id="input-2"
          v-model="form.max"
          placeholder="그룹 인원제한을 설정해주세요."
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="지역:" label-for="input-3">
        <b-form-select
          id="input-3"
          v-model="form.region"
          :options="region"
          required
        ></b-form-select>
      </b-form-group>

      <b-form-group id="input-group-4" label="카테고리:" label-for="input-4">
        <b-form-select
          id="input-4"
          v-model="form.category"
          :options="category"
          required
        ></b-form-select>
      </b-form-group>

      <b-form-group
        id="input-group-5"
        label="스터디원 모집 마감일:"
        label-for="input-5"
      >
        <b-form-datepicker
          id="input-5"
          v-model="form.edate"
          required
        ></b-form-datepicker>
      </b-form-group>

      <b-form-group id="input-group-6" label="스터디 설명:" label-for="input-6">
        <b-form-input
          id="input-6"
          v-model="form.groupDesc"
          placeholder="스터디그룹에 대해 설명해주세요."
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
  import axios from 'axios'
  const SERVER_URL = process.env.VUE_APP_SERVER_URL

  export default {
    data() {
      return {
        params: {
          type: 'gId',
          word: this.$route.query.gId,
        },
        form: Object,
        region:[{text:'지역을 선택해주세요.',value:null},'온라인','서울','대전','광주','구미'],
        category:[{text:'카테고리를 선택해주세요.',value:null},'한국사','프로그래머','농부','어부','광부']
      }
    },
    methods: {
      onEdit: function() {
        // 일단 로그인여부는 구현 ㄴ
        const item = {
          max: this.form.max,
          edate: this.form.edate,
          groupName: this.form.groupName,
          groupDesc: this.form.groupDesc,
          img: "null",
          category: this.form.category,
          region: this.form.region,
          onoff: this.form.onoff,
          groupFlag: this.form.groupFlag,
          cycle: this.form.cycle,
          gId: this.form.gId
        }
        // const data = [item]
        axios.put(`${SERVER_URL}/group/update/`, item)
          .then(res => {
            // console.log(res)
            this.$router.push({ path: './' });
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    created: function () {
      axios.get(`${SERVER_URL}/group/list/`, {params: this.params})
        .then(res => {
          // console.log(res.data.list[0])
          this.form = res.data.list[0]
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
</script>

<style scoped>
</style>
