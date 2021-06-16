<template>
  <div class="home">
    <h1 class=m-5>메인페이지입니당</h1>
    <GroupList />
  </div>
</template>

<script>
import GroupList from '@/components/Group/GroupList'

// @ is an alias to /src
export default {
  name: "Home",
  components: {
    GroupList
  }
};
</script>
