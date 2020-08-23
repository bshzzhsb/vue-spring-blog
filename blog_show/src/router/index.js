import Vue from 'vue'
import VueRouter from 'vue-router'

if (process.env.NODE_ENV === 'development') {
  Vue.use(VueRouter);
}

export const constantRoutes = [
  {
    path: '/admin/login',
    name: 'Login',
    component: () => import("../components/admin/Login"),
    meta: {
      requireAuth: false,
    }
  },
  {
    path: '/redirect/:path(.*)',
    component: () => import("../components/common/Redirect"),
  },
  {
    path: '/404',
    name: '404',
    component: () => import("../components/error/404"),
  },
  {
    path: '/',
    name: 'Layout',
    redirect: '/index',
    component: () => import("../components/layout/Layout"),
    children: [
      {
        path: '/index',
        name: 'Index',
        component: () => import("../components/layout/index/Index"),
        meta: {
          title: '首页',
          affix: true,
        },
      },
      {
        path: '/blog/:id',
        name: 'Blog',
        component: () => import("../components/layout/blog/Blog"),
        meta: {
          title: '博客',
        }
      },
      {
        path: 'typeandtag',
        name: 'TypeAndTagShow',
        component: () => import("../components/layout/type/TypeAndTagShow"),
        redirect: '/type',
        children: [
          {
            path: '/type',
            name: 'TypeShow',
            component: () => import("../components/layout/type/TypeShow"),
            meta: {
              title: '分类'
            }
          },
          {
            path: '/tag',
            name: 'TagShow',
            component: () => import("../components/layout/type/TagShow"),
            meta: {
              title: '标签'
            }
          },
        ]
      },
      {
        path: '/message',
        name: 'Message',
        component: () => import("../components/layout/message/Message"),
        meta: {
          title: '留言'
        }
      },
      {
        path: '/mood',
        name: 'MoodShow',
        component: () => import("../components/layout/mood/MoodShow"),
        meta: {
          title: '动态'
        }
      },
      {
        path: '/archive',
        name: 'ArchiveShow',
        component: () => import("../components/layout/archive/ArchiveShow"),
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
    component: () => import("../components/admin/Register"),
    meta: {
      requireAuth: true,
    },
  },
  {
    path: '/admin',
    name: 'Admin',
    redirect: '/admin/home',
    component: () => import("../components/admin/Admin"),
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: () => import("../components/admin/home/AdminHome"),
        meta: {
          requireAuth: true,
          title: '主页',
          affix: true,
        },
      },
      {
        path: 'blog',
        name: 'BlogManager',
        component: () => import("../components/admin/blog/BlogManager"),
        meta: {
          requireAuth: true,
          title: '博客',
        },
      },
      {
        path: 'blogging',
        name: 'Blogging',
        component: () => import("../components/admin/blogging/Blogging"),
        meta: {
          requireAuth: true,
          title: '写博客',
        },
      },
      {
        path: 'typeandtag',
        name: 'TypeAndTag',
        component: () => import("../components/admin/type/TypeAndTag"),
        redirect: '/admin/type',
        children: [
          {
            path: '/admin/type',
            name: 'TypeTable',
            component: () => import("../components/admin/type/TypeTable"),
            meta: {
              requireAuth: true,
              title: '分类'
            }
          },
          {
            path: '/admin/tag',
            name: 'TagTable',
            component: () => import("../components/admin/type/TagTable"),
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
        component: () => import("../components/admin/mood/MoodManager"),
        redirect: '/admin/mood',
        children: [
          {
            path: '/admin/mood',
            name: 'Mood',
            component: () => import("../components/admin/mood/Mood"),
            meta: {
              requireAuth: true,
              title: '动态',
            }
          },
          {
            path: '/admin/mooding',
            name: 'Mooding',
            component: () => import("../components/admin/mood/Mooding"),
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
        component: () => import("../components/admin/message/MessageManager"),
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
  }
];

const createRouter = () => new VueRouter({
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes,
});

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  // 重置 router
  // matcher 对外提供 match 和 addRoutes
  router.matcher = newRouter.matcher;
}

export default router;
