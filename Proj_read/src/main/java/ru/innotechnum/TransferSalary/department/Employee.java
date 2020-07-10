package ru.innotechnum.TransferSalary.department;

import java.math.BigDecimal;

public class Employee {
    private String name;
    private BigDecimal salary;

    public BigDecimal getSalary() { return salary; }

    public void setSalary(String salary) { this.salary=new BigDecimal(salary); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
