import {getToken, setToken, removeToken} from "../../utils/auth";

const state = {
  token: getToken(),
  name: '',
  roles: [],
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  }
};

const actions = {
  login({commit}, token) {
    commit('SET_TOKEN', token);
    setToken(token);
  },
  getInfo({commit, state}) {
    if (getToken()) {
      console.log("set admin")
      commit('SET_ROLES', ['admin']);
    } else {
      commit('SET_ROLES', ['client'])
    }
    return state.roles;
  },
  logout({commit, state, dispatch}) {
    commit('SET_TOKEN', '');
    commit('SET_ROLES', []);
    removeToken();
    // dispatch('tagsView/delAllViews', null, { root: true });
    dispatch('tagsView/initViews', null, {root: true});
  },
  resetToken({commit}) {
    commit('SET_TOKEN', '');
    commit('SET_ROLES', []);
    removeToken();
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
