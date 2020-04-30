import router, {constantRoutes, errorRoutes, resetRouter} from "./router";
import Router from 'vue-router'
import store from "./store";
import { getToken } from "./utils/auth";
import getPageTitle from "./utils/getPageTitle";

router.beforeEach(async(to, from ,next) => {
    document.title = getPageTitle(to.meta.title);
    const hasToken = getToken();
    const hasRoles = store.getters.roles && store.getters.roles.length;
    // token: 判断是否为 admin
    // role: ["admin", "client"] 判断是否拿到了相应的 permission_routes
    if (hasRoles) {
        if (to.path === '/admin/login') {
            await store.dispatch('user/resetToken');
            await store.dispatch('tagsView/initViews');
            resetRouter();
        }
        next();
    } else {
        if (to.path === '/admin/login') {
            next();
        } else {
            try {
                const roles = await store.dispatch('user/getInfo');
                const accessRoutes = await store.dispatch('permission/generateRoutes', roles);
                router.addRoutes(accessRoutes);
                router.addRoutes(errorRoutes);
                next({path: to.fullPath, replace: true});
            } catch (error) {
                console.log(error)
                await store.dispatch('user/resetToken');
                await store.dispatch('tagsView/initViews');
                resetRouter();
                next(`/admin/login?redirect=${to.path}`);
            }
        }
    }
});
