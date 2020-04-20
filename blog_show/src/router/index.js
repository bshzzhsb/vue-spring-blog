import Vue from 'vue'
import Router from 'vue-router'
import Register from "../components/admin/Register";
import Login from "../components/admin/Login";
import AdminHome from "../components/admin/home/AdminHome"
import Admin from "../components/admin/Admin";
import BlogManager from "../components/admin/blog/BlogManager";
import Blogging from "../components/admin/blogging/Blogging";
import TypeTable from "../components/admin/type/TypeTable";
import TypeAndTag from "../components/admin/type/TypeAndTag";
import TagTable from "../components/admin/type/TagTable";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/admin/login',
            name: 'Login',
            component: Login,
        },
        {
            path: '/admin/register',
            name: 'Register',
            component: Register,
            meta: {
                requireAuth: true,
            },
        },
        {
            path: '/admin',
            name: 'Admin',
            redirect: '/admin/home',
            component: Admin,
            children: [
                {
                    path: 'home',
                    name: 'AdminHome',
                    component: AdminHome,
                    meta: {
                        requireAuth: true,
                        title: '首页',
                    },
                },
                {
                    path: 'blog',
                    name: 'BlogManager',
                    component: BlogManager,
                    meta: {
                        requireAuth: true,
                        title: '博客',
                    },
                },
                {
                    path: 'blogging',
                    name: 'Blogging',
                    component: Blogging,
                    meta: {
                        requireAuth: true,
                        title: '写博客',
                    },
                },
                {
                    path: 'typeandtag',
                    name: 'TypeAndTag',
                    component: TypeAndTag,
                    redirect: '/admin/type',
                    children: [
                        {
                            path: '/admin/type',
                            name: 'TypeTable',
                            component: TypeTable,
                            meta: {
                                requireAuth: true,
                                title: '分类'
                            }
                        },
                        {
                            path: '/admin/tag',
                            name: 'TagTable',
                            component: TagTable,
                            meta: {
                                requireAuth: true,
                                title: '标签'
                            }
                        },
                    ]
                },
            ]
        },
    ]
})
