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

    public void addEmpl(Employee empl) {
        getAr().add(empl);
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void display() //Выводит в консоль данные об отделе, сотрудниках и ср. зарплате
    {
        BigDecimal sSalary = SumSalary(); //Сумма всех зарплат
        BigDecimal sAlary = sSalary.divide(new BigDecimal(ar.size()),6,3);//Делим сумму на кол-во сотрудников. Решил использовать для вычислений 6 знаков после запятой

        String answer ="\nDisplay ->" + name + ": \n"; //Составляем вывод в консоль для демонстрации работы
        for (int i=0; i<ar.size();i++) {
            answer+="\nTD "+i+":   "+ ar.get(i).getName() + " " + ar.get(i).getSalary();
        }

        answer+="\nSum salary: " + sSalary + " ar size " + ar.size() + "\nAverage salary: ";
        System.out.println(answer);
        System.out.printf("%.2f",  sAlary);    //Средняя зп
    }


    public BigDecimal avarageSalary() {  //Подсчет средней зп по отделу
        BigDecimal salary = SumSalary(); //Подсчет суммарной зп всех работников в отделе

        salary = salary.divide(new BigDecimal(ar.size()),6,3);
        return salary;
    }

    public BigDecimal avarageSalaryWithTransfer(BigDecimal sal) {  //Подсчет средней зп по отделу после изменений (перевода сотрудников)
        BigDecimal salary = SumSalary(); //Подсчет суммарной зп всех работников в отделе
        salary=salary.subtract(sal); //Вычитаем из суммарной зп зп переводящегося сотрудника (или складываем. Может придти отрицательное число для рассчетов)

        if(sal.compareTo(BigDecimal.ZERO)==1) //В зависимости от знака sal - открепляем или прикрепляем сотрудника к отделу.
            salary=salary.divide(new BigDecimal(ar.size()-1),6,3);

        if(sal.compareTo(BigDecimal.ZERO)==-1)
            salary=salary.divide(new BigDecimal(ar.size()+1),6,3);

        return salary;
    }
}
