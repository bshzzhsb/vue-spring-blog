const getters = {
    device: state => state.app.device,
    sidebar: state => state.app.sidebar,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
    token: state => state.user.token,
    roles: state => state.user.roles,
    permission_routes: state => state.permission.routes,
};
export default getters