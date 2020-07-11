package ru.innotechnum.TransferSalary.ReadWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileWrite {
    String path;
    FileWriter fw;
    BufferedWriter bw;
    File file;

   public FileWrite(String path)
    {
        try {
            if(path==null)
            {file = new File("SquadTransfer.txt"); file.createNewFile(); System.out.println("File [SquadTransfer.txt] has been created on default path " + file.getAbsolutePath() );} //Создает новый файл, если не был указан в аргументах (пересоздает уже созданный там)
            else
            {file = new File(path);System.out.println("File [....txt] "+file.getAbsolutePath());}

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File for write not found");
        }
    }


    public void CreateFile(String answer) {
        try {
            String ans[] = answer.split("\n");  //Чтобы ответ был не в одну строку. Так читабельней
            bw.newLine();
            for(int h=1;h<ans.length;h++)
            {
                bw.write(ans[h]);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
           System.out.println("IOException FileWrite");
        }

    }
}
