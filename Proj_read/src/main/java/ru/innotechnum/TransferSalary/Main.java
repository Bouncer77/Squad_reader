package ru.innotechnum.TransferSalary;

import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        FileReader reader = new FileReader();
       try{
           ArrayList<Squad> arSQ = reader.reading(args[0]);
//        System.out.println(arSQ.size());


        for(int i=0; i<arSQ.size(); i++) //Прогон по всем отделам с выводом данных
        {

            arSQ.get(i).testdisplay();
        }

        Squad sq1;
        Squad sq2;
        for(int i=0; i<arSQ.size();i++)   //Делал без переменных, с помощью функций. Не уверен что так правильно
            for(int j=0;j<arSQ.size();j++)
            {
                sq1 = arSQ.get(i);
                sq2 = arSQ.get(j);

                if(sq1.testAvarageSalary().compareTo(sq2.testAvarageSalary())==1)  //Сравнение средней зарплаты по отделам. Если в первом больше чем во втором...
                {
                    List<Employee> ar1 = sq1.getAr();
                    for(int k=0;k<ar1.size();k++)  //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                    {
                        if(ar1.get(k).getSalary().compareTo(sq1.testAvarageSalary())==-1 &&  ar1.get(k).getSalary().compareTo(sq2.testAvarageSalary())==1)
                        {
                            String answ = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + ar1.get(k).getName() +" С доходом "+ar1.get(k).getSalary()+ " в отдел " + sq2.getName(); //Формирование ответа.
                             answ += "\n Было в 1: " + sq1.testAvarageSalary() + " было в 2: " + sq2.testAvarageSalary() ;
                            answ+="\n Стало в 1: " +sq1.testAvarageSalary2(ar1.get(k).getSalary()) + " Стало в 2: " + sq2.testAvarageSalary2(ar1.get(k).getSalary().negate());
                            System.out.println(answ);
                        }
                    }
                }

            }
       }catch (NullPointerException ex) {System.out.println("Squads not found ");}
       catch (ArrayIndexOutOfBoundsException exep){System.out.println("Main args is empty");}
 //     BigDecimal ds = new BigDecimal(887);
 //     ds = ds.divide(new BigDecimal(3),6, 2);
 //     System.out.println(ds);
    }


}