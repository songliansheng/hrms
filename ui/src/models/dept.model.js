export class Dept {
  constructor() {
    this.dname = "";
    this.description = "";
  }
}

export const deptHeader = [
  {
    text: "部门名称",
    align: "start",
    sortable: false,
    value: "dname",
  },
  { text: "备注", value: "description" },
  { text: "操作", value: "actions", sortable: false },
];
