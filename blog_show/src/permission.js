import router from "./router";
import store from "./store";
import { getToken } from "./utils/auth";
import getPageTitle from "./utils/getPageTitle";

router.beforeEach(async(to, from ,next) => {
    document.title = getPageTitle(to.meta.title);
    const hasToken = getToken();
    if (hasToken) {
        if (to.path === '/admin/login') {
            next();
        } else {
            const hasRoles = store.getters.roles && store.getters.roles.length;
            if (hasRoles) {
                next();
            } else {
                try {
                    const roles = await store.dispatch('user/getInfo');
                    const accessRoutes = await store.dispatch('permission/generateRoutes', roles);
                    router.addRoutes(accessRoutes);
                    next({...to, replace: true});
                } catch (error) {
                    console.log(error)
                    await store.dispatch('user/resetToken');
                    next(`/admin/login?redirect=${to.path}`);
                }
            }
        }
    } else {
        if (to.meta.requireAuth === false) {
            next();
        } else {
            next({
                path: '/admin/login',
                query: {redirect: to.fullPath},
            })
        }
    }
});
