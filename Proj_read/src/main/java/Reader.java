import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Reader {
    private ArrayList <Squad> arSQ = new ArrayList<Squad>();  //Список всех отделов
    private LinkedList<String[]> ar = new LinkedList<String[]>(); //Данные отдела (ФИО/ЗП)
    private String sqName = null; // Название текущего отдела
    private Squad squad = null;  //текущий отдел


    private void remember_squad()  //Запись отдела. Этот код дважды повторяется (По ходу выполнения чтения и один в конце), т.ч вынес в отдельный метод
    {
        squad.setAr(ar);
        arSQ.add(squad);
    }

    ArrayList <Squad> reading() throws IOException {  //Сама функция чтения
        FileReader rd;
        BufferedReader brd;
        String line; //Считываемая строка

        rd = new FileReader("C:\\Users\\maxim\\IdeaProjects\\Squad_reader\\Proj_read\\src\\main\\resources\\squads.txt");
        brd = new BufferedReader(rd);
        line = brd.readLine();

        while (line != null){ //Пока строка есть - читаем

            if(sqName==null || !line.contains("/"))   //Запись наименования отряда
            {
                if(squad!=null)  //Если отдел уже был и мы начинаем смотреть новый, то уже созданные записи записываем и уже тогда переходим к новому.
                    remember_squad();

                sqName=line;
                squad = new Squad(); //Создание нового отдела
                squad.setName(sqName);  //Даем ему имя
                ar.clear(); //Чистим старые записи

            }
            else {
                String mas[] = new String[2];
                mas = line.split("/");   //Запись в "отдел" имя/доход

                ar.add(mas);
            }
            System.out.println(line);    //вывод что прочитали
            line = brd.readLine();
        }

        if(squad!=null)  //Когда прочтение заканчивается и финальная строка null - записываем уже поулченные данные, чтобы не потерять и завершаем программу
            remember_squad();

        return arSQ;
    }




}