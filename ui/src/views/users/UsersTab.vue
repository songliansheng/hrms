<template>
  <v-app>
    <v-data-table :items="users" :headers="HEADER" :items-per-page="10">
      <template v-slot:top>
        <v-toolbar>
          <v-toolbar-title>用户列表</v-toolbar-title>
          <v-divider vertical class="mx-4" inset></v-divider>
          <v-spacer></v-spacer>
          <v-dialog
            v-model="dialogEditing"
            hide-overlay
            max-width="600px"
            overlay-opacity="true"
          >
            <template v-slot:activator="{ attrs, on }">
              <v-btn color="primary" dark class="mb-2" v-on="on" v-bind="attrs"
                >增加用户</v-btn
              >
            </template>
            <UserForm :user="editedItem" :addingMode = "addingMode"/>
          </v-dialog>
        </v-toolbar>

        <v-dialog v-model="dialogDeleting" max-width="500px">
          <v-card>
            <v-card-title class="justify-center"
              >确定要删除此条目吗？</v-card-title
            >
            <v-card-actions class="justify-center">
              <v-btn @click="closeDialogDelete"> 取消 </v-btn>
              <v-btn @click="confirmDelete">删除</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon @click="openDialogEdit(item)">mdi-pencil</v-icon>
        <v-icon @click="openDialogDelete(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
    <v-layout justify-center>
      <v-alert v-model="errorOccured" dismissible width="500" type="error">{{
        errorText
      }}</v-alert>
    </v-layout>
  </v-app>
</template>
<script>
import { User, userHeader } from "@/models/user.model";
import UserForm from "@/views/users/UserForm.vue";
import { EventBus } from "@/utils/event-bus";
import { mapState } from "vuex";

export default {
  components: { UserForm },
  name: "users-tab",
  data() {
    return {
      dialogEditing: false,
      dialogDeleting: false,
      editedItem: new User(),
      editedIndex: -1,
      HEADER: userHeader,
    };
  },
  computed: {
    ...mapState({
      users: (state) => state.auth.users,
      errorText: (state) => state.errors.errorText,
    }),
    errorOccured: {
      get: function () {
        return this.$store.state.errors.errorOccured;
      },
      set: function (value) {
        this.$store.dispatch("errors/resetErrorState", value);
      },
    },
    addingMode:function(){
      return !(this.editedIndex + 1)
      }
  },
  mounted() {
    this.$store.dispatch("auth/getUsers");
    EventBus.$on("saveUser", this.saveEditing);
    EventBus.$on("cancelEdit", this.closeDialogEdit);
  },

  watch: {
    dialogEdit(val) {
      val || this.closeDialogEdit();
    },
    dialogDelete(val) {
      val || this.closeDialogDelete();
    },
  },
  methods: {
    openDialogEdit(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogEditing = true;
    },
    saveEditing() {
      if (this.editedIndex > -1) {
        this.$store
          .dispatch("auth/updateUser", {
            index: this.editedIndex,
            item: this.editedItem,
          })
          .then(this.closeDialogEdit());
      } else
        this.$store
          .dispatch("auth/addUser", this.editedItem)
          .then(this.closeDialogEdit());
    },
    closeDialogEdit() {
      this.dialogEditing = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, new User());
        this.editedIndex = -1;
      });
    },

    openDialogDelete(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDeleting = true;
    },
    confirmDelete() {
      this.$store
        .dispatch("auth/deleteUser", {
          item: this.editedItem,
          index: this.editedIndex,
        })
        .then(this.closeDialogDelete());
    },

    closeDialogDelete() {
      this.dialogDeleting = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
  },
};
</script>
