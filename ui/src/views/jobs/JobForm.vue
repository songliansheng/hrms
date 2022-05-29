<template>
  <div>
    <v-card>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          <v-row>
            <v-col
              ><v-text-field
                label="姓名"
                v-model="job.jname"
                :rules="nameRules"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col
              ><v-text-field label="部门" v-model="job.department.dname"></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field
                label="备注"
                v-model="job.description"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn color="blue darken-1" text @click="cancel"> Cancel </v-btn>
        <v-spacer />
        <v-btn
          color="blue darken-1"
          text
          @click="save"
          :disabled="!valid"
          class="mr-4"
        >
          Save
        </v-btn>
        <v-spacer />
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import { EventBus } from "@/utils/event-bus";
export default {
  name: "job-form",
  props: { job: Object },
  data() {
    return {
      valid: true,
      nameRules: [
        (v) => !!v || "姓名不能为空",
        (v) => v.length <= 10 || "Name must be less than 10 characters",
      ],
      idNumRules: [(v) => v.length == 18 || "身份证号必须是18位"],
    };
  },
  methods: {
    save() {
      this.$refs.form.validate();
      EventBus.$emit("saveJob", this.job);
    },
    cancel() {
      EventBus.$emit("cancelEdit");
    },
  },
};
</script>
