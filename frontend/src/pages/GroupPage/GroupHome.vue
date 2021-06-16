<template>
  <div id="group-page" class="container">
    <div id="group-info" class="row">
      <div id="group-picture" class="col-md-2">
        <img
          class="rounded-circle img-fluid img-raised"
          src="https://placekitten.com/300/300"
          alt="group profile img"
        />
      </div>
      <div id="group-profile" class="col-md-6">
        <p style="text-align: left; font-size: 40px; margin-left: 30px">
          {{ group.groupName }}
        </p>
        <p style="text-align: left; font-size: 14px; margin-left: 30px">
          카테고리 : {{ group.category }}
        </p>
        <p style="text-align: left; font-size: 14px; margin-left: 30px">
          {{ group.groupDesc }}
        </p>
        <div v-if="state == 3">
          <p style="text-align: left; font-size: 14px; margin-left: 30px">
            스터디 모집 종료일 : {{ group.edate }}
          </p>
          <p style="text-align: left; font-size: 14px; margin-left: 30px">
            스터디 인원 제한 : {{ group.max }}
          </p>
          <p style="text-align: left; font-size: 14px; margin-left: 30px">
            지역 : {{ group.region }}
          </p>
        </div>
      </div>
      <div v-if="state==3">
          <form action="" method="post" @submit.prevent="updateGroup(group.gId)">
                  <b-button type="submit" pill variant="warning">스터디 정보 수정하기</b-button>
                </form>
          
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
    name: 'group-home',
  data() {
    return {
      group: Object,
    };
  },
  created() {
    this.getGroup();
  },
  props: {
    gId: String,
    state: Number,
  },
  methods: {
    getGroup: function() {
      axios
        .get(`${SERVER_URL}/group/list/`, {
          params: {
            word: this.gId,
            type: 'gId',
          },
        })
        .then((res) => {
          this.group = res.data.list[0];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    
      updateGroup: function(gId) {
        this.$router.push({path: './GroupUpdate', query: { gId: gId}})
      },
  },
};
</script>
