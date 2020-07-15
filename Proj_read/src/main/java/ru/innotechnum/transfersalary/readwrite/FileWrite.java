package ru.innotechnum.transfersalary.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class FileWrite {
    private String path;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private File file;
    private boolean adding;

    public FileWrite(String path, boolean adding) {
            this.path=path;
            this.adding = adding;
            filecreator();
    }

    //Создание файла для записи результатов или же поиск его и добавление первой строки с датой
    private void filecreator() {
        try {
            if (path==null || path.equals("-")) {  //Создает новый файл, если не был указан в аргументах (пересоздает уже созданный там)
                file = new File("SquadTransfer.txt"); System.out.println("File [SquadTransfer.txt] was created using the default path " + file.getAbsolutePath() );
            } else {
                file = new File(path); System.out.println("File Path "+file.getAbsolutePath());
            }
            fileWriter = new FileWriter(file, adding); //true чтобы не перезаписывать файл, а добавлять в конец.
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("_______________________"+new Date().toString()+"_________________________");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("File for write not found");
        }
    }


    public void writeAnswer(String answer) {
        try {
            String ans[] = answer.split("\n");  //Чтобы ответ был не в одну строку, а блоком. Так читабельней
            for (int h=1;h<ans.length;h++) {
                bufferedWriter.write(ans[h]);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("IOException Записи результата");
        } catch (NullPointerException ex) {   //Не должно выскакивать, но на будущее впишу.
            ex.printStackTrace();
            System.out.println("Запись невозможна, т.к не был инициализирован filecreator()");
        }

    }

    public void closer()
    {
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка закрытия FileWriter");
        } catch (NullPointerException ex) {  //Не выбрасывается, но решил добавить. Мало ли в будущем этот метод будет вызываться в другом порядке, нежели в ConsoleMain
            ex.printStackTrace();
            System.out.println("Ошибка закрытия FileWriter = null?");
        }
    }
}
