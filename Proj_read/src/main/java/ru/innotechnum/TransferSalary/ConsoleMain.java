package ru.innotechnum.TransferSalary;

import ru.innotechnum.TransferSalary.ReadWrite.FileRead;
import ru.innotechnum.TransferSalary.ReadWrite.FileWrite;
import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleMain {

    public static void main(String[] args) {

       try{
           String path=null;
           FileRead reader = new FileRead(args[0]); //Создаем ридера для чтения файла с сотрудниками и передаем ему аргумент, содержащий путь до файла.
           if(args.length>1){path=args[1];}
           FileWrite fw = new FileWrite(path); //Запись в файл. Кидаем аргумент с путем для файла. Если null, то создает файл

           ArrayList<Squad> arSQ = reader.reading();

        for(int i=0; i<arSQ.size(); i++) //Прогон по всем отделам с выводом данных
        {
            arSQ.get(i).display();
        }

        Squad sq1;
        Squad sq2;
        String answ=""; //Формирование текста дял файла/вывода
        for(int i=0; i<arSQ.size();i++)   //Делал без переменных, с помощью функций. Не уверен что так правильно
            for(int j=0;j<arSQ.size();j++)
            {
                sq1 = arSQ.get(i);
                sq2 = arSQ.get(j);

                if(sq1.AvarageSalary().compareTo(sq2.AvarageSalary())==1)  //Сравнение средней зарплаты по отделам. Если в первом больше чем во втором...
                {
                    List<Employee> ar1 = sq1.getAr();
                    for(int k=0;k<ar1.size();k++)  //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                    {
                        if(ar1.get(k).getSalary().compareTo(sq1.AvarageSalary())==-1 &&  ar1.get(k).getSalary().compareTo(sq2.AvarageSalary())==1)
                        {
                             answ = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + ar1.get(k).getName() +" С доходом "+ar1.get(k).getSalary()+ " в отдел " + sq2.getName(); //Формирование ответа.
                             answ += "\n Было в 1: " + sq1.AvarageSalary() + " было в 2: " + sq2.AvarageSalary() ;
                            answ+="\n Стало в 1: " +sq1.AvarageSalaryWithTransfer(ar1.get(k).getSalary()) + " Стало в 2: " + sq2.AvarageSalaryWithTransfer(ar1.get(k).getSalary().negate());

                            fw.WriteAnswer(answ); //Кидаем на запись в файл вариант с переводом сотрудника
                            System.out.println(answ);
                        }
                    }
                }
            }
       }
       catch (NullPointerException ex) {System.out.println("Squads not found ");}
       catch (ArrayIndexOutOfBoundsException exep){System.out.println("Main args is empty");}
       catch (IOException e) { System.out.println("Main. IOExeption?"); }
    }


}