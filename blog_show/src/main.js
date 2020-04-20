// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import VueAxios from "vue-axios";
import store from "./store";
import VueProgressBar from 'vue-progressbar';
import * as filters from './filter'
import mavonEditor from "mavon-editor";
import 'mavon-editor/dist/css/index.css'

import './assets/css/blog.css'
import './assets/js/iconfont'

Vue.config.productionTip = false;

Vue.use(ElementUI);

axios.defaults.baseURL = "/hsblog";
axios.defaults.withCredentials = true;
Vue.use(VueAxios, axios);

Vue.use(VueProgressBar, {
    color: 'rgb(64,158,255)',
    failedColor: '#874b4b',
    thickness: '2px',
    transition: {
        speed: '0.2s',
        opacity: '0.6s',
        termination: 300
    },
    autoRevert: true,
    location: 'top',
    inverse: false
});

Vue.use(mavonEditor);

router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
        if (store.state.currentUser !== null && store.state.currentUser) {
            next();
        } else {
            next({
                path: '/admin/login',
                query: {redirect: to.fullPath},
            })
        }
    } else {
        next();
    }
});

Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key])
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: {App},
    template: '<App/>'
}).$mount('#app')
