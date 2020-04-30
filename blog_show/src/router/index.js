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
import MessageManager from "../components/admin/message/MessageManager";
import Layout from "../components/layout/Layout";
import Index from "../components/layout/index/Index";
import Page404 from '../components/error/404';
import Blog from "../components/layout/blog/Blog";
import TypeAndTagShow from "../components/layout/type/TypeAndTagShow";
import TypeShow from "../components/layout/type/TypeShow";
import TagShow from "../components/layout/type/TagShow";
import Message from "../components/layout/message/Message";
import MoodShow from "../components/layout/mood/MoodShow";
import ArchiveShow from "../components/layout/archive/ArchiveShow";

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
    },
    {
        path: '/404',
        name: '404',
        component: Page404,
    },
    {
        path: '/',
        name: 'Layout',
        redirect: '/index',
        component: Layout,
        children: [
            {
                path: '/index',
                name: 'Index',
                component: Index,
                meta: {
                    title: '首页',
                    affix: true,
                },
            },
            {
                path: '/blog/:id',
                name: 'Blog',
                component: Blog,
                meta: {
                    title: '博客',
                }
            },
            {
                path: 'typeandtag',
                name: 'TypeAndTagShow',
                component: TypeAndTagShow,
                redirect: '/type',
                children: [
                    {
                        path: '/type',
                        name: 'TypeShow',
                        component: TypeShow,
                        meta: {
                            title: '分类'
                        }
                    },
                    {
                        path: '/tag',
                        name: 'TagShow',
                        component: TagShow,
                        meta: {
                            title: '标签'
                        }
                    },
                ]
            },
            {
                path: '/message',
                name: 'Message',
                component: Message,
                meta: {
                    title: '留言'
                }
            },
            {
                path: '/mood',
                name: 'MoodShow',
                component: MoodShow,
                meta: {
                    title: '动态'
                }
            },
            {
                path: '/archive',
                name: 'ArchiveShow',
                component: ArchiveShow,
                meta: {
                    title: '归档',
                }
            }
        ]
    },
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
                    title: '主页',
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
                    },
                ]
            },
            {
                path: 'message',
                name: 'MessageManager',
                component: MessageManager,
                meta: {
                    requireAuth: true,
                    title: '留言'
                }
            }
        ]
    },
];

export const errorRoutes = [
    {
        path: '*',
        redirect: '/404',
        hidden: true,
    }
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
