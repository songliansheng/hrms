<template>
  <v-app>
    <v-data-table
      show-select
      :headers="HEADER"
      :items="depts"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar>
          <v-toolbar-title>部门列表</v-toolbar-title>
          <v-divider vertical class="mx-4" inset></v-divider>
          <v-spacer></v-spacer>
          <v-dialog
            v-model="dialogEditing"
            hide-overlay
            max-width="600px"
            overlay-opacity="true"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                增加职位
              </v-btn>
            </template>
            <DeptForm :department="editedItem" />
          </v-dialog>
          <v-dialog v-model="dialogDeleting" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">确定要删除此条目吗?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDialogDelete"
                  >取消</v-btn
                >
                <v-btn color="blue darken-1" text @click="confirmDelete"
                  >删除</v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="mr-2" @click="openDialogEdit(item)">
          mdi-pencil
        </v-icon>
        <v-icon small @click="openDialogDelete(item)"> mdi-delete </v-icon>
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
import { Dept, deptHeader } from "../../models/dept.model";
import DeptForm from "@/views/depts/DeptForm.vue";
import { EventBus } from "@/utils/event-bus";
import { mapState } from "vuex";

export default {
  components: { DeptForm },
  name: "depts-tab",
  data() {
    return {
      dialogEditing: false,
      dialogDeleting: false,
      editedItem: new Dept(),
      editedIndex: -1,
      HEADER: deptHeader,
    };
  },
  computed: {
    ...mapState({
      depts: (state) => state.dept.depts,
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
  mounted() {
    this.$store.dispatch("dept/initDepts");
    EventBus.$on("saveDepartment", this.saveEditing);
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
      this.editedIndex = this.depts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogEditing = true;
    },
    saveEditing() {
      if (this.editedIndex > -1) {
        this.$store
          .dispatch("dept/updateDept", {
            index: this.editedIndex,
            item: this.editedItem,
          })
          .then(this.closeDialogEdit());
      } else
        this.$store
          .dispatch("dept/addDept", this.editedItem)
          .then(this.closeDialogEdit());
    },
    closeDialogEdit() {
      this.dialogEditing = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, new Dept());
        this.editedIndex = -1;
      });
    },

    openDialogDelete(item) {
      this.editedIndex = this.depts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDeleting = true;
    },
    async confirmDelete() {
      await this.$store.dispatch("dept/deleteDept", {
        item: this.editedItem,
        index: this.editedIndex,
      });
      this.$store.dispatch("job/initJobs");
      this.closeDialogDelete();
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
<style></style>
