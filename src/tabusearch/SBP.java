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

        LinkedList<Operation>[] e = s.getE();
        Map<Machine, Integer> maplag = new HashMap<>();
        Map<Machine, List<Operation>> mapo = new HashMap<>();
        for (Machine m : machines) {
            int lag = 0;
            int mid = m.getId();
            List<Operation> operations = new ArrayList<>();
            List<Operation> operationsed = new ArrayList<>();
            operations.addAll(e[mid]);


            int t = 0;
            while (operationsed.size() < e[mid].size()) {
                Operation omin = operations.get(0);
                int rmin = Integer.MAX_VALUE;
                int pmin = Integer.MAX_VALUE;
                for (Operation o : operations) {
                    if (t > o.getStart()) {
                        if (pmin > o.getDuration()) {
                            pmin = o.getDuration();
                            rmin = o.getStart();
                            omin = o;
                        }
                    } else {
                        if (rmin > o.getStart()) {
                            rmin = o.getStart();
                            omin = o;
                        }
                    }
                }
                if (t < omin.getStart()) {
                    t = omin.getStart() + omin.getDuration();
                } else {
                    t = t + omin.getDuration();
                }
                if (t - omin.getEnd() > lag)
                    lag = t - omin.getEnd();
                operationsed.add(omin);
                operations.remove(omin);
            }


            maplag.put(m, lag);
            mapo.put(m, operationsed);
        }
        int maxlag = 0;
        Machine mbottleneck = machines.iterator().next();
        for (Map.Entry<Machine, Integer> entry : maplag.entrySet()) {
            if (entry.getValue() > maxlag) {
                maxlag = entry.getValue();
                mbottleneck = entry.getKey();
            }
        }
        s.scheduleOneMachine(mapo.get(mbottleneck), maxlag);
        return mbottleneck;
    }

/*    private static RPQ findRPQ(List<RPQ> rpqs) {
        RPQ rpq = new RPQ();
        return rpq;
    }*/


}