import router, { resetRouter } from "./router";
import { Message } from "element-ui";
import store from "./store";
import getPageTitle from "./utils/getPageTitle";

router.beforeEach(async (to, from, next) => {
  document.title = getPageTitle(to.meta.title);
  const roles = store.getters.roles || [];
  if (roles.length === 0) {
    if (to.path === '/admin/login') {
      await store.dispatch('user/resetToken');
      await store.dispatch('tagsView/initViews');
      resetRouter();
      next();
    } else {
      try {
        const roles = await store.dispatch('user/getInfo');
        const accessRoutes = await store.dispatch('permission/generateRoutes', roles);
        next({path: to.fullPath, replace: true})
      } catch (error) {
        console.log(error)
        await store.dispatch('user/resetToken');
        await store.dispatch('tagsView/initViews');
        resetRouter();
        next(`/admin/login?redirect=${to.path}`);
      }
    }
  } else {
    if (roles.includes("admin")) {
      if (to.path === '/admin/login') {
        Message.success("您已登录！请先退出登录")
        next({path: '/admin/home'});
      } else {
        next()
      }
    } else {
      if (to.path === '/admin/login') {
        await store.dispatch('user/resetToken');
        await store.dispatch('tagsView/initViews');
        resetRouter();
        next();
      } else if (to.redirectedFrom.startsWith("/admin")) {
        next({path: '/admin/login'});
      } else {
        next();
      }
    }
  }
  // token: 判断是否为 admin
  // role: ["admin", "client"] 判断是否拿到了相应的 permission_routes
  // if (hasRoles.length > 0) {
  //   // 已分配 role
  //   if (hasRoles.includes("admin")) {
  //     console.log("admin")
  //     if (to.path === '/admin/login') {
  //       console.log("admin to")
  //       next({path: '/admin/home'});
  //     }
  //   } else {
  //     if (to.path === '/admin/login') {
  //       await store.dispatch('user/resetToken');
  //       await store.dispatch('tagsView/initViews');
  //       resetRouter();
  //     }
  //   }
  //   next();
  // } else {
  //   // 未分配 role
  //   if (to.path === '/admin/login') {
  //     await store.dispatch('user/resetToken');
  //     await store.dispatch('tagsView/initViews');
  //     resetRouter();
  //     console.log("logout")
  //     next();
  //   } else {
  //     // 判断是否为 admin，并生成 routes
  //     try {
  //       const roles = await store.dispatch('user/getInfo');
  //       const accessRoutes = await store.dispatch('permission/generateRoutes', roles);
  //       router.addRoutes(accessRoutes);
  //       router.addRoutes(errorRoutes);
  //       console.log(to)
  //       next({path: to.fullPath});
  //     } catch (error) {
  //       console.log("error")
  //       console.log(error)
  //       await store.dispatch('user/resetToken');
  //       await store.dispatch('tagsView/initViews');
  //       resetRouter();
  //       next(`/admin/login?redirect=${to.path}`);
  //     }
  //   }
  // }
});
