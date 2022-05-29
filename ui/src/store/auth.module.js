import { appApi } from "../service/appApi";

const currentUser = JSON.parse(localStorage.getItem("user"));
const initState = currentUser
  ? { loggedIn: true, currentUser }
  : { loggedIn: false, currentUser: null };
const baseUrl = "users";

export const auth = {
  state: {
    users: [],
    roles: [],
    ...initState,
  },
  namespaced: true,
  actions: {
    logIn({ commit }, user) {
      return appApi.logIn(user).then((res) => {
        if (res.data) commit("logIn", res.data);
        return res;
      });
    },

    getUsers({ commit }) {
      appApi.get(baseUrl).then((res) => commit("initUsers", res.data));
    },
    addUser({ commit }, item) {
      appApi.add(baseUrl, item).then((res) => commit("addUser", res.data));
    },
    deleteUser({ commit }, payload) {
      let url = baseUrl + "/" + payload.item.id;
      appApi.delete(url).then(commit("deleteUser", payload.index));
    },
    async updateUser({ commit }, payload) {
      let url = "users/" + payload.item.id;
      let response = await appApi.update(url, payload.item);
      commit("updateUser", { item: response.data, index: payload.index });
    },
    clearUsers({ commit }) {
      commit("clearUsers");
    },

    logOut({ commit }) {
      commit("logOut");
    },
  },

  mutations: {
    logIn(state, data) {
      localStorage.setItem("user", JSON.stringify(data));
      state.loggedIn = true;
      state.currentUser = data;
    },
    initUsers(state, data) {
      state.users = data;
    },
    addUser(state, item) {
      state.users.push(item);
    },
    deleteUser(state, index) {
      state.users.splice(index, 1);
    },
    updateUser(state, payload) {
      Object.assign(state.users[payload.index], payload.item);
    },
    clearUsers(state) {
      state.users = [];
    },
    logOut(state) {
      localStorage.removeItem("user");
      state.loggedIn = false;
      state.currentUser = null;
    },
  },
};
