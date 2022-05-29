import Vue from 'vue'
import Vuex from 'vuex'
import App from './views/App.vue'
import vuetify from './utils/vuetify';
import store from './store';

Vue.config.errorHandler = (err, vm, info) => {
  console.log("[ERROR CATCH]: ", err);
  console.log("[ERROR COMPONENT]: ", vm);
  console.log("[ERROR INFO]: ", info);
}

Vue.config.warnHandler = (warn, vm, info) => {
  console.log("[WARN CATCH]: ", warn);
  console.log("[WARN COMPONENT]: ", vm);
  console.log("[WARN INFO]: ", info);
}

Vue.config.productionTip = false
Vue.use(Vuex)

new Vue({
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

