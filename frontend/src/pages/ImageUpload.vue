<template>
  <div>
    <form @submit.prevent="upload">

      <input type="file" id="file">
      <button type="submit">upload</button>
    </form>
    <p
      v-for="(img, idx) in imgs"
      :key="idx"
      :img="img"
      alt="">
      <img :src="img.url" alt="">
      <!-- <img src="http://localhost:8877/a407/file/read/20210209T131738323.jpg" alt=""> -->
    </p>
  </div>
</template>

<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data() {
    return {
      imgs: Array,
    }
  },
  methods: {
    upload: function() {
      const frm = new FormData()
      var file = document.getElementById("file")
      // // console.log(file.files[0])
      frm.append('file', file.files[0])
      // // console.log(frm)

      axios.post(`${SERVER_URL}/file/upload/`, frm 
      // {
      //   headers: {
      //     'Content-Type': 'multipart/form-data'
      //   }
      // }
      )
        .then(res => {
          // // console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      },
      getImg: function() {
        axios.get(`${SERVER_URL}/file/read/`)
          .then(res => {
            // // console.log(res)
            this.imgs = res.data
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    created() {
      this.getImg()
    }
  }
</script>

<style>

</style>