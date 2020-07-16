package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FileRead {
    private String path;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

   public FileRead(String filePath) {
       path=filePath;
    }

   private Boolean parsingString(String[] mas) { //Проверки на входные данные
       if (mas.length!=3) {
           System.out.println("Строка состоит не из трех частей, а из " + mas.length);
           return false;
       }
       if (mas[0].length()<2 || !mas[0].matches("[a-zA-Z ]+")) {
           System.out.println("Некорректное имя. Разрешены только буквы и пробелы");
           return false;
       }
       try {
           int numberAfterPoint = mas[1].split("\\.")[1].length();  //считаем знаки после запятой
           if (numberAfterPoint!=2)
               throw new NumberFormatException("Должно быть два знака после запятой. [X.xx], а в строке " + numberAfterPoint);
           BigDecimal sal = new BigDecimal(mas[1]);
       } catch (NumberFormatException numEx){
           System.out.println("Некорректное число.\n" + numEx.getMessage());
           return false;
       }
       return true;
   }

   private String nextLine(String line){  //Дублирование. Используется два раза
       try {
           return bufferedReader.readLine();
       } catch (IOException e) {
           System.out.println("Ошибка чтения");
           return null;
       }
   }

   public Map<String,Squad> reading() {  //Сама функция чтения
       String line; //Считываемая строка
       Map<String,Squad> hashMapSquads = new HashMap<String, Squad>(); //Возвращаемый
       // Можно Filereader и bufReader счелать с try-with-resources
       try {
           fileReader = new java.io.FileReader(path);
           bufferedReader = new BufferedReader(fileReader);
           line = bufferedReader.readLine();
       } catch (IOException ex) {
           System.out.println("Path to file not found");
           return null;
       }

       int numberLine=0;     //Счетчик прочитанных строк
       while (line != null) {     //Пока строка есть - читаем
            numberLine++;     //Считаем строки для обозначения ошибочной строки.
            String[] mas;
            mas = line.split("/");    // имя/доход/отдел

           if(!parsingString(mas)) { // Проверка входных параметров. Если не прошел, пропускаем цикл
               System.out.println("Некорректная запись в строке " +numberLine+"\n"+line);
               line = nextLine(line); //След строка
               continue;
           } else {
               hashMapSquads.putIfAbsent(mas[2], new Squad(mas[2])); //Если такого нет, то создаем
               hashMapSquads.get(mas[2]).addEmpl(new Employee(mas[0],mas[1])); //Кладем в него сотрудника
           }

           /* Было раньше - старый код (удалить, для сравнения)
           Employee rab = new Employee();   //Создаем нового работника
           rab.setName(mas[0]);             //Записываем имя
           rab.setSalary(new BigDecimal(mas[1]));   //записываем доход.

           if (hashMapSquads.containsKey(mas[2])) {  //Ищем отдел с таким именем и если находим, добавляем сотрудника в него
               hashMapSquads.get(mas[2]).addEmpl(rab);
           } else {                                  //Если нет, то создаем новый отдел и добавляем сотрудника
               Squad squad = new Squad();
               squad.setName(mas[2]);
               squad.addEmpl(rab);
               hashMapSquads.put(mas[2],squad);
           }*/

            System.out.println(line);    //вывод прочитанной строки
            line = nextLine(line); //След строка
        }
       return hashMapSquads;
   }

   public void closer() {
       try {
           fileReader.close();
           bufferedReader.close();
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("Ошибка чтения. Closer");
       } catch (NullPointerException ex) {
           System.out.println("Ошибка. Reader не назначен. Closer");
       }
   }
}