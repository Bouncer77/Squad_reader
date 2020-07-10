package ru.innotechnum.TransferSalary;

import ru.innotechnum.TransferSalary.department.Squad;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader();
        ArrayList<Squad> arSQ = reader.reading();
        System.out.println(arSQ.size());
        for(int i=0; i<arSQ.size(); i++) //Прогон по всем отделам с выводом данных
        {

            arSQ.get(i).testdisplay();
        }
    }
}