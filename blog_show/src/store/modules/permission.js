import { constantRoutes, asyncRoutes } from "../../router";

const state = {
    routes: [],
};

const mutations = {
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes;
        state.routes = constantRoutes.concat(routes);
    }
};

const actions = {
    generateRoutes({ commit }, roles) {
        return new Promise(resolve => {
            let accessedRoutes;
            if (roles.includes('admin')) {
                accessedRoutes = asyncRoutes || [];
            } else {
                accessedRoutes = [];
            }
            commit('SET_ROUTES', accessedRoutes);
            resolve(accessedRoutes)
        })
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
}
