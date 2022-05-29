<template>
  <div>
    <v-card>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          <v-row>
            <v-col
              ><v-text-field
                label="姓名"
                v-model="employee.eName"
                :rules="nameRules"
              ></v-text-field
            ></v-col>
            <v-col>
              <p>性别：</p>
              <v-radio-group row v-model="employee.sex">
                <v-radio label="男" value="男"></v-radio>
                <v-radio label="女" value="女"></v-radio>
              </v-radio-group>
            </v-col>
            <v-col
              ><v-text-field
                label="学历"
                v-model="employee.education"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-text-field
            label="身份证号"
            v-model="employee.idNumber"
            :rules="idNumRules"
            required
          ></v-text-field>
          <v-row>
            <v-col
              ><v-text-field
                label="手机"
                v-model="employee.phone"
              ></v-text-field
            ></v-col>
            <v-col
              ><v-text-field
                label="专业"
                v-model="employee.speciality"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col
              ><v-combobox
                v-model="employee.job.jname"
                :items="jobNames"
                label="填写或选择职位"
                outlined
                dense
              ></v-combobox
            ></v-col>
            <v-col
              ><v-text-field
                label="住址"
                v-model="employee.address"
              ></v-text-field
            ></v-col>
          </v-row>

          <v-text-field
            label="备注"
            v-model="employee.description"
          ></v-text-field>
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
import { mapState } from "vuex";

export default {
  name: "employee-form",
  props: { employee: Object },
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
  computed: {
    ...mapState({
      jobs: (state) => state.job.jobs,
    }),
    jobNames: function () {
      let arr = [];
      this.jobs.forEach((element) => arr.push(element.jname));
      return arr;
    },
  },
  methods: {
    save() {
      this.$refs.form.validate();
      let jobName = this.employee.job.jname;
      let jobIndex = this.jobNames.indexOf(jobName);
      if (jobIndex != -1) this.employee.job = this.jobs[jobIndex];
      EventBus.$emit("saveEmployee");
    },
    cancel() {
      EventBus.$emit("cancelEdit");
    },
  },
};
</script>
