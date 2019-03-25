package org.prime.internship.service;

import org.prime.internship.entity.Department;
import org.prime.internship.repository.DepartmentRepository;

public class DepartmentService {

    private DepartmentRepository departmentRepository;

    DepartmentService() {
        this.departmentRepository = new DepartmentRepository();
    }

    Department processDepartmentToDB(String name) {
        Department department;
        //Check if department exists in DB, if not, use INSERT repository method
        if (departmentRepository.getOneByName(name) == null) {
            department = new Department();
            department.setName(name);
            department.setDepartmentId(departmentRepository.insert(department).getDepartmentId());
        } else {
            // If department already exists in DB, use UPDATE repository method
            department = departmentRepository.getOneByName(name);
            departmentRepository.update(department);
        }
        return department;
    }
}
