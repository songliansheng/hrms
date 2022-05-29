import { appApi } from "../service/appApi";
const baseUrl = "depts";
export const dept = {
  state:{  depts: []},
  namespaced: true,
  actions: {
    initDepts({ commit }) {
      appApi.get(baseUrl).then((res) => {
        commit("initDepts", res.data);
      });
    },
    addDept({commit},data){
      appApi.add(baseUrl,data)
      .then(res => commit('addDept',res.data))
    },
     deleteDept({commit},payload){
      let url = baseUrl + '/' + payload.item.id;
      return appApi.delete(url)
      .then(res => {commit('deleteDept',payload.index) ;
        return res}
      )
    },
    updateDept({commit},payload){
      let url = baseUrl + '/' + payload.item.id;
      appApi.update(url,payload.item)
      .then(res => commit('updateDept',{item:res.data,index:payload.index}))
    },
    clearDepts({ commit }) {
      commit("clearDepts");
    },
   
  },

  mutations: {
    initDepts(state, data) {
      state.depts = data;
    },
    addDept(state,data){
      state.depts.push(data)
    },
    updateDept(state,payload){
      Object.assign(state.depts[payload.index],payload.data)

    },
    deleteDept(state,index){
      state.depts.splice(index, 1)
    },
    clearDepts(state) {
      state.depts = [];
    },
  },
};
