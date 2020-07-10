package ru.innotechnum.TransferSalary.department;

import java.util.ArrayList;
import java.util.List;

public class Squad {
    private List<Employee> ar = new ArrayList<Employee>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела


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
        double salary = 0;
        System.out.println("\ntestdisplay ->" + name + ": \n");
        for(int i=0; i<ar.size();i++)
        {
            salary+=ar.get(i).getSalary();
           System.out.println("TD "+i+":   "+ ar.get(i).getName() + " " + ar.get(i).getSalary());
        }
        System.out.println("Average salary: ");
        System.out.printf("%.2f",  salary/ar.size());    //Средняя зп
    }
}
