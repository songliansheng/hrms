<template>
  <v-app>
    <v-data-table
      show-select
      :headers="HEADER"
      :items="jobs"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar>
          <v-toolbar-title>职位列表</v-toolbar-title>
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
            <JobForm :job="editedItem" />
          </v-dialog>
          <v-dialog v-model="dialogDeleting" max-width="500px">
            <v-card>
              <v-card-title class="justify-center"
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
import { Job, jobHeader } from "../../models/job.model";
import { Dept } from "../../models/dept.model";
import JobForm from "@/views/jobs/JobForm.vue";
import { EventBus } from "@/utils/event-bus";
import { mapState } from "vuex";

export default {
  components: { JobForm },
  name: "jobs-tab",
  data() {
    return {
      dialogEditing: false,
      dialogDeleting: false,
      editedItem: new Job(),
      editedIndex: -1,
      HEADER: jobHeader,
    };
  },
  computed: {
    ...mapState({
      jobs: (state) => state.job.jobs,
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
  },
  mounted() {
    EventBus.$on("saveJob", this.saveEditing);
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
      this.editedIndex = this.jobs.indexOf(item);
      if(item.dept == null) item.dept = new Dept(),
      this.editedItem = Object.assign({}, item);
      this.dialogEditing = true;
    },
    saveEditing() {
      if (this.editedIndex > -1) {
        this.$store
          .dispatch("job/updateJob", {
            index: this.editedIndex,
            item: this.editedItem,
          })
          .then(this.closeDialogEdit());
      } else
        this.$store
          .dispatch("job/addJob", this.editedItem)
          .then(this.closeDialogEdit());
    },
    closeDialogEdit() {
      this.dialogEditing = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, new Job());
        this.editedIndex = -1;
      });
    },

    openDialogDelete(item) {
      this.editedIndex = this.jobs.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDeleting = true;
    },
    confirmDelete() {
      this.$store.dispatch("job/deleteJob", {
        item: this.editedItem,
        index: this.editedIndex,
      });
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
