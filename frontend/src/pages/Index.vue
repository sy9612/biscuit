<template>

  <div>
    
    <div class="page-header clear-filter" filter-color="orange">
      <parallax
        class="page-header-image"
        style="background-image:url('img/header.jpg')"
      >
      </parallax>

      <div class="container">
          
        <div class="content-center brand">
          <img class="n-logo" src="img/bisWhite.png" alt="" />
          <h1 class="h1-seo" style="font-weight:bold">아직까지 혼자 공부하세요?</h1>
          <h3>비스킷에서 전국 {{groups.length}}개의 스터디를 찾고 함께 공부하세요</h3>

           <router-link class="navbar-brand" to="/grouplist">
        <b-button class="btn-primary btn-round btn-lg" data-aos="flip-left">지금 시작하기</b-button>
      </router-link>

      
          <div><i class="now-ui-icons arrows-1_minimal-down"></i></div>
        </div>
       
      </div>



      

      
    </div>


    <div class="main">
      <div class="section section-images">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="hero-images-container">
                <img src="img/Group 44.png" alt="" />
              </div>
            </div>

            <div class="col-md-6 ml-auto mr-auto text-center">
           
             
         
              <div class="hero-images-container-3 " >
                <h2 style="font-weight:bold; text-align:center" >비대면 시대에도 언제나 스터디,</h2>
                <div class="row">
                  <div class="col-md-6 ml-auto mr-auto text-center">
               
                <div class=" d-flex justify-content-center" data-aos="zoom-in"
     data-aos-easing="ease-out-cubic"
     data-aos-duration="1000">
                  <img class="col-md-12 col-sm-4 col-xs-2" src="img/path225.png" alt="" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>

    
  


<div><hr></div>
<br>




<landing></landing>


  </div>
</template>



<script>
import axios from 'axios'
import { Parallax } from '@/components';
import Landing from './Landing.vue';

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'index',
  bodyClass: 'index-page',
  components: {
    Parallax, 
    Landing
  },
  data() {
    return {
      groups: [],
    }
  },
  created() {
    this.groupList()
  },
  methods: {
    groupList: function() {
      axios
        .get(`${SERVER_URL}/group/list/`, {params: {type: '', word: ''}})
        .then((res) => {
          for (let i = 0; i < res.data.list.length; i++) {
            res.data.list[i].edate = res.data.list[i].edate.split(' ')[0];
          }
          this.groups = res.data.list;
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }
}
</script>

<style>

</style>
