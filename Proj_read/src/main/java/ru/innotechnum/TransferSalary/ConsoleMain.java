package ru.innotechnum.transfersalary;

import ru.innotechnum.transfersalary.readwrite.FileRead;
import ru.innotechnum.transfersalary.readwrite.FileWrite;
import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleMain {
    static final int CHARS_AFTER_POINT = 6;  //Кол-во знаков после запятой для расчетов. 6 написал от балды

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
            reader.closer(); //Закрываем подключение
            /*
                Если возвращает null, то в ридере что-то пошло не так.
                Значит завершаем работу метода.
                Ошибка пишется в консоль в ридере, т.ч дублировать тут не имеет смысла
            */
            if(hashMapSquads==null) {
                return;
            }
            //Прогон по всем отделам с выводом данных для консоли
            for (HashMap.Entry<String, Squad> entry : hashMapSquads.entrySet()) {
                entry.getValue().display(CHARS_AFTER_POINT);
            }
            calculate(hashMapSquads, writer);
            writer.closer();
    }

    //Вычисления и вывод результатов
    private static void calculate(Map<String,Squad> hashMapSquads, FileWrite fileWrite) {
        Squad sq1;
        Squad sq2;
        String answer="";
        /*
            Формирование текста дял файла/вывода.
            Решил не использовать StringBuilder, А написал ниже answer= ""+""+""...
            Из за того, что String перегружен, то все ок и работать будет быстрее чем в билдере.
        * */
        for (HashMap.Entry<String, Squad> entryFirst : hashMapSquads.entrySet())
            for (HashMap.Entry<String, Squad> entryTwo : hashMapSquads.entrySet()) {
                if (entryFirst!=entryTwo) {  //Проверка, чтобы лишний раз не сравнивало отдел с ним же
                    sq1 = entryFirst.getValue();
                    sq2 = entryTwo.getValue();
                    if (sq1.avarageSalary(CHARS_AFTER_POINT).compareTo(sq2.avarageSalary(CHARS_AFTER_POINT))>0) { //Нет проверки на тот же отдел, т.к зп в одном отделе не может отличаться от своей же.
                        List<Employee> employeeList = sq1.getListEmpl();
                        for (int k=0;k<employeeList.size();k++) { //Ищем из того отдела где средняя зп больше, людей у которых зп ниже средней, но выше чем средняя зп в другом отделе.
                            if (employeeList.get(k).getSalary().compareTo(sq1.avarageSalary(CHARS_AFTER_POINT))<0 &&  employeeList.get(k).getSalary().compareTo(sq2.avarageSalary(CHARS_AFTER_POINT))>0) {
                                answer = "\n Перекидываем из " + sq1.getName() + " Сотрудника " + employeeList.get(k).getName() +" С доходом "+employeeList.get(k).getSalary()+ " в отдел " + sq2.getName()
                                + "\n Было в 1: " + sq1.avarageSalary(CHARS_AFTER_POINT) + " было в 2: " + sq2.avarageSalary(CHARS_AFTER_POINT)
                                + "\n Стало в 1: " +sq1.avarageSalaryWithTransfer(employeeList.get(k).getSalary(),CHARS_AFTER_POINT)
                                + " Стало в 2: " + sq2.avarageSalaryWithTransfer(employeeList.get(k).getSalary().negate(),CHARS_AFTER_POINT);
                                fileWrite.writeAnswer(answer); //Кидаем на запись в файл вариант с переводом сотрудника
                                System.out.println(answer);
                            }
                        }
                    }
                }
            }

    }
}