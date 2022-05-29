import Vue from "vue";
import Vuetify from "vuetify/lib";

Vue.use(Vuetify);


export default new Vuetify({
  theme: { dark: true },
  breakpoint: {
    mobile: true,
    mobileBreakpoint: 1300,
    sm: true,
    thresholds: {
      xs: 340,
      sm: 540,
      md: 800,
      lg: 1280,
    },
  },
});
