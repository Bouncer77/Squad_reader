package ru.innotechnum.TransferSalary.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Squad {
    private List<Employee> ar = new ArrayList<Employee>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела

    private BigDecimal SumSalary() //Подсчитывает суммарную зп одного отдела в BigDecimal. Экономия места, дублирующийся код.
    {
        BigDecimal salary = new BigDecimal(0);
        for(int i=0; i<ar.size();i++) {
            salary = salary.add(ar.get(i).getSalary());
        }
        return salary;
    }

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

        String answer ="\ntestdisplay ->" + name + ": \n"; //Составляем вывод в консоль для демонстрации работы
        for(int i=0; i<ar.size();i++)
        {
            salary=salary.add(new BigDecimal(ar.get(i).getSalary().toString()));
            answer+="TD "+i+":   "+ ar.get(i).getName() + " " + ar.get(i).getSalary();
        }
        answer+="Sum salary: " + salary + " ar size " + ar.size() + "\nAverage salary: ";
        salary = salary.divide(new BigDecimal(ar.size()),6,3);

        System.out.println(answer);
        System.out.printf("%.2f",  salary);    //Средняя зп
    }

    public BigDecimal testAvarageSalary()  //Подсчет средней зп по отделу
    {
        BigDecimal salary = SumSalary(); //Подсчет суммарной зп всех работников в отделе

        salary = salary.divide(new BigDecimal(ar.size()),6,3);
        return salary;
    }

    public BigDecimal testAvarageSalary2(BigDecimal sal)  //Подсчет средней зп по отделу после изменений (перевода сотрудников)
    {
        BigDecimal salary = SumSalary(); //Подсчет суммарной зп всех работников в отделе

        salary=salary.subtract(sal); //Вычитаем из суммарной зп зп переводящегося сотрудника (или складываем. Может придти отрицательное число для рассчетов)

        if(sal.compareTo(BigDecimal.ZERO)>0)
        {
            salary=salary.divide(new BigDecimal(ar.size()-1),6,3);
        }
        if(sal.compareTo(BigDecimal.ZERO)<0)
        {
            salary=salary.divide(new BigDecimal(ar.size()+1),6,3);
        }
        return salary;
    }
}
