<template>
  <div>
    <center>
      <!-- <div style="font-size: 30px; margin-top:30px;font-weight:bold"> -->
        <h2 style="margin-top:30px;">나의 일정</h2>
      <!-- </div> -->
    </center>
    <div>
      <!-- <n-checkbox  v-model="personal" style="margin-left: 180px ; color:#F96332"  @change="getSchedule">개인 일정</n-checkbox> -->
      <div style="margin-top:20px; margin-left:200px; margin-right:730px;margin-bottom:-20px">
      <div style="color:#F96332">
      개인 일정<input
        type="checkbox"
        v-model="personal"
        @change.prevent="getSchedule"
        style="margin-left:5px;"
      /></div>
      <drop-down
        tag="div"
        title="스터디 일정"
        style="margin-left: 80px; margin-top:-30px;"
      >
      <div class="dropdown-item" style="border-bottom :solid">
        <span style="font-weight:bold; font-size:13px">전체 스터디</span>
        <input
        type="checkbox"
        @change.prevent="allGroupChange"
        v-model = allGroupChecked
        style="margin-left:10px"
      />
      </div>
      <div class="dropdown-item" v-for="(item , idx) in myGroups" v-bind:item="item" v-bind:key="idx">
        {{item.groupName}}<input
        type="checkbox"
        @change.prevent="groupChange"
        v-model = checked[idx]
        style="margin-left:10px"
      /></div>
      </drop-down>
      </div>
    </div>
    <center style="margin-top:0px">
      <Schedule
        v-on:createSchedule="createSchedule"
        v-on:getSchedule="getSchedule"
        :scheduleType="mySchedule"
        :items="items"
        :GroupInfo="myGroups"
      />
    </center>
  </div>
</template>
<script>
import Schedule from '../../components/Schedule';
import { Button, FormGroupInput, Checkbox, DropDown } from '@/components';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data: function() {
    return {
      mySchedule: 'mySchedule',
      list: [],
      list2: [],
      items: [],
      color: '',
      cnt: 0,
      myGroups: Object,
      personal: true,
      checked:[],
      allGroupChecked:true,
    };
  },
  components: {
    Schedule,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    [Checkbox.name]: Checkbox,
    DropDown,
  },

mounted() {
   axios
        .get(`${SERVER_URL}/group/member/apply/user/list`, {
          params: {
            email: localStorage.getItem('email'),
          },
        })
        .then((res) => {
          this.myGroups = res.data.list;

          for (var i in this.myGroups) {
            this.checked[i] = true;
            // console.log("ㅎㅇㄹ" +this.checked[i]);
          }
          
          this.getSchedule();
        })
        .catch((err) => {
          console.log(err);
        });
},
  methods: {
    getSchedule() {
      this.items = [];
      if (this.personal) {
        
        this.form = { email: localStorage.getItem('email') };
        axios
          .get(`${SERVER_URL}/schedule/perlist`, {
            params: this.form,
          })
          .then((res) => {
            this.list = res.data.list;
            this.insertItems();
          });
      }

      axios
        .get(`${SERVER_URL}/group/member/apply/user/list`, {
          params: {
            email: localStorage.getItem('email'),
          },
        })
        .then((res) => {
          this.myGroups = res.data.list;

          for (var i in this.myGroups) {
            if(this.checked[i] === true){
            this.form2 = { gId: this.myGroups[i].gId };
            axios
              .get(`${SERVER_URL}/schedule/grouplist`, {
                params: this.form2,
              })
              .then((res) => {
                this.list2 = res.data.list;
                this.insertGroupItems();
                //this.myGroups[i].checked = true;
              });
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createSchedule(data) {
      axios
        .post(`${SERVER_URL}/schedule/create`, data)
        .then((response) => {
          if (response.data.success === 'success') {
            alert('일정 등록에 성공하셨습니다.');
            this.getSchedule();
          } else alert('일정 등록에 실패하셨습니다.');
          //window.location.reload();
          //this.back();
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    allGroupChange(){
      if(this.allGroupChecked == true){
        for (var i in this.myGroups) {
            this.checked[i] = true;
        }
      }else{
        for (var i in this.myGroups) {
            this.checked[i] = false;
        }
      }
      this.getSchedule();
    },
    groupChange(){
      var cnt = 0;
      for (var i in this.myGroups) {
        //// console.log("하이ㅋㅋ" + i);
        if(this.checked[i] == false){
          this.allGroupChecked = false;
          cnt++;
          break;
        }
      }
      if(cnt == 0){
        this.allGroupChecked = true;
      }
      this.getSchedule();
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

    insertGroupItems() {
      for (var i in this.list2) {
        // this.items[i].id = 'e' + this.list[i].sId;
        // this.items[i].startDate = this.list[i].sdate;
        // this.items[i].endDate = this.list[i].edate;
        // this.items[i].title = this.list[i].title;
        if (this.list2[i].sId % 10 === 0) {
          this.color = 'pink';
        } else if (this.list2[i].sId % 10 === 1) {
          this.color = '';
        } else if (this.list2[i].sId % 10 === 2) {
          this.color = 'purple';
        } else if (this.list2[i].sId % 10 === 3) {
          this.color = 'orange';
        } else if (this.list2[i].sId % 10 === 4) {
          this.color = 'mint';
        } else if (this.list2[i].sId % 10 === 5) {
          this.color = 'lemon';
        } else if (this.list2[i].sId % 10 === 6) {
          this.color = 'red';
        } else if (this.list2[i].sId % 10 === 7) {
          this.color = 'green';
        } else if (this.list2[i].sId % 10 === 8) {
          this.color = 'nureng';
        } else if (this.list2[i].sId % 10 === 9) {
          this.color = 'ocean';
        }
        this.items.push({
          id: 'e' + this.list2[i].sId,
          startDate: this.list2[i].sdate,
          endDate: this.list2[i].edate,
          title: this.list2[i].title,
          url: '#',
          classes: this.color,
        });
      }
    },
  },
};
</script>
