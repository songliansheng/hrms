import { appApi } from "../service/appApi";

const baseUrl = "jobs";

export const job = {
  state:{  jobs: [] },
  namespaced: true,
  actions: {
    initJobs({ commit }) {
      appApi.get(baseUrl).then((res) => {
        commit("initJobs", res.data);
      });
    },
    addJob({commit},data){
       appApi.add(baseUrl,data)
      .then(res => commit('addJob',res.data))
    },
    deleteJob({commit},payload){
      let url = baseUrl + '/' + payload.item.id;
      appApi.delete(url)
      .then(commit('deleteJob',payload.index))
    },
    updateJob({commit},payload){
      let url = baseUrl + '/' + payload.item.id;
       appApi.update(url,payload.item)
      .then(res => commit('updateJob',{data:res.data,index:payload.index}))
    },
    clearJobs({ commit }) {
      commit("clearJobs");
    },
   
  },

  mutations: {
    initJobs(state, data) {
      state.jobs = data;
    },
    addJob(state,data){
      state.jobs.push(data)
    },
    updateJob(state,payload){
      Object.assign(state.jobs[payload.index],payload.data)

    },
    deleteJob(state,index){
      state.jobs.splice(index, 1)
    },
    clearJobs(state) {
      state.jobs = [];
    },
  },
};
