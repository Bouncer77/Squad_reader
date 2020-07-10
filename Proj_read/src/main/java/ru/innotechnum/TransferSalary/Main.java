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
        ArrayList<Squad> arSQ = reader.reading();
        System.out.println(arSQ.size());
        for(int i=0; i<arSQ.size(); i++) //Прогон по всем отделам с выводом данных
        {

            arSQ.get(i).testdisplay();
        }

        Squad sq1;
        Squad sq2;
        for(int i=0; i<arSQ.size();i++)
            for(int j=0;j<arSQ.size();j++)
            {
                sq1 = arSQ.get(i);
                sq2 = arSQ.get(j);

                if(sq1.testAvarageSalary().compareTo(sq2.testAvarageSalary())==1)  //Если в предыдущем отделе средняя зп больше. Теперь сделать еще для варианта если меньше.
                {
                    List<Employee> ar1 = sq1.getAr();
                    for(int k=0;k<ar1.size();k++)
                    {
                        if(ar1.get(k).getSalary().compareTo(sq1.testAvarageSalary())==-1 &&  ar1.get(k).getSalary().compareTo(sq2.testAvarageSalary())==1)
                        {
                            String answ = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + ar1.get(k).getName() +" С доходом "+ar1.get(k).getSalary()+ " в отдел " + sq2.getName();
                             answ += "\n Было в 1: " + sq1.testAvarageSalary() + " было в 2: " + sq2.testAvarageSalary() ;
                            answ+="\n Стало в 1: " +sq1.testAvarageSalary2(ar1.get(k).getSalary()) + " Стало в 2: " + sq2.testAvarageSalary2(ar1.get(k).getSalary().negate());
                            System.out.println(answ);
                        }
                    }
                }

            }
 //     BigDecimal ds = new BigDecimal(887);
 //     ds = ds.divide(new BigDecimal(3),6, 2);
 //     System.out.println(ds);
    }


}