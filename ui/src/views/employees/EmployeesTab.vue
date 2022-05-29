<template>
  <v-app>
    <v-data-table
      show-select
      :headers="HEADER"
      :items="employees"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar>
          <v-toolbar-title>员工列表</v-toolbar-title>
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
                增加员工
              </v-btn>
            </template>
            <EmployeeForm :employee="editedItem" />
          </v-dialog>
          <v-dialog v-model="dialogDeleting" max-width="500px">
            <v-card>
              <v-card-title class="text-h5 justify-center"
                >确定要删除此条目吗?</v-card-title
              >
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
import { Employee, employeeHeader } from "../../models/employee.model";
import { Job } from "../../models/job.model";
import EmployeeForm from "@/views/employees/EmployeeForm.vue";
import { EventBus } from "@/utils/event-bus";
import { appApi } from "../../service/appApi";
import { mapState } from "vuex";

export default {
  components: { EmployeeForm },
  name: "employees-tab",

  data() {
    return {
      employees: [],
      baseUrl: "empls",
      dialogEditing: false,
      dialogDeleting: false,
      editedItem: new Employee(),
      editedIndex: -1,
      HEADER: employeeHeader,
    };
  },
  computed: {
    ...mapState({
      jobs: (state) => state.job.jobs,
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
    this.getEmployees();
    this.$store.dispatch("job/initJobs");
    EventBus.$on("saveEmployee", this.saveEditing);
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
    async getEmployees() {
      let response = await appApi.get(this.baseUrl);
      this.employees = response.data;
    },

    openDialogEdit(item) {
      this.editedIndex = this.employees.indexOf(item);
      if (item.job == null) item.job = new Job();
      this.editedItem = Object.assign({}, item);
      this.dialogEditing = true;
    },
    async saveEditing() {
      if (this.editedIndex > -1) {
        let url = this.baseUrl + "/" + this.editedItem.id;
        let response = await appApi.update(url, this.editedItem);
        Object.assign(this.employees[this.editedIndex], response.data);
      } else await appApi.add(this.baseUrl, this.editedItem);
      this.closeDialogEdit();
    },
    closeDialogEdit() {
      this.dialogEditing = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, new Employee());
        this.editedIndex = -1;
      });
    },

    openDialogDelete(item) {
      this.editedIndex = this.employees.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDeleting = true;
    },
    async confirmDelete() {
      let url = this.baseUrl + "/" + this.editedItem.id;
      await appApi.delete(url);
      this.employees.splice(this.editedIndex, 1)
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
