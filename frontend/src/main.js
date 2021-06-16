/*!

 =========================================================
 * Vue Now UI Kit - v1.1.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/now-ui-kit
 * Copyright 2019 Creative Tim (http://www.creative-tim.com)

 * Designed by www.invisionapp.com Coded by www.creative-tim.com

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */
import Vue from 'vue';
import App from './App.vue';
// You can change this import to `import router from './starterRouter'` to quickly start development from a blank layout.
import router from './router';
import store from "./store";
import NowUiKit from './plugins/now-ui-kit';
import {BootstrapVue, BootstrapVueIcons} from 'bootstrap-vue'
import axios from 'axios'; //axios 호출
import 'core-js'
import { CarouselCard, CarouselCardItem } from 'vue-carousel-card'
import 'vue-carousel-card/styles/index.css'


Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.config.productionTip = false;
// Vue.component(Button.name, Button);

Vue.prototype.$axios = axios;

Vue.use(NowUiKit);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
