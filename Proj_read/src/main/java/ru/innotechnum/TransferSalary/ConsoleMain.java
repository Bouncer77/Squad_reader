package ru.innotechnum.TransferSalary;

import ru.innotechnum.TransferSalary.readWrite.FileRead;
import ru.innotechnum.TransferSalary.readWrite.FileWrite;
import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleMain {

    public static void main(String[] args) {
        try {  //!Не нужен трай
            FileWrite fw = null;
            FileRead reader = null;
            String path = null;
            boolean ara = false;

            switch (args.length)
            {
                case 3: ara=Boolean.parseBoolean(args[2]);  //Отвечает за перезапись файла после каждого запуска программы. По дефолту перезаписывает. True - будет добавлять в конец
                case 2:  path=args[1]; //Путь для файла с результатами. Если null, то создает файл
                case 1:  fw = new FileWrite(path, ara);
                reader = new FileRead(args[0]); break; //Создаем ридера для чтения файла с сотрудниками и передаем ему аргумент, содержащий путь до файла.
                case 0: throw new ArrayIndexOutOfBoundsException();
            }
            ArrayList<Squad> arSQ = reader.reading();

            for (int i=0; i<arSQ.size(); i++) { //Прогон по всем отделам с выводом данных
                arSQ.get(i).display();
            }
//Отдельная
            Squad sq1;
            Squad sq2;
            String answ=""; //Формирование текста дял файла/вывода
            for (int i=0; i<arSQ.size();i++)   //Делал без переменных, с помощью функций. Не уверен что так правильно
                for (int j=0;j<arSQ.size();j++) {
                    sq1 = arSQ.get(i);
                    sq2 = arSQ.get(j);
                    //Проверка на тот же отдел.
                    if (sq1.avarageSalary().compareTo(sq2.avarageSalary())==1) {  //Сравнение средней зарплаты по отделам. Если в первом больше чем во втором...
                        List<Employee> ar1 = sq1.getAr();
                        for (int k=0;k<ar1.size();k++) { //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                            if (ar1.get(k).getSalary().compareTo(sq1.avarageSalary())==-1 &&  ar1.get(k).getSalary().compareTo(sq2.avarageSalary())==1) {
                                answ = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + ar1.get(k).getName() +" С доходом "+ar1.get(k).getSalary()+ " в отдел " + sq2.getName(); //Формирование ответа.
                                answ += "\n Было в 1: " + sq1.avarageSalary() + " было в 2: " + sq2.avarageSalary() ;
                                answ+="\n Стало в 1: " +sq1.avarageSalaryWithTransfer(ar1.get(k).getSalary()) + " Стало в 2: " + sq2.avarageSalaryWithTransfer(ar1.get(k).getSalary().negate());
                                fw.writeAnswer(answ); //Кидаем на запись в файл вариант с переводом сотрудника
                                System.out.println(answ);
                            }
                        }
                    }
                }
        }
        catch (NullPointerException ex) {
            System.out.println("Squads not found ");
        }
        catch (ArrayIndexOutOfBoundsException exep) {
            System.out.println("Main args is empty");
        }
    }
//отдельная
//StringBuilder.
}