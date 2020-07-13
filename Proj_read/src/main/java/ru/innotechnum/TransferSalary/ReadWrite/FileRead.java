package ru.innotechnum.TransferSalary.readWrite;

import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FileRead {
    private ArrayList <Squad> arSQ = new ArrayList<Squad>();  //Список всех отделов
    private Squad squad = null;  //текущий отдел
    private String path;

   public FileRead(String filePath)
    {
        path=filePath;
    }

   public ArrayList <Squad> reading() throws IOException {  //Сама функция чтения
        java.io.FileReader rd;
        BufferedReader brd;
        String line; //Считываемая строка

       try{
           rd = new java.io.FileReader(path);
           brd = new BufferedReader(rd);
           line = brd.readLine();
       }catch (IOException ex) {System.out.println("Path to file not found"); return null;}


        int numberLine=0; //Счетчик прочитанных строк

        while (line != null){ //Пока строка есть - читаем

                numberLine++; //Считаем строки для обозначения ошибочной строки.

                String mas[];
                mas = line.split("/");   // имя/доход/отдел

                try {  //Траем я захватываю довольно внушительный участок кода, чтобы пропустить весь участок в случае ошибки и начать след. проход.
                    if(mas.length!=3) throw new Exception ("Mass not 3");  //генерит исключение на некорректную запись. Исключение позволяет пропустить весь след участок кода

                    Employee rab = new Employee();   //Создаем нового работника
                    rab.setName(mas[0]);             //Записываем имя
                    BigDecimal sal = new BigDecimal(mas[1]);
                    rab.setSalary(sal); //записываем доход.


                boolean find = false;
                for(int i= 0; i<arSQ.size();i++)  //перебираем список всех отделов
                {
                    if(arSQ.get(i).getName().equalsIgnoreCase(mas[2])) //Если находим уже созданный с таким именем (Добавил игнор регистра из-за возможных опечаток при наборе файла(Tech =tech)).
                    {
                        find=true;
                        arSQ.get(i).addEmpl(rab); //Записываем в список отдела нового сотрудника
                        break;
                    }
                }

            if(arSQ.size()==0 || !find) //Если до этого не было отделов и мы не нашли уже созданный отдел с таким же именем, то делаем новый.
            {
                squad = new Squad();
                squad.setName(mas[2]);
                squad.addEmpl(rab);
                arSQ.add(squad);
            }
                }
               catch (Exception e) {
                    System.out.println("ERROR AT LINE: " + numberLine); //В консоли пишется с какой строкой при чтении возникла ошибка
                }

            System.out.println(line);    //вывод что прочитали
            line = brd.readLine();
        }
        return arSQ;
    }
}