package tabusearch;

import java.util.Random;

public class ITabuList {

    /**
     * Initialize an empty tabu list for a given problem. The minimum and
     * maximum length of the tabu list will be initialised automatically.
     *
     * @param p The JSS instance.
     */
    public ITabuList(Problem p) {
        matrix = new int[p.getNumberOfOperations()][p.getNumberOfOperations()];
        L = 10 + p.getNumberOfJobs() / (p.getNumberOfMachines() * 2);
        setLength(L);
    }

    private int L;
    /************************************
     * CONTENT (matrix)
     ************************************/

    /**
     * Variable referencing the tabu list, which can be visualised as a matrix.
     * <p>
     * Matrix[i][j] contains the count of the iteration in which the arc (i,j)
     * has been reversed last time.
     */
    private final int[][] matrix;

    /**
     * @return the matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /************************************
     * MODIFICATIONS TO THE TABULIST
     ************************************/

    /**
     * Update the tabu list.
     *
     * @param m
     * @param iterationCount
     * @param phase
     */
    public void update(Move m, int iterationCount, int numberOfIterationsOfNoImprovement, Phase phase) {
        try {
            matrix[m.getInversion().get(0).getId()][m.getInversion().get(1)
                    .getId()] = iterationCount + 1;
            if (m.getNumberOfOperationsInInversion() == 3) {
                // We always consider reversal of three arcs, so memorize all
                // arcs considered.
                matrix[m.getInversion().get(1).getId()][m.getInversion().get(2)
                        .getId()] = iterationCount + 1;
                matrix[m.getInversion().get(0).getId()][m.getInversion().get(2)
                        .getId()] = iterationCount + 1;
            }
/*            if (iterationCount / getLambda() == 0) {
                randomlyChooseMinAndMax();
            }*/
            int K = 800 / 4;
            if (numberOfIterationsOfNoImprovement < K) {
                setLength(L);
            } else if (numberOfIterationsOfNoImprovement < K * 2) {
                int l = (numberOfIterationsOfNoImprovement - K) / (5 * K) * L + L;
                setLength(l);
            } else if (numberOfIterationsOfNoImprovement < K * 3) {
                int l = (int) ((numberOfIterationsOfNoImprovement - 2 * K) * 4 / (5 * K) * L + 1.2 * L);
                setLength(l);
            } else if (numberOfIterationsOfNoImprovement < K * 4) {
                setLength(2 * L);
            }
        } catch (Exception e) {
            System.out.println("Invalid move.");
        } finally { // always update the list length
            updateListLength(phase);
        }
    }

    /**
     * Randomly choose the value min and max. Min will be chosen between a and
     * b, max will be chosen between A and B.
     */
    private void randomlyChooseMinAndMax() {
        //todo
    }

    private void updateListLength(Phase phase) {
        //todo
    }

    public boolean isAllowed(Move m, int k) {
        boolean res = checkTabuStatus(m.getInversion().get(0).getId(), m
                .getInversion().get(1).getId(), k + 1);
        if (m.getNumberOfOperationsInInversion() == 3) {
            res = res
                    && checkTabuStatus(m.getInversion().get(0).getId(), m
                    .getInversion().get(2).getId(), k + 1)
                    && checkTabuStatus(m.getInversion().get(1).getId(), m
                    .getInversion().get(2).getId(), k + 1);
        }
        return res;
    }

    private boolean checkTabuStatus(int i, int j, int k) {
        return (getMatrix()[j][i] + getLength()) <= k;
    }

    private static int length = 1;

    public static int getLength() {
        return length;
    }

    public static void setLength(int length) {
        ITabuList.length = length;
    }

    @Override
    public String toString() {
        int[][] m = getMatrix();
        String res = "";
        for (int i = 0; i < m.length; i++) {
            res += i + ": |";
            for (int j = 0; j < m.length; j++) {
                res += (matrix[i][j] + " ");
            }
            res += ("|\n");
        }
        return res;
    }

}
