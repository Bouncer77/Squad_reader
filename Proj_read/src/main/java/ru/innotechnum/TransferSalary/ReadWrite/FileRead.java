package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileRead {
    private String path;

   public FileRead(String filePath) {
       path=filePath;
    }

   private Employee parsingString(String[] mas) throws NumberFormatException { //Проверки на входные данные
       if (mas.length!=3) {
           System.out.println("Строка состоит не из трех частей, а из " + mas.length);
           return null;
       }

       if (mas[0].length()<2 || !mas[0].matches("[a-zA-Z ]+")) {
           System.out.println("Некорректное имя. Разрешены только буквы и пробелы");
           return null;
       }

       Employee rab = new Employee();   //Создаем нового работника
       rab.setName(mas[0]);             //Записываем имя
       try {
           int numberAfterPoint = mas[1].split("\\.")[1].length(); //считаем знаки после запятой
           if (numberAfterPoint!=2)
               throw new NumberFormatException("Должно быть две цифры после запятой. [X.xx], а в строке " + numberAfterPoint);

           BigDecimal sal = new BigDecimal(mas[1]); //BigDecimal не используется с try-with-resourcec
           rab.setSalary(sal); //записываем доход.
       } catch (NumberFormatException numEx){
           System.out.println("Некорректное число.\n" + numEx.getMessage());
           return null;
       }
       return rab;
   }

   public HashMap<String,Squad> reading() {  //Сама функция чтения
       java.io.FileReader rd;
       BufferedReader brd;
       String line; //Считываемая строка

       try {
           rd = new java.io.FileReader(path);
           brd = new BufferedReader(rd);
           line = brd.readLine();
       } catch (IOException ex) {
           System.out.println("Path to file not found");
           return null;
       }

       HashMap<String,Squad> hashMapSquads = new HashMap<String, Squad>(); //Возвращаемый
       int numberLine=0;     //Счетчик прочитанных строк
       while (line != null) {     //Пока строка есть - читаем
            numberLine++;     //Считаем строки для обозначения ошибочной строки.
            String[] mas;
            mas = line.split("/");    // имя/доход/отдел

                Employee rab = parsingString(mas);   //Создаем нового работника с проверкой входных параметров
                if (rab==null) {
                    System.out.println("Некорректная запись в строке " +numberLine+"\n"+line);
                    try {
                        line = brd.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                if (hashMapSquads.containsKey(mas[2])) {  //Ищем отдел с таким именем и если находим, добавляем сотрудника в него
                    hashMapSquads.get(mas[2]).addEmpl(rab);
                } else {          //Если нет, то создаем новый отдел и добавляем сотрудника
                    Squad squad = new Squad();
                    squad.setName(mas[2]);  //Имя отдела хранить в нем уже нет надобности, но на всякий оставлю
                    squad.addEmpl(rab);
                    hashMapSquads.put(mas[2],squad);

                }
            System.out.println(line);    //вывод что прочитали
            try {
                line = brd.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return hashMapSquads;
   }
} //Трай с ресурсами
//hashmap get or default для отделов