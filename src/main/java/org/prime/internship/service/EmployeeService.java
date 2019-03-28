package org.prime.internship.service;

import org.prime.internship.entity.Employee;
import org.prime.internship.repository.EmployeeRepository;

class EmployeeService {

    private final EmployeeRepository employeeRepository;

    EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
    }

    Employee processEmployeeToDB(String name, int companyId, int cityID, int departmentId) {
        Employee employee;
        //Check if employee exists in DB, if not, use INSERT repository method
        if (employeeRepository.getOneByName(name) == null) {
            employee = new Employee();
            employee.setName(name);
            employee.setCompanyId(companyId);
            employee.setCityId(cityID);
            employee.setDepartmentId(departmentId);
            employee.setEmployeeId(employeeRepository.insert(employee).getEmployeeId());
        } else {
            // If employee already exists in DB, use UPDATE repository method
            employee = employeeRepository.getOneByName(name);
            employee.setCompanyId(companyId);
            employee.setCityId(cityID);
            employee.setDepartmentId(departmentId);
        }
        return employee;
    }
}