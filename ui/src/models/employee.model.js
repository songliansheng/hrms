 import { Job } from "./job.model";
 class Employee {
    constructor() {
        this.eName = '';
        this.sex = '';
        this.idNumber = "";
        this.education = "";
        this.phone = "";
        this.speciality = "";
        this.address = "";
        this.description = "";
        this.job = new Job();
    }

}

const employeeHeader = [
    {
        text: "姓名",
        align: "start",
        sortable: false,
        value: "eName",
    },
    { text: "性别", value: "sex" },
    { text: "手机", value: "phone" },
    { text: "学历", value: "education" },
    { text: "身份证号", value: "idNumber" },
    { text: "职位", value: "job.jname" },
    { text: "住址", value: "address" },
    { text: "备注", value: "description" },
    { text: "操作", value: "actions", sortable: false },
]

export {Employee,employeeHeader}