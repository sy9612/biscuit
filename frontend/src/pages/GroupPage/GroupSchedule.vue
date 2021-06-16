<template>
  <div>
    <h2 style="margin-top:30px;" class="text-center">
      그룹 일정
    </h2>
    <center>
      <Schedule
        v-on:createSchedule="createSchedule"
        v-on:getSchedule="getSchedule"
        :scheduleType="groupSchedule"
        :items="items"
        :gId="gId"
      />
    </center>
  </div>
</template>
<script>
import Schedule from '../components/Schedule';
import { Button, FormGroupInput } from '@/components';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data: function() {
    return {
      groupSchedule: 'groupSchedule',
      list: [],
      items: [],
      color: '',
      cnt: 0,
    };
  },
  components: {
    Schedule,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  props: ['gId'],
  methods: {
    getSchedule() {
      this.form = { gId: this.gId };
      axios
        .get(`${SERVER_URL}/schedule/grouplist`, {
          params: this.form,
        })
        .then((res) => {
          this.list = res.data.list;
          this.items = [];
          this.insertItems();
        });
    },
    createSchedule(data) {
      axios
        .post(`${SERVER_URL}/schedule/create`, data)
        .then((response) => {
          if (response.data.success === 'success') {
            alert('일정 등록에 성공하셨습니다.');
            // axios
            //   .post(`${SERVER_URL}/notification/create`, {
            //     receiveEmail: email,
            //     sendEmail: this.loginStatus.email,
            //     isRead: 0,
            //     type: 'accept',
            //     contentId: this.gId,
            //     message:
            //       '[' + this.groupName + '] 스터디 가입이 승인되었습니다!',
            //     notiUrl: '/GroupPage?gId=' + this.gId,
            //   })
            //   .then((res) => {
            //     if (res.data.success) {
            //       // console.log('receiveEmail >>> ' + this.email);
            //       // alert('등록되었습니다.');
            //     } else {
            //       // console.log('알림 전송 실패');
            //     }
            //   })
            //   .catch((err) => {
            //     console.log(err);
            //   });
            this.getSchedule();
          } else alert('일정 등록에 실패하셨습니다.');
          //window.location.reload();
          //this.back();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    insertItems() {
      for (var i in this.list) {
        // this.items[i].id = 'e' + this.list[i].sId;
        // this.items[i].startDate = this.list[i].sdate;
        // this.items[i].endDate = this.list[i].edate;
        // this.items[i].title = this.list[i].title;
        if (this.list[i].sId % 10 === 0) {
          this.color = 'pink';
        } else if (this.list[i].sId % 10 === 1) {
          this.color = '';
        } else if (this.list[i].sId % 10 === 2) {
          this.color = 'purple';
        } else if (this.list[i].sId % 10 === 3) {
          this.color = 'orange';
        } else if (this.list[i].sId % 10 === 4) {
          this.color = 'mint';
        } else if (this.list[i].sId % 10 === 5) {
          this.color = 'lemon';
        } else if (this.list[i].sId % 10 === 6) {
          this.color = 'red';
        } else if (this.list[i].sId % 10 === 7) {
          this.color = 'green';
        } else if (this.list[i].sId % 10 === 8) {
          this.color = 'nureng';
        } else if (this.list[i].sId % 10 === 9) {
          this.color = 'ocean';
        }
        this.items.push({
          id: 'e' + this.list[i].sId,
          startDate: this.list[i].sdate,
          endDate: this.list[i].edate,
          title: this.list[i].title,
          url: '#',
          classes: this.color,
        });
      }
    },
  },
};
</script>
