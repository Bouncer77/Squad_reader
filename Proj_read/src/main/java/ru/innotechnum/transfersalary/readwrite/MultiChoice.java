package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;
import java.math.BigDecimal;
import java.util.*;

public class MultiChoice {
    private Map<String, String> map = new TreeMap<>();  //Первый String - отсортированный массив имен в строке. Второй - Текст с информацией о переводе (Сколько ср.зп была в отделе и сколько стала)
    private Squad squad1;
    private Squad squad2;
    private int numberOfValues = 0;  //Количество подходящих вариантов перестановок

    public MultiChoice(Squad squad1, Squad squad2) {
        this.squad1=squad1;
        this.squad2=squad2;
    }

    public StringBuilder additionalResponse(List<Employee> listEmpl) { //
        for(Employee employee : listEmpl) {                     // [ Если Убрать этот код и при первом вызове multiTransfer подать вторым аргументом пустой массив
            Employee[] emplArray = new Employee[] {employee};   // [ То выведет все комбинации + одиночные переводы. Т.к одиночные переводы в основном блоке, а групповые в дополнительном - код пока оставил.
            multiTransfer(listEmpl, emplArray);                  // [ Пока не решил, стоит ли выводить информацию в разных блоках или в одном.
        }
        StringBuilder answer = new StringBuilder("\nДополнитльные варианты переводов: \n");
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                answer.append("\n" + entry.getKey() + " {" + entry.getValue() + "}");
            }
        } else {
            answer.append("\n Отсутствуют ");
        }
        System.out.println(answer);
        answer.append("\nНайдено подходящих результатов: " + numberOfValues);
        System.out.println("Найдено подходящих результатов: " + numberOfValues);
        return answer;
    }

    private Boolean checkRepeatNumber(Employee[] employees) {  //Проверка, чтобы не выбирать одних и тех же сотрудников для перевода.
        for (int d = 0; d < employees.length; d++)
            for (int i = 0; i < employees.length; i++) {
                if (employees[d].getName().equals(employees[i].getName()) && d != i) {
                    return false;
                }
            }
        return true;
    }

    private Boolean checkSalary(Employee[] emplArr) {  //Проверка на возрастание средней зарплаты в обоих отделах при переводе группы сотрудников.
        BigDecimal sum = new BigDecimal(0);
        for (Employee rr: emplArr) {
            sum = sum.add(rr.getSalary());
        }
        return (squad2.getListEmpl().size()>emplArr.length
                && squad2.avarageSalaryWithTransfer(sum,emplArr.length).compareTo(squad2.getAvarageSalary())>0
                && squad1.avarageSalaryWithTransfer(sum.negate(),emplArr.length).compareTo(squad1.getAvarageSalary())>0); //Перенос строки в таком случае вроде делается так
    }

    private void multiTransfer(List<Employee> employeeList, Employee[] emplArray) {
        for (Employee s3 : employeeList) {
            BigDecimal salary = BigDecimal.valueOf( 0 );  //Сумма переводящихся
            Employee[] newArrays = new Employee[emplArray.length + 1];  //newArrays = Выбранная для перевода пачка сотрудников
            String[] namesEmpl = new String[emplArray.length + 1];     //namesEmpl - список сотрудников поименно в hashmap.
            for (int i = 0; i < emplArray.length; i++) {
                newArrays[i] = emplArray[i];
                namesEmpl[i] = emplArray[i].getName();
                salary = salary.add(emplArray[i].getSalary());
            }

            newArrays[emplArray.length] = s3;
            namesEmpl[emplArray.length] = s3.getName();
            salary = salary.add(s3.getSalary());
            Arrays.sort(namesEmpl);

            if (checkRepeatNumber(newArrays)) {
                if (checkSalary(newArrays)) {
                    if (employeeList.size() > newArrays.length) {
                        if (!map.containsKey(Arrays.toString(namesEmpl))) {  //map.putIfAbsent -- Проблема была в ней. Нужно было проверку вынести за код, чтобы не перебирало вообще все варианты.
                            map.put(Arrays.toString(namesEmpl), "Средняя зарплата в I отделе возрасла до " + squad2.avarageSalaryWithTransfer(salary, newArrays.length)
                                    + ", во втором до " + squad1.avarageSalaryWithTransfer(salary.negate(), newArrays.length) + " | Элементов: " + namesEmpl.length);
                            numberOfValues++;
                            System.out.println("Найдено подходящих результатов: " + numberOfValues);
                            multiTransfer(employeeList, newArrays);
                        }
                    }
                }
            }
        }
    }
}
