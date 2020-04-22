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
import Redirect from "../components/common/Redirect";
import MoodManager from "../components/admin/mood/MoodManager";
import Mood from "../components/admin/mood/Mood";
import Mooding from "../components/admin/mood/Mooding";

Vue.use(Router);

export const constantRoutes = [
    {
        path: '/admin/login',
        name: 'Login',
        component: Login,
        meta: {
            requireAuth: false,
        }
    },
    {
        path: '/redirect/:path(.*)',
        component: Redirect,
    }
];

export const asyncRoutes = [
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
                    affix: true,
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
            {
                path: "moodmanager",
                name: "MoodManager",
                component: MoodManager,
                redirect: '/admin/mood',
                children: [
                    {
                        path: '/admin/mood',
                        name: 'Mood',
                        component: Mood,
                        meta: {
                            requireAuth: true,
                            title: '动态',
                        }
                    },
                    {
                        path: '/admin/mooding',
                        name: 'Mooding',
                        component: Mooding,
                        meta: {
                            requireAuth: true,
                            title: '写动态',
                        }
                    }
                ]
            }
        ]
    },
];

const createRouter = () => new Router({
    scrollBehavior: () => ({ y:0 }),
    routes: constantRoutes,
});

const router = createRouter();

export function resetRouter() {
    const newRouter = createRouter();
    router.matcher = newRouter.matcher;
}

export default router;
