<template>
  <div>
    <v-card>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          <v-row>
            <v-col
              ><v-text-field
                label="用户名"
                v-model="user.username"
                :rules="nameRules"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-else></v-else>
          <v-row>
            <v-col v-if="addingMode"
              ><v-text-field
                type="password"
                label="设置密码"
                v-model="user.password"
              ></v-text-field
            ></v-col>
            <v-col v-else
              ><v-text-field
                type="password"
                label="修改密码"
                v-model="newPwd"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col align-self="center" md="2">
              <span>角色：</span>
            </v-col>
            <v-col>
              <v-radio-group v-model="user.label" row>
                <v-radio label="普通用户" value="普通用户"></v-radio>
                <v-radio label="管理员" value="管理员"></v-radio
              ></v-radio-group>
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
  components: {},
  name: "user-form",
  props: { user: Object ,addingMode:Boolean},
  data() {
    return {
      role: "",
    
      newPwd: "",
      valid: true,
      nameRules: [
        (v) => !!v || "姓名不能为空",
        (v) => v.length <= 25 || "Name must be less than 10 characters",
      ],
    };
  },
  computed: {
  },
  methods: {
    save() {
      this.$refs.form.validate();
      if(this.updatingMode)
      this.user.password = this.newPwd ;
      EventBus.$emit("saveUser");
    },
    cancel() {
      EventBus.$emit("cancelEdit");
    },
  },
};
</script>
