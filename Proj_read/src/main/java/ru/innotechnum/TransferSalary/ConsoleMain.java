package ru.innotechnum.transfersalary;

import ru.innotechnum.transfersalary.readwrite.FileRead;
import ru.innotechnum.transfersalary.readwrite.FileWrite;
import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.util.HashMap;
import java.util.List;

public class ConsoleMain {
//C:\Users\maxim\IdeaProjects\Squad_reader\Proj_read\squads.txt  C:\Users\maxim\Desktop\An\answerW.txt true
    public static void main(String[] args) {
            FileWrite writer = null;
            FileRead reader = null;
            String path = null;
            boolean ara = false;

            switch (args.length) {
                case 3: ara=Boolean.parseBoolean(args[2]);  //Отвечает за перезапись файла после каждого запуска программы. По дефолту перезаписывает. True - будет добавлять в конец
                case 2:  path=args[1]; //Путь для файла с результатами. Если null, то создает файл
                case 1:  writer = new FileWrite(path, ara);
                reader = new FileRead(args[0]); break; //Создаем ридера для чтения файла с сотрудниками и передаем ему аргумент, содержащий путь до файла.
                case 0: System.out.println("Запуск программы был осуществлен без аргумента"); return;
                default:    //В руководстве было написано, что default нужно писать в любом случае. Даже если он пустой. Зачем? ._.
            }

            HashMap<String,Squad> hashMapSquads = reader.reading();
            /*
            Если возвращает null, то в ридере что-то пошло не так.
            Значит завершаем работу метода.
            Ошибка пишется в консоль в ридере, т.ч дублировать тут не имеет смысла
            */
            if(hashMapSquads==null) {
                return;
            }

            for (HashMap.Entry<String, Squad> entry : hashMapSquads.entrySet()) { //Прогон по всем отделам с выводом данных
                entry.getValue().display();
            }
            calculate(hashMapSquads,writer);
            writer.closer();
    }

    private static void calculate(HashMap<String,Squad> hashMapSquads, FileWrite fileWrite)
    {
        Squad sq1;
        Squad sq2;
        String answ=""; //Формирование текста дял файла/вывода
        for (HashMap.Entry<String, Squad> entryFirst : hashMapSquads.entrySet())   //Делал без переменных, с помощью функций. Не уверен что так правильно
            for (HashMap.Entry<String, Squad> entryTwo : hashMapSquads.entrySet()) {
                sq1 = entryFirst.getValue();
                sq2 = entryTwo.getValue();
                //Проверка на тот же отдел.
                if (sq1.avarageSalary().compareTo(sq2.avarageSalary())>0) {  //Сравнение средней зарплаты по отделам. Если в первом больше чем во втором... Проверка на тот же отдел не нужна, т.к своя зп не может быть больше своей же зп
                    List<Employee> employeeList = sq1.getAr();
                    for (int k=0;k<employeeList.size();k++) { //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                        if (employeeList.get(k).getSalary().compareTo(sq1.avarageSalary())==-1 &&  employeeList.get(k).getSalary().compareTo(sq2.avarageSalary())==1) { //Формирование ответа.
                            answ = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + employeeList.get(k).getName() +" С доходом "+employeeList.get(k).getSalary()+ " в отдел " + sq2.getName()
                            + "\n Было в 1: " + sq1.avarageSalary() + " было в 2: " + sq2.avarageSalary()
                            + "\n Стало в 1: " +sq1.avarageSalaryWithTransfer(employeeList.get(k).getSalary()) + " Стало в 2: " + sq2.avarageSalaryWithTransfer(employeeList.get(k).getSalary().negate());
                            fileWrite.writeAnswer(answ); //Кидаем на запись в файл вариант с переводом сотрудника
                            System.out.println(answ);
                        }
                    }
                }
            }

    }
//отдельная
//StringBuilder.
}