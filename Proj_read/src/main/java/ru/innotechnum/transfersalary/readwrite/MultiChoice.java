package ru.innotechnum.transfersalary.readwrite;

import ru.innotechnum.transfersalary.department.Employee;
import ru.innotechnum.transfersalary.department.Squad;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiChoice {
     Map<String, String> map = new HashMap<>();  //Первый String[] - массив строк с именами сотрудников
    Squad squad1;
    Squad squad2;
    public Map<String, String> map_creation(List<Employee> listEmpl, Squad squad1,Squad squad2) {
        this.squad1=squad1;
        this.squad2=squad2;
       // Integer[] ls = new Integer[]{1,3,5,7,9};
        for(Employee s1 : listEmpl) {
            Employee[] ir2 = new Employee[] {s1};

            if(provChislo(ir2, s1))
                if(provEquals(ir2)) {
                    Arrays.sort(ir2);
                   // map.putIfAbsent(Arrays.toString(new String[]{s1.getName()}), "Зарплата чуть-чуть выросла во 2");
                    voda(listEmpl,ir2);

                }
        }
        System.out.println("\nТакже возможны следующие варианты переводов: \n");

       // for( : map.entrySet()){
            System.out.println(map.entrySet());
        //}
        return map;
    }


    public Boolean provEquals(Employee[] us) {
        Boolean as = true;
        for(int d =0; d<us.length;d++)
            for(int i =0; i<us.length;i++) {

                if(us[d].getName().equals(us[i].getName()) && d!=i) {as = false; }
            }
        return as;
    }


    public Boolean provChislo(Employee[] us, Employee ar) {
        BigDecimal i=new BigDecimal(0);
        for(Employee rr: us)
        {
            i.add(rr.getSalary());
        }
        i.add(ar.getSalary());
        boolean bl = false;
        //if(new BigDecimal(700).compareTo(i)>0)
        if(squad1.avarageSalaryWithTransfer(i,1).compareTo(squad1.avarageSalary())>0
                && squad2.avarageSalaryWithTransfer(i.negate(),1).compareTo(squad2.avarageSalary())>0)
        {bl = true;}
        else
        {bl = false;}
        return bl;
    }

    public void voda(List<Employee> ls,Employee[] uss) {

        for(Employee s3 :ls) {
            BigDecimal salary=new BigDecimal("55");
            Employee[] ir = new Employee[uss.length+1];
            String[] ans = new String[uss.length+1];
            for(int i=0; i<uss.length; i++){

                ir[i]=uss[i];
                ans[i]=uss[i].getName();
                salary= salary.add(uss[i].getSalary());
         //       System.out.println("___" + salary + " salary + getsalary" + uss[i].getSalary());
            }

            ir[uss.length]=s3;
            ans[uss.length]=s3.getName();
            salary= salary.add(s3.getSalary());
           // System.out.println("___" + salary + " salary + getsalary s3" + s3.getSalary());
            if(provEquals(ir))
            {
                if(provChislo(ir, s3)) {
                    if(ls.size()>=ir.length) {
                        voda(ls, ir);
                        Arrays.sort(ans);
                        map.putIfAbsent(Arrays.toString(ans), "Средняя зарплата в I отделе стала " + squad2.avarageSalaryWithTransfer(salary,ir.length)
                                + ", во втором " + squad1.avarageSalaryWithTransfer(salary.negate(),ir.length));
                        // System.out.println();
                    }
                    //System.out.println(ans);
                }
            }
        }
    }
}
