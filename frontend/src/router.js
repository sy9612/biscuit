import Vue from 'vue';
import Router from 'vue-router';
import Index from './pages/Index.vue';
import Landing from './pages/Landing.vue';
import Login from './pages/Login.vue';
import Profile from './pages/Profile.vue';
import GroupList from './pages/Group/GroupList.vue';
import GroupCreate from './pages/Group/GroupCreate.vue';
import GroupUpdate from './pages/Group/GroupUpdate.vue';
import GroupPage from './pages/GroupPage/GroupPage.vue';
import NoticeBoard from './pages/Board/NoticeBoard.vue';
import BoardList from './pages/Board/BoardList.vue';
import BoardRead from './pages/Board/BoardRead.vue';
import BoardUpdate from './pages/Board/BoardUpdate.vue';
import BoardWrite from './pages/Board/BoardWrite.vue';
import MainNavbar from './layout/MainNavbar.vue';
import MainFooter from './layout/MainFooter.vue';
import Join from './pages/Join.vue';
import MyPage from './pages/User/MyPage.vue';
import FindPw from './pages/FindPw.vue';
import test from './pages/ImageUpload.vue';
import SignUpForm from './pages/components/SignupForm.vue';
import ChatRoomList from './pages/Chat/ChatRoomList.vue';
import ChatRoom from './pages/Chat/ChatRoom.vue';

Vue.use(Router);

export default new Router({
  linkExactActiveClass: 'active',
  routes: [
    {
      path: '/test',
      name: 'test',
      components: { default: test },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/',
      name: 'index',
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/landing',
      name: 'landing',
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/login',
      name: 'login',
      components: { default: Login, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 }
      }
    },
    {
      path: '/profile',
      name: 'profile',
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/grouplist',
      name: 'grouplist',
      components: { default: GroupList, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/groupcreate',
      name: 'groupcreate',
      components: { default: GroupCreate, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/groupupdate',
      name: 'groupupdate',
      components: { default: GroupUpdate, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/grouppage',
      name: 'grouppage',
      components: { default: GroupPage, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 100 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/boardlist',
      name: 'boardlist',
      components: { default: BoardList, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/noticeboard',
      name: 'noticeboard',
      components: { default: NoticeBoard, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/boardread',
      name: 'boardread',
      components: { default: BoardRead, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/boardupdate',
      name: 'boardupdate',
      components: { default: BoardUpdate, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/boardwrite',
      name: 'boardwrite',
      components: { default: BoardWrite, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/join',
      name: 'join',
      components: { default: Join, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/mypage',
      name: 'mypage',
      components: { default: MyPage, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/findpw',
      name: 'findpw',
      components: { default: FindPw, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/signup',
      name: 'signup',
      components: { default: SignUpForm, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/chatroomlist',
      name: 'chatroomlist',
      components: { default: ChatRoomList, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 0 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/chatroom',
      name: 'chatroom',
      components: { default: ChatRoom },
      
    },
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
