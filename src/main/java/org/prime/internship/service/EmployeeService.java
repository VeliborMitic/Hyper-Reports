package org.prime.internship.service;

import org.prime.internship.entity.Employee;
import org.prime.internship.repository.EmployeeRepository;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    EmployeeService(){
        this.employeeRepository = new EmployeeRepository();
    }

    Employee processEmployeeToDB (String name, int companyId, int cityID, int departmentId){
        Employee employee;
        if (employeeRepository.getOneByName(name) == null){
            employee = new Employee();
            employee.setName(name);
            employee.setCompanyId(companyId);
            employee.setCityId(cityID);
            employee.setDepartmentId(departmentId);
            employee.setEmployeeId(employeeRepository.insert(employee).getEmployeeId());
        }else {
            employee = employeeRepository.getOneByName(name);
            employee.setCompanyId(companyId);
            employee.setCityId(cityID);
            employee.setDepartmentId(departmentId);
        }
        return employee;
    }

}
