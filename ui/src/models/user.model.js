class User {
  constructor() {
    this.username = "";
    this.password = "";
    this.descrption = "";
    this.label = "";
  }
}

const userHeader = [
  {
    text: "用户名",
    align: "start",
    sortable: false,
    value: "username",
  },
  { text: "角色", value: "label" },
  { text: "备注", value: "descrption" },
  { text: "操作", value: "actions", sortable: false },
];

export { userHeader, User };
