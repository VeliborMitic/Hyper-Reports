create table cities
(
  city_id int auto_increment,
  name    varchar(30) null,
  constraint cities_city_id_uindex
    unique (city_id)
);

alter table cities
  add primary key (city_id);

create table companies
(
  company_id   int auto_increment,
  name         varchar(30) null,
  lastDocument timestamp   null,
  constraint companies_company_id_uindex
    unique (company_id)
);

alter table companies
  add primary key (company_id);

create table departments
(
  department_id int auto_increment,
  name          varchar(30) null,
  constraint departments_department_id_uindex
    unique (department_id)
);

alter table departments
  add primary key (department_id);

create table employees
(
  employee_id   int auto_increment,
  name          varchar(39) null,
  company_id    int         not null,
  city_id       int         not null,
  department_id int         not null,
  constraint employees_employee_id_uindex
    unique (employee_id),
  constraint employees_cities__fk
    foreign key (city_id) references cities (city_id),
  constraint employees_companies__fk
    foreign key (company_id) references companies (company_id),
  constraint employees_departments__fk
    foreign key (department_id) references departments (department_id)
);

alter table employees
  add primary key (employee_id);

create table turnovers
(
  turnover_id int auto_increment,
  employee_id int       not null,
  date        timestamp null,
  turnover    double    null,
  constraint turnovers_turnover_id_uindex
    unique (turnover_id),
  constraint turnovers_employees__fk
    foreign key (employee_id) references employees (employee_id)
);

alter table turnovers
  add primary key (turnover_id);