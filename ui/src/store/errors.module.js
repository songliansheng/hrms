export const errors = {
  state: {
    errorOccured: false,
    errorText: "",
    errrors: [],
  },
  namespaced: true,
  actions: {
    handleError({ commit }, err) {
      commit("displayError", err);
    },
    resetErrorState({ commit }, errored) {
      commit("resetError", errored);
    },
  },
  mutations: {
    displayError(state, err) {
      (state.errorOccured = true), (state.errorText = err);
    },
    resetError(state, errored) {
      (state.errorOccured = errored), (state.errorText = "");
    },
  },
};
