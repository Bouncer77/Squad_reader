package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.math.BigDecimal;
import java.util.*;

public class MultiChoice {
    private Map<String, String> map = new HashMap<>();  //Первый String - отсортированный массив имен в строке. Второй - Текст с информацией о переводе (Сколько ср.зп была в отделе и сколько стала)
    private Squad squad1;
    private Squad squad2;

    public MultiChoice(Squad squad1, Squad squad2) {
        this.squad1=squad1;
        this.squad2=squad2;
    }

    public StringBuilder additionalResponse(List<Employee> listEmpl) { //
        for(Employee employee : listEmpl) {                     // [ Если Убрать этот код и при первом вызове multiTransfer подать вторым аргументом пустой массив
            Employee[] emplArray = new Employee[] {employee};   // [ То выведет все комбинации + одиночные переводы. Т.к одиночные переводы в основном блоке, а групповые в дополнительном - код пока оставил.
            multiTransfer(listEmpl,emplArray);                  // [ Пока не решил, стоит ли выводить информацию в разных блоках или в одном.
        }
        StringBuilder answer = new StringBuilder("\nДополнитльные варианты переводов: \n");
        if(!map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                answer.append("\n" + entry.getKey() + " " + "{" + entry.getValue() + "}");
            }
        } else {
            answer.append("\n Отсутствуют ");
        }
        System.out.println(answer);
        return answer;
    }

    private Boolean checkRepeatNumber(Employee[] us) {  //Проверка, чтобы не выбирать одних и тех же сотрудников для перевода.
        Boolean as = true;
        for(int d =0; d<us.length;d++)
            for(int i =0; i<us.length;i++) {
                if(us[d].getName().equals(us[i].getName()) && d!=i) {as = false;}
            }
        return as;
    }


    private Boolean checkSalary(Employee[] emplArr, Employee employee) {  //Проверка на возрастание средней зарплаты в обоих отделах при переводе группы сотрудников. employee устарела. Она уже есть в emplArr
        BigDecimal i=new BigDecimal(0);
        for(Employee rr: emplArr) {
            i = i.add(rr.getSalary());
        }
        boolean bl = false;
        if(squad2.getListEmpl().size()>emplArr.length)
        if(squad2.avarageSalaryWithTransfer(i,emplArr.length).compareTo(squad2.avarageSalary())>0
                && squad1.avarageSalaryWithTransfer(i.negate(),emplArr.length).compareTo(squad1.avarageSalary())>0) {
        bl = true;
        } else {
        bl = false; }
        return bl;
    }

    private void multiTransfer(List<Employee> employeeList, Employee[] emplArray) {
        for(Employee s3 :employeeList) {
            BigDecimal salary= BigDecimal.valueOf(0);  //Сумма переводящихся
            Employee[] newArrays = new Employee[emplArray.length+1];  //newArrays = Выбранная для перевода пачка сотрудников
            String[] namesEmpl = new String[emplArray.length+1];     //namesEmpl - список сотрудников поименно в hashmap.
            for(int i=0; i<emplArray.length; i++){
                newArrays[i]=emplArray[i];
                namesEmpl[i]=emplArray[i].getName();
                salary= salary.add(emplArray[i].getSalary());
            }

            newArrays[emplArray.length]=s3;
            namesEmpl[emplArray.length]=s3.getName();
            salary= salary.add(s3.getSalary());

            if(checkRepeatNumber(newArrays)) {
                if(checkSalary(newArrays, s3)) {
                    if(employeeList.size()>=newArrays.length) {
                        multiTransfer(employeeList, newArrays);
                        Arrays.sort(namesEmpl);
                        map.putIfAbsent("" + Arrays.toString(namesEmpl), "Средняя зарплата в I отделе возрасла до " + squad2.avarageSalaryWithTransfer(salary,newArrays.length)
                                + ", во втором до " + squad1.avarageSalaryWithTransfer(salary.negate(),newArrays.length));
                    }
                }
            }
        }
    }
}