<template>
  <div>
    <center>
      <h2 style="font-weight:bold ; margin-top:30px">나의 스터디</h2>
    </center>

    <div class="row">
      <b-col
        class="col-12 col-sm-12 col-md-6 col-lg-4"
        v-for="(group, idx) in myGroups.slice().reverse()"
        :key="idx"
        :group="group"
      >
        <b-card
          @click="goGroupPage(group)"
          class="my-3"
          style="min-height:400px; max-height:400px; min-width:350px; overflow:hidden"
        >
         <div style="max-height:200px; min-height:200px; width:100%; overflow:hidden; ">
              <b-card-img :src="group.img" alt="Image" top></b-card-img>
            </div>
            <b-card-text style="padding:10px; overflow:hidden">

              <div>
                <div
                  style="font-size:23px; font-weight:bold; color:#222; margin-right:5px; margin-bottom:10px; white-space: nowrap; overflow: hidden; text-overflow:ellipsis;">
                  {{group.groupName}}</div>
              </div>
              <h5 style="font-size:14px;  color:#000000; margin-right:5px"><i style="margin-right:5px"
                  class="now-ui-icons business_badge"></i> {{group.nickname}}</h5>
              <span class="badge badge-info"
                style="font-size:12px; font-weight:bold; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons users_single-02"></i> {{ group.max }}</span>
              <!-- <b>온라인 여부: {{ group.onoff }}</b><br> -->
              <!-- <b>모집기간: {{ group.edate }}</b><br> group.nickname-->
              <span class="badge badge-success" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_pin"></i> {{ group.region }}</span>
              <span class="badge badge-primary" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons location_bookmark"></i> {{ group.category }}</span>
              <span class="badge badge-danger" style="font-size:12px; color:#FFFFFF; margin-right:5px"><i
                  class="now-ui-icons ui-1_calendar-60" id="dday"></i> ~{{ group.edate }}</span>
              <!-- <b>모임 주기: {{ group.cycle }}</b> -->
            </b-card-text>
            <template #footer>
              <!-- <small class="text-muted">Last updated 3 mins ago</small> -->
            </template>
        </b-card>
      </b-col>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import axios from 'axios';
//   import card from '@/components/Cards/Card'
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data() {
    return {
      params: {
        type: '',
        word: '',
      },
      groups: Object,
      myGroups: Object,
      permission: '',
    };
  },
  components: {
    // card
  },
  computed: {
    ...mapState(['loginStatus', 'myStudyNum']),
  },
  created() {
    this.myGroupList();
  },
  methods: {
    myGroupList: function() {
      axios
        .get(`${SERVER_URL}/group/member/apply/user/list`, {
          params: {
            email: this.loginStatus.email,
          },
        })
        .then((res) => {
          for (let i = 0; i < res.data.list.length; i++) {
            res.data.list[i].edate = res.data.list[i].edate.split(' ')[0];
          }
          this.myGroups = res.data.list;
          // this.$emit('studyNum',res.data.list.length);
          this.loginStatus.myStudyNum = res.data.list.length;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goGroupPage: function(myGroup) {
      this.$router.push({ path: './GroupPage', query: { gId: myGroup.gId } });
    },
  },
};
</script>
