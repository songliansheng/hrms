<template>
  <v-form v-model="valid">
    <v-row justify="space-around" align-content="center"
      ><v-col cols="4"
        ><v-text-field
          v-model="user.username"
          label="用户名"
          :rules="userNameRules"
        ></v-text-field></v-col
    ></v-row>
    <v-row justify="space-around"
      ><v-col cols="4"
        ><v-text-field
          v-model="user.password"
          type="password"
          label="密码"
          :rules="passWordRules"
        ></v-text-field></v-col
    ></v-row>

    <v-row justify="center">
      <v-col cols="1"
        ><v-btn @click="handleLogin" :disabled="!valid">登陆</v-btn></v-col
      ><v-col cols="2"></v-col>
      <v-col cols="1"
        ><v-btn @click="reset" :disabled="!valid">重置</v-btn></v-col
      >
    </v-row>
    <v-layout justify-center>
      <v-alert v-model="errorOccured" dismissible width="500" type="error">
        {{ errorText }}
      </v-alert>
    </v-layout>
  </v-form>
</template>
<script>
import { User } from "@/models/user.model";
import { mapState } from "vuex";

export default {
  name: "login-form",
  data() {
    return {
      valid: true,
      user: new User(),
      align: "center",
      userNameRules: [(v) => !!v || "用户名不能为空"],
      passWordRules: [(v) => !!v || "密码不能为空"],
    };
  },
  mounted() {},
  computed: {
    ...mapState({
      errorText: (state) => state.errors.errorText,
    }),
    errorOccured: {
      get: function() {
        return this.$store.state.errors.errorOccured;
      },
      set: function(value) {
        this.$store.dispatch("errors/resetErrorState", value);
      },
    },
  },
  methods: {
    async handleLogin() {
      this.$store.dispatch("auth/logIn", this.user);
    },
    reset() {
      this.user = new User();
    },
  },
};
</script>
