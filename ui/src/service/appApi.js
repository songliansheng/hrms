import authHeader from "./auth.header";
import store from '../store';


const axios = require("axios").default;

// URL below is set for non-production Environment , this URL should be set to the address of the server in production environment
axios.defaults.baseURL = "http://localhost:8090";
axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    store.dispatch("errors/handleError" , error)
  }
);

const appApi = {
  logIn(data) {
    return axios({
      url: "login",
      method: "POST",
      headers: { "Content-Type": "application/json;charset=UTF-8" },
      data: data,
    });
  },
  add(url, data) {
    return axios({
      url: url,
      method: "POST",
      data: data,
      headers: authHeader(),
    });
  },
  delete(url) {
    return axios({
      url: url,
      method: "DELETE",
      headers: authHeader(),
    });
  },
  update(url, data) {
    return axios({
      url: url,
      method: "PUT",
      data: data,
      headers: authHeader(),
    });
  },
  get(url) {
    return axios({ url: url, method: "GET", headers: authHeader() });
  },
};

export { appApi };
