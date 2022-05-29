import { Dept } from "./dept.model";
export class Job {
  constructor() {
    this.jname = "";
    this.dept = new Dept();
    this.description = "";
  }
}
export const jobHeader = [
  {
    text: "职位名称",
    align: "start",
    sortable: false,
    value: "jname",
  },
  { text: "部门", value: "dept.dname" },
  { text: "备注", value: "description" },
  { text: "操作", value: "actions", sortable: false },
];
