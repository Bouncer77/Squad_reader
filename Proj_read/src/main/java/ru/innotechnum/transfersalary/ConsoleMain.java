package ru.innotechnum.transfersalary;

import ru.innotechnum.transfersalary.readwrite.FileRead;
import ru.innotechnum.transfersalary.readwrite.FileWrite;
import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleMain {
    public static void main(String[] args) {
            FileWrite writer = null;
            FileRead reader = null;
            String path = null;
            boolean rewrite = false;

            //Исправил плохой момент с try-cathe и оставил switch
            switch (args.length) {
                case 3:
                    rewrite=Boolean.parseBoolean(args[2]);
                    // Проскакивание дальше. Если не будет трех аргументов или придет false, то перезапись будет отключена
                case 2:
                    path=args[1];
                    // Проскакивание дальше. Если не будет второго аргумента с путем до файла с результатами, то path = null и в writer создастся файл по умолчанию
                case 1:
                    writer = new FileWrite(path, rewrite);
                    reader = new FileRead(args[0]);
                    break;
                case 0:
                    System.out.println("Запуск программы был осуществлен без аргумента");
                    return;
                default:    //В руководстве было написано, что default нужно писать в любом случае. Даже если он пустой. Зачем? ._.
            }

            Map<String,Squad> hashMapSquads = reader.reading();

            /*
                Если возвращает null, то в ридере что-то пошло не так.
                Значит завершаем работу метода.
                Ошибка пишется в консоль в ридере
            */
            if(hashMapSquads.isEmpty()) {
                System.out.println("Завершение работы программы");
                return;
            }
            //Прогон по всем отделам с выводом данных для консоли
            for (HashMap.Entry<String, Squad> entry : hashMapSquads.entrySet()) {
                entry.getValue().display();
            }
            calculate(hashMapSquads, writer);
            writer.closer(); //Подумать
    }

    //Вычисления и вывод результатов
    private static void calculate(Map<String,Squad> hashMapSquads, FileWrite fileWrite) {
        Squad squad1;
        Squad squad2;
        String answer=null;
        /*
            Формирование текста дял файла/вывода.
            Решил не использовать StringBuilder, А написал ниже answer= ""+""+""...
            Из за того, что String перегружен, то все ок и работать будет быстрее чем в билдере.
        * */
        for (Squad squadFirst : hashMapSquads.values())  //раньше использовал EntrySet()
            for (Squad squadTwo : hashMapSquads.values()) {
                if (squadFirst!=squadTwo) {  //Проверка, чтобы лишний раз не сравнивало отдел с ним же
                    squad1 = squadFirst;
                    squad2 = squadTwo;
                    if (squad1.avarageSalary().compareTo(squad2.avarageSalary())>0) {
                        List<Employee> employeeList = squad1.getListEmpl();
                        for (Employee employ : employeeList) {    //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                            if (employ.getSalary().compareTo(squad1.avarageSalary())<0 && employ.getSalary().compareTo(squad2.avarageSalary())>0) {
                                answer = "\n Перекидываем из " + squad1.getName() + " Сотрудника " + employ.getName() +" С доходом "+ employ.getSalary()+ " в отдел " + squad2.getName()
                                + "\n Было в 1: " + squad1.avarageSalary() + " было в 2: " + squad2.avarageSalary()
                                + "\n Стало в 1: " +squad1.avarageSalaryWithTransfer(employ.getSalary())
                                + " Стало в 2: " + squad2.avarageSalaryWithTransfer(employ.getSalary().negate());
                                fileWrite.writeAnswer(answer);     //Кидаем на запись в файл вариант с переводом сотрудника
                                System.out.println(answer);
                            }
                        }
                    }
                }
            }

    }
}