<template>
  <div>
    <div v-if="isLoading === false" class="loading">
      <b-spinner
        class="my-5"
        style="width: 10rem; height: 10rem; border: 1em solid currentColor; border-right-color: transparent;"
        label="Large Spinner"
      ></b-spinner>
      <h2>로딩 중 ...</h2>
    </div>
    <div v-else>
      <p></p>
      <ul class="list-group">
        <li
          class="list-group-item list-group-item-action"
          v-for="item in applys"
          v-bind:key="item.mId"
        >
          <div 
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              {{ item.nickname }}
            </span>
            <span>
              신청합니다
            </span>
            <div>
              <b-button @click="accept(item.mId)">
                승인
              </b-button>
              <b-button @click="deny(item.mId)">
                거절
              </b-button>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      isLoading: false,
      applys: Object,
    };
  },
  created() {
    this.applyList();
  },
  props: {
    gId: String,
  },
  methods: {
    applyList: function() {
      // console.log('gId : ' + this.gId);
      axios
        .get(`${SERVER_URL}/group/member/apply/group/list`, {
          params: {
            gId: this.gId,
          },
        })
        .then((res) => {
          this.applys = res.data.list;
        });
      this.isLoading = true;
    },
    accept: function(mid) {
      axios
        .put(`${SERVER_URL}/group/member/accept`, {
          mId: mid,
        })
        .then((res) => {
          if (res.data.success == 'success') {
            this.applyList();
          }
        });
    },
    deny: function(mid) {
      axios
        .put(`${SERVER_URL}/group/member/deny`, {
          mId: mid,
        })
        .then((res) => {
          if (res.data.success == 'success') {
            this.applyList();
          }
        });
    },
  },
};
</script>
