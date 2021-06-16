<template>
  <div class="searchWrap">
    <b-select name="type" v-model="type" style="width:80px">
      <b-select-option value="" selected="selected" disabled hidden
        >전체</b-select-option
      >
      <b-select-option value="nickname">작성자</b-select-option>
      <b-select-option value="title">제목</b-select-option>
      <b-select-option value="contents">내용</b-select-option>
    </b-select>
    <input type="text" v-model="word" @keyup.enter="fnSearch" />
    <b-button @click="fnSearch" class="btnSearch m-1">검색</b-button>
  </div>
</template>

<script>
export default {
  
}
</script>

<style>
.searchWrap {
  width: 100%;
  border-radius: 5px;
  text-align: center;
  padding: 20px 0;
  margin-bottom: 40px;
}
.searchWrap input {
  width: 60%;
  height: 36px;
  border-radius: 3px;
  padding: 0 10px;
  border: 1px solid #888;
}
.searchWrap .btnSearch {
  display: inline-block;
  height: 36px;
}
</style>