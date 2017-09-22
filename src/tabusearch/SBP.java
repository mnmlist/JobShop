package tabusearch;

import java.util.HashSet;
import java.util.Set;


public class SBP {
    private static int ADD_0_NODE = 0;

    public static Solution getInitSol(Problem p) {
        Solution s = new Solution(p);

        Set<Machine> machinesed = new HashSet<>();
        Set<Machine> machines = new HashSet<>();

        for (Operation o : p.getV()) {
            if (o.getMachine() != null)
                if (o.getMachine().getId() == 0 && ADD_0_NODE == 1)
                    machines.add(o.getMachine());
        }

        return s;
    }

    private static Machine findMaxMachine(Set<Machine> machines) {

    }
}
