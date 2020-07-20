package ru.innotechnum.transfersalary.readwrite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultiChoice {
     StringBuilder sb=new StringBuilder(" ");
     Map<String, String> map = new HashMap<>();

    public Map<String, String> map_creation() {
        Integer[] ls = new Integer[]{1,3,5,7,9};
        String ans = "";
        for(Integer s1 : ls) {
            Integer[] ir2 = new Integer[] {s1};
            ans += "\n Vivt: "+ s1 + " " ;
            map.putIfAbsent(Arrays.toString(new Integer[]{s1}), String.valueOf(s1));
            if(provChislo(ir2, s1))
                if(provEquals(ir2)) {
                    ans +="\n Vivw: "+ Arrays.toString(ir2);
                    Arrays.sort(ir2);
                    map.putIfAbsent(Arrays.toString(ir2), Arrays.toString(ir2));

                    ans +=voda(ls,ir2,ans);

                }
        }
        System.out.println(ans);
        System.out.println(sb);
        System.out.println("\nАРРРРРРРРРРРРРРРРРРРРРР\n");

        for(String sb : map.values()){
            System.out.println(sb);
        }
        return map;
    }


    public Boolean provEquals(Integer[] us) {
        Boolean as = true;
        for(int d =0; d<us.length;d++)
            for(int i =0; i<us.length;i++) {

                if(us[d].equals(us[i]) && d!=i) {as = false; }
            }
        return as;
    }


    public Boolean provChislo(Integer[] us, Integer ar) {
        int i=0;
        for(Integer rr: us)
        {
            i+=rr;
        }
        i+=ar;
        boolean bl = false;
        if(33<=i)
        {bl = false;}
        else
        {bl = true;}
        return bl;
    }

    public String voda(Integer[] ls,Integer[] uss, String ans) {

        for(Integer s3 :ls) {
            Integer[] ir = new Integer[uss.length+1];
            for(int i=0; i<uss.length; i++){
                ir[i]=uss[i];
            }

            ir[uss.length]=s3;
            //System.out.println(ir.length + " asd " + uss.length);
            if(provEquals(ir))
            {
                if(provChislo(ir, s3)) {
                    ans =" \n Viv: " + Arrays.toString(ir);
                    if(ls.length>=ir.length) {
                        sb.append(voda(ls, ir, ans));
                        Arrays.sort(ir);
                        map.putIfAbsent(Arrays.toString(ir), Arrays.toString(ir));
                        // System.out.println();
                    }
                    //System.out.println(ans);
                }
            }
        }

        return ans;
    }
}
