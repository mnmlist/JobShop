package tabusearch;

import java.util.*;


public class SBP {
    private static int ADD_0_NODE = 0;

    public static Solution getInitSol(Problem p) {
        Solution s = new Solution(p);
        int MACHINE_NUM = ADD_0_NODE == 1 ? p.getNumberOfMachines() - 1 : p.getNumberOfMachines();
        Set<Machine> machinesed = new HashSet<>();
        Set<Machine> machines = new HashSet<>();

        for (Operation o : p.getV()) {
            if (o.getMachine() != null) {
                if (o.getMachine().getId() == 0 && ADD_0_NODE == 1)
                    continue;
                machines.add(o.getMachine());
            }
        }
        while (machinesed.size() < MACHINE_NUM) {
            Machine mbottleneck = findBottleneck(machines, s);
            machinesed.add(mbottleneck);
            machines.remove(mbottleneck);
        }
        return s;
    }

    private static Machine findBottleneck(Set<Machine> machines, Solution s) {
        Machine mbottleneck = null;
        LinkedList<Operation>[] e = s.getE();
        Map<Machine, Integer> maplag = new HashMap<>();
        Map<Machine, List<RPQ>> maprpq = new HashMap<>();
        for (Machine m : machines) {
            int lag = 0;
            int mid = m.getId();
            List<RPQ> rpqs = new ArrayList<>();
            List<RPQ> rpqsed = new ArrayList<>();
            for (Operation o : e[mid]) {
                RPQ rpq = new RPQ();
                rpq.setO(o);
                rpq.setP(o.getDuration());
  /*              rpq.setR();
                rpq.setQ();*/
                rpqs.add(rpq);
            }


            int t = 0;
            while (rpqsed.size() < e[mid].size()) {
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
            }


            maplag.put(m, lag);
            maprpq.put(m, rpqsed);
        }
        int maxlag = 0;
        for (Map.Entry<Machine, Integer> entry : maplag.entrySet()) {
            if (entry.getValue() > maxlag) {
                maxlag = entry.getValue();
                mbottleneck = entry.getKey();
            }
        }
        for (RPQ rpq : maprpq.get(mbottleneck)) {
            s.scheduleOperationLeft(rpq.getO());
        }
        return mbottleneck;
    }

/*    private static RPQ findRPQ(List<RPQ> rpqs) {
        RPQ rpq = new RPQ();
        return rpq;
    }*/
}

class RPQ {
    private Operation o;
    private int r;
    private int p;
    private int q;

    public Operation getO() {
        return o;
    }

    public void setO(Operation o) {
        this.o = o;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }
}