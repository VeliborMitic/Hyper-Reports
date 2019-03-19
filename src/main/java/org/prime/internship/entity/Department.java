package org.prime.internship.entity;

import java.io.Serializable;

public class Department  implements Serializable {
    private static final long serialVersionUID = 1224162932987697738L;
    private int departmentId;
    private String name;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
