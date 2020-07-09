import java.util.LinkedList;

public class Squad {
    private LinkedList<String[]> ar = new LinkedList<String[]>(); //Данные отдела (ФИО/ЗП)
    private String name = null; //Название отдела


    public void setAr(LinkedList<String[]> ar) {  //Пока добавил только сеттеры для выполнения первичной задачи
        this.ar = ar;
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
            //  int zp =Integer.parseInt(ar.get(i)[1]);

            System.out.println("TD "+i+":   "+ ar.get(i)[0] + " " + ar.get(i)[1]);
        }

    }
}
