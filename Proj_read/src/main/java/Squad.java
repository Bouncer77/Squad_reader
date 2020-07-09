import java.util.ArrayList;
import java.util.LinkedList;

public class Squad {
    private ArrayList<Rabotnik> ar = new ArrayList<Rabotnik>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела


    public void setAr(ArrayList<Rabotnik> as) {  //Пока добавил только сеттеры для выполнения первичной задачи
        ar.addAll(0,as);
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void testdisplay()
    {
        System.out.println("\ntestdisplay ->" + name + ": \n");
        for(int i=0; i<ar.size();i++)
        {
           System.out.println("TD "+i+":   "+ ar.get(i).getName() + " " + ar.get(i).getZp());
        }

    }
}
