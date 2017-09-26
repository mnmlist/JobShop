package tabusearch;

import java.util.*;

public class Test {
/*    public static void main(String[] args){
        int t = 0;
        int lag=0;
        List<RPQ> rpqs = new ArrayList<>();
        List<RPQ> rpqsed = new ArrayList<>();
        RPQ rpq1=new RPQ();
        rpq1.setO(new Operation(0,null,null,1));
        rpq1.setR(10);
        rpq1.setP(5);
        rpq1.setQ(7);
        rpqs.add(rpq1);
        RPQ rpq2=new RPQ();
        rpq2.setO(new Operation(0,null,null,2));
        rpq2.setR(13);
        rpq2.setP(6);
        rpq2.setQ(26);
        rpqs.add(rpq2);
        RPQ rpq3=new RPQ();
        rpq3.setO(new Operation(0,null,null,3));
        rpq3.setR(11);
        rpq3.setP(7);
        rpq3.setQ(24);
        rpqs.add(rpq3);
        RPQ rpq4=new RPQ();
        rpq4.setO(new Operation(0,null,null,4));
        rpq4.setR(20);
        rpq4.setP(4);
        rpq4.setQ(21);
        rpqs.add(rpq4);
        RPQ rpq5=new RPQ();
        rpq5.setO(new Operation(0,null,null,5));
        rpq5.setR(30);
        rpq5.setP(3);
        rpq5.setQ(8);
        rpqs.add(rpq5);
        RPQ rpq6=new RPQ();
        rpq6.setO(new Operation(0,null,null,6));
        rpq6.setR(0);
        rpq6.setP(6);
        rpq6.setQ(17);
        rpqs.add(rpq6);
        RPQ rpq7=new RPQ();
        rpq7.setO(new Operation(0,null,null,7));
        rpq7.setR(30);
        rpq7.setP(2);
        rpq7.setQ(0);
        rpqs.add(rpq7);
        while (rpqsed.size() < 7) {
            RPQ rpqmin = null;
            int rmin = Integer.MAX_VALUE;
            int pmin = Integer.MAX_VALUE;
            for (RPQ rpq : rpqs) {
                if (t > rpq.getR()) {
                    if (pmin > rpq.getP()) {
                        pmin = rpq.getP();
                        rmin = rpq.getR();
                        rpqmin = rpq;
                    }
                } else {
                    if (rmin > rpq.getR()) {
                        rmin = rpq.getR();
                        rpqmin = rpq;
                    }
                }
            }
            if (t < rpqmin.getR()) {
                t = rpqmin.getR() + rpqmin.getP();
            } else {
                t = t + rpqmin.getP();
            }
            lag += Integer.max(t - rpqmin.getQ(), 0);
            rpqsed.add(rpqmin);
            rpqs.remove(rpqmin);
            System.out.println("o:"+rpqmin.getO().getId());
            System.out.println("lag:"+lag);
            System.out.println("t:"+t);
        }

    }*/
}
