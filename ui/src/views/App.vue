<template>
  <div id="app">
    <v-app>
      <v-app-bar app>
        <v-icon large color="green darken-2"> mdi-file-account-outline </v-icon>
        <v-toolbar-title>人事管理系统</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <template v-if="loggedIn">
          <v-btn
            class="text-h6"
            v-for="tab in this.tabs"
            v-bind:key="tab.name"
            @click="currentTab = tab.name"
          >
            {{ tab.label }}
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn icon>
            <v-icon @click="logOut">mdi-export</v-icon>
          </v-btn>
        </template>
      </v-app-bar>
      <v-main>
        <v-container>
          <template v-if="loggedIn">
            <keep-alive>
              <component v-bind:is="currentTab" class="tab"></component>
            </keep-alive>
          </template>
          <template v-else>
            <loginform />
          </template>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import { tabs } from "../models/utils.array";
import employeesTab from "@/views/employees/EmployeesTab";
import usersTab from "@/views/users/UsersTab";
import jobsTab from "@/views/jobs/JobsTab";
import DeptsTab from "@/views/depts/DeptsTab";
import loginform from "@/views/loginForm";
import authHeader from "../service/auth.header";

export default {
  name: "App",
  components: {
    employeesTab,
    DeptsTab,
    usersTab,
    jobsTab,
    loginform,
    authHeader,
  },
  data() {
    return {
      tabs: tabs,
      currentTab: "employeesTab",
    };
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/clearUsers");
      this.$store.dispatch("auth/logOut");
    },
  },
};
</script>

<style></style>
