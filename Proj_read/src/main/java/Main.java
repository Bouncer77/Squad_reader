import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        ArrayList<Squad> arSQ = reader.reading();

        for(int i=0; i<arSQ.size(); i++) //Прогон по всем отделам с выводом данных
        {
            arSQ.get(i).testdisplay();
        }
    }
}