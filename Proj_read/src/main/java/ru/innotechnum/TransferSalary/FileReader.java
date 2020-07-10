package ru.innotechnum.TransferSalary;

import ru.innotechnum.TransferSalary.department.Employee;
import ru.innotechnum.TransferSalary.department.Squad;

import java.io.BufferedReader;
import java.io.IOException;
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

        while (line != null){ //Пока строка есть - читаем

                String mas[];
                mas = line.split("/");   // имя/доход/отдел

                Employee rab = new Employee();   //Создаем нового работника
                rab.setName(mas[0]);             //Записываем имя
                rab.setSalary(mas[1]); //записываем доход. !!!!!!!!!ДОБАВИТЬ ОБРАБОТЧИК!!!!!!!!!

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

            System.out.println(line);    //вывод что прочитали
            line = brd.readLine();
        }

        //ar.clear();
        return arSQ;
    }




}