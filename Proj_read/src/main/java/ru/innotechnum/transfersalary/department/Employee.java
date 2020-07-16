package ru.innotechnum.transfersalary.department;

import java.math.BigDecimal;

public class Employee {
    private String name;
    private BigDecimal salary;

    public Employee() {
    }

    public Employee(String name, BigDecimal salary) {
        setName(name);
        setSalary(salary);
    }

    public Employee(String name, String salary) {
        setName(name);
        setSalary(new BigDecimal(salary));
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary=salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
