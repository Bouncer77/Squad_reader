package ru.innotechnum.TransferSalary;

import com.sun.deploy.security.SelectableSecurityManager;
import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FileReader {
    private ArrayList <Squad> arSQ = new ArrayList<Squad>();  //Список всех отделов
    private Squad squad = null;  //текущий отдел



    ArrayList <Squad> reading() throws IOException {  //Сама функция чтения
        java.io.FileReader rd;
        BufferedReader brd;
        String line; //Считываемая строка

        rd = new java.io.FileReader("C:\\Users\\maxim\\IdeaProjects\\Squad_reader\\Proj_read\\src\\main\\resources\\squads.txt");
        brd = new BufferedReader(rd);
        line = brd.readLine();
        int numberLine=0; //Счетчик прочитанных строк

        while (line != null){ //Пока строка есть - читаем

                numberLine++; //Считаем строки для обозначения ошибочной строки.

                String mas[];
                mas = line.split("/");   // имя/доход/отдел

                if(mas.length==3)
                {
                try {
                    Employee rab = new Employee();   //Создаем нового работника
                    rab.setName(mas[0]);             //Записываем имя
                    BigDecimal sal = new BigDecimal(mas[1]);
                    rab.setSalary(sal); //записываем доход.




                boolean find = false;
                for(int i= 0; i<arSQ.size();i++)  //перебираем список всех отделов
                {
                    if(arSQ.get(i).getName().equals(mas[2])) //Если находим уже созданный с таким именем
                    {
                        find=true;
                        arSQ.get(i).addEmpl(rab); //Записываем в список отдела нового сотрудника
                        break;
                    }
                }

            if(arSQ.size()==0 || !find)
            {
                squad = new Squad();
                squad.setName(mas[2]);
                squad.addEmpl(rab);
                arSQ.add(squad);
            }
                }
                catch (java.lang.NumberFormatException ex)
                {
                    System.out.println("ERROR AT LINE: " + numberLine);
                  //  line = brd.readLine();  //В случае ошибки переходим к след строке
                   // break A;
                }
                }else
                {
                    System.out.println("ERROR AT LINE TMS: " + numberLine);
                }

            System.out.println(line);    //вывод что прочитали
            line = brd.readLine();
        }

        //ar.clear();
        return arSQ;
    }




}