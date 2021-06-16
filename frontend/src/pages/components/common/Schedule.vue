<template>
  <div id="app">
    <div class="calendar-controls">
      <div v-if="message" class="notification is-success">{{ message }}</div>
      <div style="margin-top:10px;margin-bottom:10px">
        <b-button v-b-modal.modal-1 style="margin-left:690px">일정 추가</b-button>

        <b-modal ref="create-modal" id="modal-1" title="일정 추가" hide-footer>
          <div class="box">
            <div class="field">
              <label class="label">Title</label>
              <div class="control">
                <!-- <input v-model="newItemTitle" class="input" type="text" /> -->
                <b-form-input v-model="newItemTitle" style="width:50%"></b-form-input>
              </div>
            </div>

            <div class="field">
              <label class="label">Start date</label>
              <div class="control">
                <!-- <input v-model="newItemStartDate" class="input" type="date" /> -->
                <b-form-datepicker id="example-datepicker" v-model="newItemStartDate" class="mb-2"></b-form-datepicker>
              </div>
            </div>

            <div class="field">
              <label class="label">End date</label>
              <div class="control">
                <!-- <input v-model="newItemEndDate" class="input" type="date" /> -->
                <b-form-datepicker id="example-datepicker2" v-model="newItemEndDate" class="mb-2"></b-form-datepicker>
              </div>
            </div>

            <div class="field">
              <label class="label">Content</label>
              <div class="control">
                <b-form-textarea v-model="newItemContents" class="input" />
              </div>
            </div>

            <button class="snip1535" @click="addSchedule"  style="margin-left:160px">
              추가
            </button>
          </div>
        </b-modal>
      </div>
    </div>
    <div class="calendar-parent">
      <calendar-view
        :items="items"
        :show-date="showDate"
        :time-format-options="{ hour: 'numeric', minute: '2-digit' }"
        :enable-drag-drop="false"
        :disable-past="disablePast"
        :disable-future="disableFuture"
        :show-times="showTimes"
        :date-classes="myDateClasses"
        :display-period-uom="displayPeriodUom"
        :display-period-count="displayPeriodCount"
        :starting-day-of-week="startingDayOfWeek"
        :class="themeClasses"
        :period-changed-callback="periodChanged"
        :current-period-label="useTodayIcons ? 'icons' : ''"
        :displayWeekNumbers="displayWeekNumbers"
        :enable-date-selection="true"
        :selection-start="selectionStart"
        :selection-end="selectionEnd"
        @date-selection-start="setSelection"
        @date-selection="setSelection"
        @date-selection-finish="finishSelection"
        @drop-on-date="onDrop"
        @click-date="onClickDay"
        @click-item="onClickItem"
        id="calendar"
      >
        <calendar-view-header
          slot="header"
          slot-scope="{ headerProps }"
          :header-props="headerProps"
          @input="setShowDate"
        />
      </calendar-view>
    </div>

    <div>
      <!-- <b-button v-b-modal.modal-1>Launch demo modal</b-button> -->

      <b-modal id="modal-2" title="일정" ref="detail-modal" hide-footer>
        <div v-if="readOnly">
        <b-icon icon="pencil" style="margin-left:430px" @click="changeReadOnly()"></b-icon><br/>
        <h2 style="text-align:center">{{detail.title}}</h2>
        {{detail.sdate}} ~ {{detail.edate}}<br/>

        <div id="detail-contents">{{detail.contents}}</div>
        </div>
        <div v-if="!readOnly">

          <div class="box">
            <div class="field">
              <label class="label">Title</label>
              <!-- <button @click="deleteSchedule()" style="margin-left:360px border">삭제</button> -->
              <b-button @click="deleteSchedule()" variant="light" style="margin-left:360px">삭제</b-button>
              <div class="control">
                <!-- <input v-model="detail.title" class="input" type="text" /> -->
                 <b-form-input v-model="detail.title" style="width:50%"></b-form-input>
              </div>
            </div>

            <div class="field">
              <label class="label">Start date</label>
              <div class="control">
                <!-- <input v-model="detail.sdate" class="input" type="date" /> -->
                 <b-form-datepicker id="example-datepicker" v-model="detail.sdate" class="mb-2"></b-form-datepicker>
              </div>
            </div>

            <div class="field">
              <label class="label">End date</label>
              <div class="control">
                <!-- <input v-model="detail.edate" class="input" type="date" /> -->
                <b-form-datepicker id="example-datepicker" v-model="detail.edate" class="mb-2"></b-form-datepicker>
              </div>
            </div>

            <div class="field">
              <label class="label">Content</label>
              <div class="control">
                <b-form-textarea v-model="detail.contents" class="input" />
              </div>
            </div>

            <button class="snip1535" @click="updateSchedule()">
              수정
            </button>
            <button class="snip1535" @click="changeReadOnly()" style="margin-left:80px">취소</button>
          </div>
        </div>
      </b-modal>
    </div>
  </div>
</template>
<script>
// Load CSS from the published version
require('vue-simple-calendar/static/css/default.css');
require('vue-simple-calendar/static/css/holidays-us.css');
// Load CSS from the local repo
//require("../../vue-simple-calendar/static/css/default.css")
//require("../../vue-simple-calendar/static/css/holidays-us.css")
import {
  CalendarView,
  CalendarViewHeader,
  CalendarMathMixin,
} from 'vue-simple-calendar'; // published version
//} from "../../vue-simple-calendar/src/components/bundle.js" // local repo
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  name: 'App',
  components: {
    CalendarView,
    CalendarViewHeader,
  },
  mixins: [CalendarMathMixin],
  props: ['scheduleType', 'items'],
  data() {
    return {
      /* Show the current month, and give it some fake items to show */
      showDate: this.thisMonth(1),
      message: '',
      startingDayOfWeek: 0,
      disablePast: false,
      disableFuture: false,
      displayPeriodUom: 'month',
      displayPeriodCount: 1,
      displayWeekNumbers: false,
      showTimes: true,
      selectionStart: null,
      selectionEnd: null,
      newItemTitle: '',
      newItemStartDate: '',
      newItemEndDate: '',
      newItemContents: '',
      useDefaultTheme: true,
      useHolidayTheme: true,
      useTodayIcons: false,
      schedule: {
        gid: '',
        email: localStorage.getItem('email'),
        sdate: '',
        edate: '',
        title: '',
        content: '',
        pgflag: '',
      },
      detail: '',
      form: '',
      delForm:'',
      sId: '',
      show_detail: false,
      readOnly: true,
    };
  },
  computed: {
    userLocale() {
      return this.getDefaultBrowserLocale;
    },
    dayNames() {
      return this.getFormattedWeekdayNames(this.userLocale, 'long', 0);
    },
    themeClasses() {
      return {
        'theme-default': this.useDefaultTheme,
        'holiday-us-traditional': this.useHolidayTheme,
        'holiday-us-official': this.useHolidayTheme,
      };
    },
    myDateClasses() {
      // This was added to demonstrate the dateClasses prop. Note in particular that the
      // keys of the object are `yyyy-mm-dd` ISO date strings (not dates), and the values
      // for those keys are strings or string arrays. Keep in mind that your CSS to style these
      // may need to be fairly specific to make it override your theme's styles. See the
      // CSS at the bottom of this component to see how these are styled.
      const o = {};
      const theFirst = this.thisMonth(1);
      const ides = [2, 4, 6, 9].includes(theFirst.getMonth()) ? 15 : 13;
      const idesDate = this.thisMonth(ides);
      o[this.isoYearMonthDay(idesDate)] = 'ides';
      o[this.isoYearMonthDay(this.thisMonth(21))] = [
        'do-you-remember',
        'the-21st',
      ];
      return o;
    },
  },
  mounted() {
    this.newItemStartDate = this.isoYearMonthDay(this.today());
    this.newItemEndDate = this.isoYearMonthDay(this.today());
    // console.log("처음값" + this.newItemStartDate);
    if (this.scheduleType === 'mySchedule') {
      this.getItem();
    }
    // this.schedule.email = localStorage.getItem('email');
    //// console.log("아이템" + this.items);
  },
  methods: {
    periodChanged() {
      // range, eventSource) {
      // Demo does nothing with this information, just including the method to demonstrate how
      // you can listen for changes to the displayed range and react to them (by loading items, etc.)
      //// console.log(eventSource)
      //// console.log(range)
    },
    thisMonth(d, h, m) {
      const t = new Date();
      return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0);
    },
    onClickDay(d) {
      this.selectionStart = null;
      this.selectionEnd = null;
      d;
      // this.message = ` ${d.toLocaleDateString()}`;
    },
    onClickItem(e) {
      //this.message = `You clicked: ${e.title}`;// console.log("dff  " + this.detail.sid);
      this.sId = e.id.substr(1);
      // this.detail.sdate = e.startDate;
      // this.detail.edate = e.endDate;
      // this.detail.title = e.title;
      // this.detail.content =
      this.form = { sId: this.sId };
      axios
        .get(`${SERVER_URL}/schedule/detail`, {
          params: this.form,
        })
        .then((res) => {
          this.readOnly =  true,
          this.detail = res.data.Schedule;
          this.show_detail = true;
          this.$refs['detail-modal'].show();
          this.detail.sdate = this.detail.sdate.substr(0,10);
          this.detail.edate = this.detail.edate.substr(0,10);
          // console.log("다음값" + this.detail.sdate);
        });
      
    },
    setShowDate(d) {
      // this.message = `Changing calendar view to ${d.toLocaleDateString()}`;
      this.showDate = d;
    },
    setSelection(dateRange) {
      this.selectionEnd = dateRange[1];
      this.selectionStart = dateRange[0];
    },
    finishSelection(dateRange) {
      this.setSelection(dateRange);
      // this.message = `You selected: ${this.selectionStart.toLocaleDateString()} -${this.selectionEnd.toLocaleDateString()}`;
    },
    onDrop(item, date) {
      this.message = `You dropped ${item.id} on ${date.toLocaleDateString()}`;
      // Determine the delta between the old start date and the date chosen,
      // and apply that delta to both the start and end date to move the item.
      const eLength = this.dayDiff(item.startDate, date);
      item.originalItem.startDate = this.addDays(item.startDate, eLength);
      item.originalItem.endDate = this.addDays(item.endDate, eLength);
    },
    addSchedule() {
      if (this.scheduleType === 'mySchedule') {
        this.clickTestAddItem();
      }
      this.$refs['create-modal'].hide();
    },
    clickTestAddItem() {
      this.schedule.sdate = this.newItemStartDate;
      this.schedule.edate = this.newItemEndDate;
      this.schedule.title = this.newItemTitle;
      this.schedule.contents = this.newItemContents;
      //   axios
      //     .post(`${SERVER_URL}/schedule/create`, this.schedule)
      //     .then((response) => {
      //       if (response.data.success === 'success') {
      //         alert('일정 등록에 성공하셨습니다.');
      //       } else alert('일정 등록에 실패하셨습니다.');
      //       //this.back();
      //     })
      //     .catch(function(error) {
      //       console.log(error);
      //     });
      this.$emit('createSchedule', this.schedule);
      // this.items.push({
      //   startDate: this.newItemStartDate,
      //   endDate: this.newItemEndDate,
      //   title: this.newItemTitle,
      //   id:
      //     'e' +
      //     Math.random()
      //       .toString(36)
      //       .substr(2, 10),
      // });
      // this.message = 'You added a calendar item!';
    },
    getItem() {
      this.$emit('getSchedule');
      //  this.insertItems();
    },
    changeReadOnly(){
      this.readOnly = !this.readOnly;
    },
    updateSchedule(){
      axios
        .put(`${SERVER_URL}/schedule/update`, this.detail, {
          headers: {
            'x-access-token': localStorage.getItem('token'),
          },
        })
        .then((response) => {
          if (response.data.success === 'success') {
            alert('정보 수정에 성공하셨습니다.');
            this.$emit('getSchedule');
            this.readOnly = !this.readOnly;
          } else alert('정보 수정에 실패하셨습니다.');
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    deleteSchedule(){
      
      if(confirm("해당 스케줄을 삭제하시겠습니까?")== true){
      axios
        .delete(`${SERVER_URL}/schedule/delete`, {
          params: this.form,
        })
        .then((res) => {
          if (res.data.success === 'success') {
            alert('삭제하였습니다.');
            this.$refs['detail-modal'].hide();
            this.$emit('getSchedule');
          }else{
            alert('실패');
          }
        });   
    }else{
      // console.log("d");
    }
    },
    // hoverItem(){
    //   document.getElementById("myP").style.cursor = "pointer"
    // }
  },
};
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=BenchNine:700);

.snip1535 {
  background-color: #c47135;
  border: none;
  color: #ffffff;
  cursor: pointer;
  display: inline-block;
  font-family: 'BenchNine', Arial, sans-serif;
  font-size: 1em;
  font-size: 22px;
  line-height: 1em;
  margin: 15px 40px;
  outline: none;
  padding: 12px 40px 10px;
  position: relative;
  text-transform: uppercase;
  font-weight: 700;
}
.snip1535:before,
.snip1535:after {
  border-color: transparent;
  -webkit-transition: all 0.25s;
  transition: all 0.25s;
  border-style: solid;
  border-width: 0;
  content: "";
  height: 24px;
  position: absolute;
  width: 24px;
}
.snip1535:before {
  border-color: #c47135;
  border-right-width: 2px;
  border-top-width: 2px;
  right: -5px;
  top: -5px;
}
.snip1535:after {
  border-bottom-width: 2px;
  border-color: #c47135;
  border-left-width: 2px;
  bottom: -5px;
  left: -5px;
}
.snip1535:hover,
.snip1535.hover {
  background-color: #c47135;
}
.snip1535:hover:before,
.snip1535.hover:before,
.snip1535:hover:after,
.snip1535.hover:after {
  height: 100%;
  width: 100%;
}

#calendar {
  margin-left: 30px;
  width: 800px;
  height: 500px;
}

#detail-contents{
  border: 1px solid gainsboro;
  height: 100px;
  margin-top: 10px;
}
</style>
