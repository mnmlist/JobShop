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
        Map<Machine, Integer> map = new HashMap<>();
        for (Machine m : machines) {
            int lag = 0;
            int mid = m.getId();
            int t = 0;
            List<RPQ> rpqs = new ArrayList<>();
            List<RPQ> rpqsed = new ArrayList<>();
            for (Operation o : e[mid]) {
                RPQ rpq = new RPQ();
                rpq.setO(o);
                rpq.setP(o.getDuration());
  /*              rpq.setR();
                rpq.setQ();*/
            }
            while (rpqsed.size() < e[mid].size()) {
                RPQ rpq = findRPQ(rpqs);
                rpqsed.add(rpq);
                rpqs.remove(rpq);
            }
            map.put(m, lag);
        }
        int maxlag = 0;
        for (Map.Entry<Machine, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxlag) {
                maxlag = entry.getValue();
                mbottleneck = entry.getKey();
            }
        }
        return mbottleneck;
    }

    private static RPQ findRPQ(List<RPQ> rpqs) {
        RPQ rpq = new RPQ();
        return rpq;
    }
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