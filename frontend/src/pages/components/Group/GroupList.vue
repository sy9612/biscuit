<template>
  <div>
    <div class="searchWrap">
      <b-input-group>
        <template #prepend>
          <b-select name="type" v-model="params.type">
            <b-select-option value="">전체</b-select-option>
            <b-select-option value="groupName">그룹 이름</b-select-option>
            <b-select-option value="groupDesc">그룹 설명</b-select-option>
            <b-select-option value="category">카테고리</b-select-option>
            <b-select-option value="region">지역</b-select-option>
          </b-select>
        </template>

        <b-form-input type="text" v-model="params.word" @keypress.enter="searchGroup"></b-form-input>
        <b-input-group-append type="submit">
          <b-button size="sm" text="Button" variant="info" @click="searchGroup">검색</b-button>
        </b-input-group-append>
      </b-input-group>
    </div>
    <b-button v-show="loginStatus" variant="primary" @click="goCreate">그룹생성</b-button>
    <b-card-group
      deck
      class="d-flex flex-row"
    >
      <b-col cols="3"
        v-for="(group, idx) in groups"
        :key="idx"
        :group="group"
      >
        <!-- @click="goDetail(group)" -->
          <!-- v-b-modal.group-13 -->
        <b-card
          @click="$bvModal.show(`group-${idx}`), getPermission(group.gId)"
          v-bind:title="group.groupName"
          img-src="https://picsum.photos/300/300/?image=41"
          img-alt="Image"
          img-top
        >
          <b-card-text>
            {{group.groupDesc}}
          </b-card-text>
          <template #footer>
            <small class="text-muted">Last updated 3 mins ago</small>
          </template>
        </b-card>
        <b-modal
          :id="'group-'+idx"
          size="xl"
          :title="''+group.groupName"
        >
          <div>
            <b-jumbotron bg-variant="info" text-variant="white" border-variant="dark">
              <template #header>{{ group.groupName }}</template>
              <b-button id="none" @click="applyGroup(group.gId)" pill variant="primary">그룹 참가하기</b-button>
              <b-button id="wait" pill variant="secondary">이미 참가신청을 하셨습니다</b-button>
              <b-button id="mine" pill variant="primary">당신의 스터디 그룹입니다</b-button>
              <b-button id="ban" pill variant="danger">당신은 이 스터디에서 추방되었습니다</b-button>
              <b-button v-if="!loginStatus.token" pill variant="secondary">스터디에 참여하려면 로그인 해주세요</b-button>

              <template #lead>
                <img src="" alt="">
                <p>모집인원: {{ group.max }}명</p>
                <p>온라인 여부: {{ group.onoff }}</p>
                <p>모집기간: {{ group.edate }}</p>
                <p>지역: {{ group.region }}</p>
                <p>스터디 주기: {{ group.cycle }}</p>
              </template>

              <hr class="my-4">

              <p>
                <!-- {{ group }} -->
                {{ group.groupDesc }}
              </p>
              <div v-if="group.nickname == loginStatus.nickname">
                <form action="" method="post" @submit.prevent="updateGroup(group.gId)">
                  <b-button type="submit" pill variant="warning">그룹 정보 수정하기</b-button>
                </form>
                <form action="" method="post" @submit="deleteGroup(group.gId)">
                  <b-button type="submit" pill variant="danger">그룹 삭제하기</b-button>
                </form>
              </div>
            </b-jumbotron>
          </div>
        </b-modal>
      </b-col>
    </b-card-group>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import axios from 'axios'
  const SERVER_URL = process.env.VUE_APP_SERVER_URL


  export default {
    data() {
      return {
        params: {
          type: '',
          word: '',
        },
        groups: Object,
        permission: '',
      }
    },
    computed: {
      ...mapState([
        'loginStatus',
      ]),
    },
    methods: {
      searchGroup: function() {
        // console.log('searchGroup')
        axios.get(`${SERVER_URL}/group/list/`, {params: this.params})
          .then(res => {
            this.groups = res.data.list
          })
          .catch(err => {
            console.log(err)
          })
      },
      getPermission: function(gId) {
        // console.log({gId: gId, nickname: this.loginStatus.nickname})
        axios.get(`${SERVER_URL}/group/member/apply/state`, {params: {gId: gId, nickname: this.loginStatus.nickname}})
          .then(res => {
            // console.log(`state:${res.data.state}`)
            const none = document.querySelector("#none")
            const wait = document.querySelector("#wait")
            const mine = document.querySelector("#mine")
            const ban = document.querySelector("#ban")
            if (res.data.state == 0 || res.data.state == 4) {
              none.style.display = "none"
              wait.style.display = "block"
              mine.style.display = "none"
              ban.style.display = "none"
            } else if (res.data.state == 1 || res.data.state == 3) {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "block"
              ban.style.display = "none"
            } else if (res.data.state == 5) {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "none"
              ban.style.display = "block"
            } else {
              none.style.display = "none"
              wait.style.display = "none"
              mine.style.display = "none"
              ban.style.display = "none"

            }
          })
          .catch(err => {
            console.log(err)
            const none = document.querySelector("#none")
            const wait = document.querySelector("#wait")
            const mine = document.querySelector("#mine")
            const ban = document.querySelector("#ban")
            none.style.display = "none"
            wait.style.display = "none"
            mine.style.display = "none"
            ban.style.display = "none"
          })
      },
      applyGroup: function(gId) {
        axios.post(`${SERVER_URL}/group/member/apply`,
        {gId: gId, email: this.loginStatus.email, nickname: this.loginStatus.nickname})
          .then(res => {
            // // console.log(res)
          })
          .catch(err => {
            console.log(err)
          })
      },
      goCreate: function() {
        // const data = [item]
        this.$router.push({path: './GroupCreate'})
      },
      goDetail: function(group) {
        this.$router.push({path: './GroupDetail', query: {gId: group.gId}})
      },
      updateGroup: function(gId) {
        this.$router.push({path: './GroupUpdate', query: { gId: gId}})
      },
      deleteGroup: function(gId) {
        axios.delete(`${SERVER_URL}/group/delete/`, {headers: {gId: gId}})
          .then(res => {
            // console.log(res)
            // console.log(this.idx)
            // 모달창 닫기
            let targetModal = document.querySelector('#group-'+this.idx)
            // console.log(targetModal)
            // 리스트에서 해당 스터디
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    created: function () {
      axios.get(`${SERVER_URL}/group/list/`, {params: this.params})
        .then(res => {
          this.groups = res.data.list
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
</script>

<style scoped>
</style>
