package ru.innotechnum.transfersalary.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Squad {
    private List<Employee> listEmpl = new ArrayList<Employee>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела


    private BigDecimal sumSalary() //Подсчитывает суммарную зп одного отдела в BigDecimal. Экономия места, дублирующийся код.
    {
        BigDecimal salary = new BigDecimal(0);
        for(int i = 0; i< listEmpl.size(); i++) {
            salary = salary.add(listEmpl.get(i).getSalary());
        }
        return salary;
    }

    public List<Employee> getListEmpl() {
        return listEmpl;
    }

    public void addEmpl(Employee empl) {
        getListEmpl().add(empl);
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    //Выводит в консоль данные об отделе, сотрудниках и ср. зарплате
    public void display(int CHARS_AFTER_POINT) {
        StringBuilder answer = new StringBuilder("\nDisplay ->" + name + ": \n");

        for (int i = 0; i< listEmpl.size(); i++) {
            answer.append("\nTD "+i+":   "+ listEmpl.get(i).getName() + " " + listEmpl.get(i).getSalary());
        }

        answer.append("\nSum salary: " + sumSalary() + " ar size " + listEmpl.size() + "\nAverage salary: ");
        System.out.println(answer);
        System.out.printf("%.2f",  avarageSalary(CHARS_AFTER_POINT));    //Среднний доход
    }
    
    //Подсчет средней зп по отделу
    public BigDecimal avarageSalary(int CHARS_AFTER_POINT) {
        BigDecimal salary = sumSalary(); //Подсчет суммарной зп всех работников в отделе
        salary = salary.divide(new BigDecimal(listEmpl.size()),CHARS_AFTER_POINT,3);
        return salary;
    }

    //Подсчет средней зп по отделу после изменений (перевода сотрудников)
    public BigDecimal avarageSalaryWithTransfer(BigDecimal sal, int CHARS_AFTER_POINT) {
        BigDecimal salary = sumSalary(); //Подсчет суммарной зп всех работников в отделе
        salary=salary.subtract(sal); //Вычитаем из суммарной зп зп переводящегося сотрудника (или складываем. Может придти отрицательное число для рассчетов)

        if(sal.compareTo(BigDecimal.ZERO)==1) //В зависимости от знака sal - открепляем или прикрепляем сотрудника к отделу.
            salary=salary.divide(new BigDecimal(listEmpl.size()-1),CHARS_AFTER_POINT,3);

        if(sal.compareTo(BigDecimal.ZERO)==-1)
            salary=salary.divide(new BigDecimal(listEmpl.size()+1),CHARS_AFTER_POINT,3);

        return salary;
    }
}
