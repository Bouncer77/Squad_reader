package ru.innotechnum.TransferSalary.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Squad {
    private List<Employee> ar = new ArrayList<Employee>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела

    public List<Employee> getAr() {
        return ar;
    }

    public void setAr(ArrayList<Employee> as) {  //Пока добавил только сеттеры для выполнения первичной задачи
        ar.addAll(0,as);
    }

    public void addEmpl(Employee empl)
    {
        ar.add(empl);
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void testdisplay()
    {
        BigDecimal salary = new BigDecimal(0);
        System.out.println("\ntestdisplay ->" + name + ": \n");
        for(int i=0; i<ar.size();i++)
        {
            salary=salary.add(new BigDecimal(ar.get(i).getSalary().toString()));
           System.out.println("TD "+i+":   "+ ar.get(i).getName() + " " + ar.get(i).getSalary());
        }
        System.out.println("Sum salary: " + salary + " ar size " + ar.size() + "\nAverage salary: ");
        salary = salary.divide(new BigDecimal(ar.size()),6,3);
        System.out.printf("%.2f",  salary);    //Средняя зп
    }

    public BigDecimal testAvarageSalary()  //Подсчет средней зп по отделу
    {
        BigDecimal salary = new BigDecimal(0);
        for(int i=0; i<ar.size();i++)
        {
            salary=salary.add(new BigDecimal(ar.get(i).getSalary().toString()));
        }
        salary = salary.divide(new BigDecimal(ar.size()),6,3);
        return salary;
    }

    public BigDecimal testAvarageSalary2(BigDecimal sal)  //Подсчет средней зп по отделу
    {
        BigDecimal salary = new BigDecimal(0);
        for(int i=0; i<ar.size();i++)
        {
            salary=salary.add(new BigDecimal(ar.get(i).getSalary().toString()));
        }

        salary=salary.subtract(sal);
        if(sal.compareTo(BigDecimal.ZERO)==1)
        {
            salary=salary.divide(new BigDecimal(ar.size()-1),6,3);
        }
        else
        {
            salary=salary.divide(new BigDecimal(ar.size()+1),6,3);
        }
        return salary;
        //return String.format("%.2f",  salary/ar.size());
    }
}
