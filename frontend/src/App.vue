<template>
  <div id="app">
    <router-view name="header" v-on:logout="logout" v-on:clickNotify="clickNotify"/>
    <div class="wrapper">
      <router-view />
    </div>
    <router-view name="footer" />
  </div>
</template>
<script>
export default {
  methods: {
    logout() {
      // // console.log("로그아웃 테스트");
        if (localStorage.getItem('token') !== null) {
          localStorage.removeItem('token');
          localStorage.removeItem('id');
          localStorage.removeItem('name');
          localStorage.removeItem('admin');
        }
        this.$router.push('/');
        window.location.reload();
    },
    clickNotify(){
      // console.log("공지클릭테스트");
      window.location.reload();
    }
  },
};
</script>
