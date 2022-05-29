import Vue from "vue";
import Vuex from "vuex";
import {job} from "./job.module";
import {dept} from "./dept.module";
import {auth} from "./auth.module";
import {errors} from "./errors.module";

Vue.use(Vuex);

export default new Vuex.Store({modules:{job,dept,auth,errors}});
